import Palet
import Stack
import Route
import Truck

import Control.Exception
import System.IO.Unsafe

testF :: Show a => a -> Bool --PARA QUE ES ESTOO, NO TENEMOS EXCEPTIONS EN LAS FUNCIONES??
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()



pal = newP "bs as" 3
testPalet :: Bool
testPalet = and [
    --testF (newP "bs as" 3),
    destinationP (pal) == "bs as",
    destinationP (pal) /= "bs a",
    netP (pal) == 3,
    netP (pal) /= 2
    ] == True


route = newR ["a", "b", "c", "d"]
testRoute :: Bool
testRoute = and [
    --testF(newR ["a", "b", "c", "d"]),
    inRouteR route "c" == True,
    inRouteR route "r" == False,
    inOrderR route "a" "b" == True,
    inOrderR route "b" "a" /= True,
    inOrderR route "h" "j" /= True, --ninguno esta en la lista
    inOrderR route "a" "j" == True, --solo el primero esta en la lista
    inOrderR route "h" "b" /= True --solo el segundo esta en la lista
    ] == True
-- casos borde:
    --  una no esta en la lista
    --  ninguna esta
    --  si te pasan el mismo lugar

pal_a = newP "a" 1
pal_b = newP "b" 5
pal_c = newP "c" 3
pal_d = newP "d" 1
pal_h = newP "h" 2

stack1 = newS 1
stack3 = newS 3

--actions
stack1' = stackS stack1 pal_c
stack1'' = stackS stack1' pal_a
stack1''' = popS stack1'' "c"

stack3' = stackS stack3 pal_c
stack3'' = stackS stack3' pal_a
stack3_p1 = popS stack3'' "h"
stack3_p2 = popS stack3_p1 "c"
stack3_p3 = popS stack3_p2 "a"

testStack :: Bool
testStack = and [
    --testF (newS 3),
    freeCellsS (stack3) == 3,
    netS (stack3) == 0,
    --stack1 = stackS stack1 pal_c,
    freeCellsS (stack1') == 0,
    --stack1 = stackS stack1 pal_c
    netS (stack1'') == 3, -- ver que lo devuelva igual en el stack en el caso donde no tiene mas lugar en el stack
    holdsS stack3 pal_a route == True, --si el stack esta vacio ==> puede agregar pal
    --stack3 = stackS stack3 pal_c, --si el stack esta vacio ==> agrega pal
    netS (stack3') == 3,
    holdsS stack3' pal_a route == True, --estee
    holdsS stack3' pal_h route == False,
    holdsS stack3' pal_d route == False,
    holdsS stack3' pal_c route == True, -- caso de que sean iguales mostrar que funca
    --stack3 = stackS stack3 pal_a,
    holdsS stack3'' pal_c route == False,
    netS (stack3'') == 4,
    --stack3 = popS stack3 "h",
    netS (stack3_p1) == 4,
    --stack3 = popS stack3 "c",
    netS (stack3_p2) == 4,
    --stack3 = popS stack3 "a",
    netS (stack3_p3) == 3,
    --stack1 = popS stack1 "c",
    netS (stack1''') == 0
    ] == True
    -- casos borde:
    --  stackS --> ver que lo devuelva igual en el stack en el caso donde no tiene mas lugar en el stack
    --  holdsS --> si el stack esta vacio ==> agrega pal
    --  holdsS --> ver si el pal que entra tiene destino en la ruta
    --  holdsS --> casos de inOrderR, lo testeamos para mostrar que no afecta
    --          caso de que sean iguales mostrar que funca
    --  popS --> cuando stack esta vacio

testTruck :: Bool
testTruck = and [
    freeCellsT truck1 == 3,  
    netT truck1 == 0,        
    freeCellsT truck2 == 2,  
    netT truck2 == 3,        
    freeCellsT truck3 == 3,   
    netT truck3 == 0,        
    unloadResult           
  ] == True
  where
    route = newR ["Buenos Aires", "Córdoba", "Rosario", "Mendoza"] 
    pal = newP "Córdoba" 3  
    truck1 = newT 3 2 route 
    truck2 = loadT truck1 pal 
    truck3 = unloadT truck2 "Córdoba" 
    unloadResult = netT truck3 == 0 && freeCellsT truck3 == 3 

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
--          esto inculye caso donde no hay stacks en eltruck todavia
--  loadT --> ver caso de stack vacio (que sepa meterlo en uno vacio)
--  loadT y checkS --> ver que todos los casos agarrados en checkS se manejen bien
--  unloadT --> ver que los casos que agarra pop se manejen bien ==> que si no esta la ciudad se devuelva el mismo palet
--                  esto es == a que no cambie el peso de el stack o truck si pasa con todos