import Palet
import Stack
import Route
import Truck

import Control.Exception
import System.IO.Unsafe


-- NO SE SI ESTA FUNC ESTA EN LO IMPORTADO O LO TENEMOS Q DEFINIR ACA
testF :: Show a => a -> Bool
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
    testF (newP "bs as" 3),
    destinationP (pal) == "bs as",
    destinationP (pal) /= "bs a",
    netP (pal) == 3,
    netP (pal) /= 2
    ] == True

route = newR ["a", "b", "c", "d"]
testRoute :: Bool
testRoute = and [
    testF(newR ["a", "b", "c", "d"]),
    destinationsR (route) == ["a", "b", "c", "d"],
    inRouteR route "c" == True,
    inRouteR route "r" == False,
    inOrderR (route) "a" "b" == True,
    inOrderR (route) "b" "a" /= True,
    inOrderR (route) "h" "j" /= True, --ninguno esta en la lista
    inOrderR (route) "a" "j" == True, --solo el primero esta en la lista
    inOrderR (route) "h" "b" /= True --solo el segundo esta en la lista
    ] == True
-- casos borde:
    --  una no esta en la lista
    --  ninguna esta
    --  si te pasan el mismo lugar


stack1 = newS 1
stack3 = newS 3

testStack :: Bool
testStack = and [
    testF (newS 3),
    freeCellsS (stack3) == 3,
    netS (stack3) == 0,
    testF(stackS stack1 pal),
    freeCellsS (stack1) == 0,
    testF(stackS stack1 pal)
    netS (stack1) == 3, -- ver que lo devuelva igual en el stack en el caso donde no tiene mas lugar en el stack
    
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
    testF (newT 3 2 route),
    length (freeCellsT truck1) == 3,  
    netT truck1 == 0,
    testF (loadT truck1 pal),
    netT truck2 == 3, 
    freeCellsT truck2 == 2,
    testF (unloadT truck2 "bs as"),
    netT truck3 == 0,  
    freeCellsT truck3 == 3, 
    testF (findS truck1 pal 0 == 0 || findS truck1 pal 0 == -1) 
    where
        truck1 = newT 3 2 route
        truck2 = loadT truck1 pal
        truck3 = unloadT truck2 "bs as"
    ] == True


-- casos bordes:
--  freeCellsS --> de 0 cuando recien creas truck
--  loadT --> ver caso donde no hay stack para meter el palet ==> devuele mismo truck
--          esto inculye caso donde no hay stacks en eltruck todavia
--  loadT --> ver caso de stack vacio (que sepa meterlo en uno vacio)
--  loadT y checkS --> ver que todos los casos agarrados en checkS se manejen bien
--  unloadT --> ver que los casos que agarra pop se manejen bien ==> que si no esta la ciudad se devuelva el mismo palet
--                  esto es == a que no cambie el peso de el stack o truck si pasa con todos