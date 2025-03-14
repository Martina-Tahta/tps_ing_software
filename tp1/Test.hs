import Palet
import Stack
import Route
import Truck


testPalet :: Bool
testPalet = and [
    destinationP (newP "bs as" 3) == "bs as",
    destinationP (newP "bs as" 3) /= "bs a",
    netP (newP "bs as" 3) == 3,
    netP (newP "bs as" 3) /= 2
    ] == True


testRoute :: Bool
testRoute = and [
    destinationsR (newR ["a", "b", "c", "d"]) == ["a", "b", "c", "d"],
    elem "c" (destinationsR (newR ["a", "b", "c", "d"])) == True,
    inOrderR (newR ["a", "b", "c", "d"]) "a" "b" == True,
    inOrderR (newR ["a", "b", "c", "d"]) "b" "a" /= True,
    inOrderR (newR ["a", "b", "c", "d"]) "h" "j" /= True, --ninguno esta en la lista
    inOrderR (newR ["a", "b", "c", "d"]) "a" "j" == True, --solo el primero esta en la lista
    inOrderR (newR ["a", "b", "c", "d"]) "h" "b" /= True --solo el segundo esta en la lista
    -- casos borde:
    --  una no esta en la lista
    --  ninguna esta
    --  si te pasan el mismo lugar
    ] == True

testStack :: Bool
testStack = and [
    freeCellsS (newS 3) == 3,
    netS (newS 3) == 0,
    freeCellsS (stackS (newS 1) (newP "a" 2)) == 0
    
    ] == True
    -- casos borde:
    --  stackS --> ver que lo devuelva igual en el stack en el caso donde no tiene mas lugar en el stack
    --  holdsS --> si el stack esta vacio ==> agrega pal
    --  holdsS --> ver si el pal que entra tiene destino en la ruta
    --  holdsS --> casos de inOrderR, lo testeamos para mostrar que no afecta
    --          caso de que sean iguales mostrar que funca
    --  popS --> cuando stack esta vacio

testTruck :: Bool
-- casos bordes:
-- 