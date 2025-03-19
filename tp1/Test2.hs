import Palet
import Stack
import Route
import Truck

import Control.Exception
import System.IO.Unsafe
import Debug.Trace (trace)

-- CASOS QUE FALTAN
--    NUMEROS NEGATIVOS AL INICIAR STACK O TRUCK O PESO DE PALET
--    


testF :: Show a => a -> Bool --PARA QUE ES ESTOO, NO TENEMOS EXCEPTIONS EN LAS FUNCIONES??
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()




testPalet :: Bool
testPalet = and [
    testF (newP "bs as" (-3)),
    testF (newP "bs as" 0),
    destinationP (pal) == "bs as",
    destinationP (pal) /= "bs a",
    netP (pal) == 3,
    netP (pal) /= 2
    ]
    where
      pal = newP "bs as" 3


testRoute :: Bool
testRoute = and [
    inRouteR route "c" == True,
    inRouteR route "r" == False,
    inOrderR route "a" "b" == True,
    inOrderR route "b" "a" /= True,
    inOrderR route "h" "j" /= True, --ninguno esta en la lista
    inOrderR route "a" "j" == True, --solo el primero esta en la lista
    inOrderR route "h" "b" /= True --solo el segundo esta en la lista
    ]
    where 
      route = newR ["a", "b", "c", "d"]
-- casos borde:
    --  una no esta en la lista
    --  ninguna esta
    --  si te pasan el mismo lugar



testStack :: Bool
testStack = and [
    testF (newS (-3)),
    freeCellsS (stack3) == 3,
    netS (stack3) == 0,
    freeCellsS (stack1') == 0,
    netS (stack1'') == 3, -- ver que lo devuelva igual en el stack en el caso donde no tiene mas lugar en el stack
    holdsS stack3 pal_a route == True, --si el stack esta vacio ==> puede agregar pal
    netS (stack3') == 3,
    holdsS stack3' pal_a route == True, --estee
    holdsS stack3' pal_h route == False,
    holdsS stack3' pal_d route == False,
    holdsS stack3' pal_c route == True, -- caso de que sean iguales mostrar que funca
    holdsS stack3'' pal_c route == False,
    netS (stack3'') == 4,
    netS (stack3_p1) == 4,
    netS (stack3_p2) == 4,
    netS (stack3_p3) == 3,
    netS (stack1''') == 0
    ]
    where
      pal_a = newP "a" 1
      pal_b = newP "b" 5
      pal_c = newP "c" 3
      pal_d = newP "d" 1
      pal_h = newP "h" 2
      route = newR ["a", "b", "c", "d"]
      stack1 = newS 1
      stack3 = newS 3
      
      stack1' = stackS stack1 pal_c
      stack1'' = stackS stack1' pal_a
      stack1''' = popS stack1'' "c"

      stack3' = stackS stack3 pal_c
      stack3'' = stackS stack3' pal_a
      stack3_p1 = popS stack3'' "h"
      stack3_p2 = popS stack3_p1 "c"
      stack3_p3 = popS stack3_p2 "a"
    -- casos borde:
    --  stackS --> ver que lo devuelva igual en el stack en el caso donde no tiene mas lugar en el stack
    --  holdsS --> si el stack esta vacio ==> agrega pal
    --  holdsS --> ver si el pal que entra tiene destino en la ruta
    --  holdsS --> casos de inOrderR, lo testeamos para mostrar que no afecta
    --          caso de que sean iguales mostrar que funca
    --  popS --> cuando stack esta vacio


testTruckCreation :: Bool
testTruckCreation = and [
    testF(newT (-1) 1 route),
    testF(newT 1 (-1) route),
    testF(newT (-1) (-1) route),
    freeCellsT truck1 == 6,  
    netT truck1 == 0,       
    freeCellsT truck2 == 5,  
    netT truck2 == 3,       
    freeCellsT truck3 == 6,  
    netT truck3 == 0         
  ]
  where
    route = newR ["Buenos Aires", "Córdoba", "Rosario", "Mendoza"]
    pal = newP "Córdoba" 3  
    truck1 = newT 3 2 route 
    truck2 = loadT truck1 pal
    truck3 = unloadT truck2 "Córdoba" 

-- Test 2: Palet no entra en ningun bahia por capacidad
testNoSpace :: Bool
testNoSpace = and [
    freeCellsT truck00 == 0, 
    netT truck00 == 0,  

    freeCellsT truck10 == 0,  
    netT truck10' == 0,   
    freeCellsT truck10'' == 0,  
    netT truck10'' == 0,

    freeCellsT truck11 == 1, 
    freeCellsT truck11' == 0,  
    netT truck11' == 3,   
    freeCellsT truck11'' == 0,  
    netT truck11'' == 3,
    netT truck11''' == 5
  ]
  where
    route = newR ["Buenos Aires", "Córdoba", "Rosario", "Mendoza"]
    pal1 = newP "Córdoba" 3
    pal2 = newP "Mendoza" 5
    truck00 = loadT (newT 0 0 route) pal1

    truck10 = newT 1 0 route
    truck10' = loadT truck10 pal1
    truck10'' = loadT truck10' pal1

    truck11 = newT 1 1 route
    truck11' = loadT truck11 pal1
    truck11'' = loadT truck11' pal1
    truck11''' = loadT (unloadT truck11'' "Córdoba") pal2


-- Test 3: Palet no entra en ningun bahia por destino
testIncorrectRoute :: Bool
testIncorrectRoute = and [
    freeCellsT truck_no_route' == 1, 
    netT truck_no_route' == 0,

    freeCellsT truck1 == 6, 
    netT truck1 == 0,       
    freeCellsT truck1' == 6,  
    netT truck1' == 0,

    freeCellsT truck12 == 1, 
    netT truck12 == 5, 
    freeCellsT truck21 == 0, 
    netT truck21 == 8,
    freeCellsT truck22 == 2, 
    netT truck22 == 10 
  ]
  where
    truck_no_route = newT 1 1 (newR [])
    pal = newP "Córdoba" 3  
    truck_no_route' = loadT truck_no_route pal
    
    route = newR ["San Juan", "Mendoza", "La Rioja"]  
    truck1 = newT 3 2 route  
    truck1' = loadT truck1 pal 

    pal1 = newP "Mendoza" 3  
    pal2 = newP "San Juan" 5  
    truck12 = foldl loadT (newT 1 2 route) [pal2, pal1]
    truck21 = foldl loadT (newT 2 1 route) [pal2, pal1]
    truck22 = foldl loadT (newT 2 2 route) [pal2, pal2, pal1]
    
-- Test 4: Palet no entra en ningun bahia por peso max
testOverWeightStack :: Bool 
testOverWeightStack = and [
    freeCellsT truck == 2,
    netT truck == 0,       
    freeCellsT truck' == 1, 
    netT truck' == 5,
    freeCellsT truck2 == 1, 
    netT truck2 == 16           
  ]
  where
    route = newR ["San Juan", "Mendoza", "La Rioja"]  
    pal1 = newP "San Juan" 4  
    pal2 = newP "Mendoza" 7 
    pal3 = newP "La Rioja" 5  
    pal4 = newP "La Rioja" 11

    truck = loadT (newT 1 2 route) pal4
    truck' = foldl loadT truck [pal3, pal2]
    truck2 = foldl loadT (newT 2 2 route) [pal3, pal2, pal1, pal2]


-- Test 5: Test de descarga exitosa
testUnload :: Bool
testUnload = and [
    freeCellsT truck == 0,  
    netT truck == 14,       
    freeCellsT truck' == 1,  
    netT truck' == 13,  
    freeCellsT truck'' == 4,  
    netT truck'' == 4,   
    freeCellsT truck''' == 6,  
    netT truck''' == 0,
      
    freeCellsT truck2' == 3,  
    netT truck2' == 6,  
    freeCellsT truck2'' == 3,  
    netT truck2'' == 6, 
    freeCellsT truck2''_bad == 3,  
    netT truck2''_bad == 6,  
    freeCellsT truck2''' == 6,  
    netT truck2''' == 0       
  ]
  where
    route = newR ["San Juan", "Mendoza", "La Rioja"]  
    pal1 = newP "San Juan" 1  
    pal2 = newP "Mendoza" 3 
    pal3 = newP "La Rioja" 2  
    truck = foldl loadT (newT 3 2 route) [pal3, pal3, pal2, pal2, pal1, pal2]
    truck' = unloadT truck "San Juan"
    truck'' = unloadT truck' "Mendoza"
    truck''' = unloadT truck'' "La Rioja"

    truck2 = foldl loadT (newT 3 2 route) [pal3, pal3, pal1, pal3]
    truck2' = unloadT truck2 "San Juan"
    truck2'' = unloadT truck2' "Mendoza"
    truck2''_bad = unloadT truck2'' "Buenos Aires"
    truck2''' = unloadT truck2'' "La Rioja"


-- Test 6: Test de camión lleno y sin espacio para más pallets
testTruckFull :: Bool
testTruckFull = and [
    freeCellsT truck1 == 5,  
    netT truck1 == 3,        
    freeCellsT truck2 == 4,  
    netT truck2 == 5         
  ]
  where
    route = newR ["Buenos Aires", "Córdoba", "Rosario", "Mendoza"]
    pal1 = newP "Córdoba" 3  
    pal2 = newP "Mendoza" 2  
    truck1 = loadT (newT 3 2 route) pal1   
    truck2 = loadT truck1 pal2           


allTestTruck :: Bool
allTestTruck = and [
    testTruckCreation,
    testNoSpace,
    testIncorrectRoute,
    testOverWeightStack,
    testUnload
  ]

-- casos para hacer:
-- testear todo las cosas pero mas por el truck
-- hacer truck con poco espacio que se llene y tratar de meter otro y ver uqe devuelva el mismo truck
-- que quede lugar pro no matcheen las rutas
-- ver casos donde daria mal 
-- unload ver si tenes que hacerlo en mas de un stack 
-- ver que llenas un truck y pasas toods los destination y se vaciaron todos (todo estaba bien en otrden)
-- ver casos bordes de stack (que tenga un stack) para ver si se cumplen

-- casos bordes:
--  freeCellsS --> de 0 cuando recien creas truck
--  loadT --> ver caso donde no hay stack para meter el palet ==> devuele mismo truck
--  loadT --> ver caso de stack vacio (que sepa meterlo en uno vacio)
--  loadT y checkS --> ver que todos los casos agarrados en checkS se manejen bien
--  unloadT --> ver que los casos que agarra pop se manejen bien ==> que si no esta la ciudad se devuelva el mismo palet
--                  esto es == a que no cambie el peso de el stack o truck si pasa con todos

testAll :: Bool
testAll = and [
  testPalet,
  testRoute,
  testStack,
  allTestTruck
  ]