-- module Main where

-- import Palet
-- import Route
-- import Stack

-- main :: IO ()
-- main = do
--     -- Test 1
--     let s1 = newS 3
--     print $ freeCellsS s1
--     let s2 = stackS s1 (newP "bs as" 1)
--     let s3 = stackS s2 (newP "ny" 3)
--     let s4 = stackS s3 (newP "santiago" 2)
--     print $ freeCellsS s4

--     let s5 = stackS s4 (newP "mv" 1)
--     print $ freeCellsS s5
--     print $ netS s5

--     -- Test 2
--     let r = newR ["a", "b", "c", "d"]  -- Crear la ruta necesaria para holdsS
--     print r

--     let s6 = newS 6
--     let s7 = stackS s6 (newP "b" 1)
--     let s8 = stackS s7 (newP "b" 1)
--     let s9 = stackS s8 (newP "b" 1)
--     let s10 = stackS s9 (newP "c" 1)

--     -- Ahora pasamos `r` a `holdsS`
--     print $ holdsS s10 (newP "a" 1) r
--     print $ holdsS s10 (newP "b" 1) r
--     print $ holdsS s10 (newP "d" 1) r
--     print $ holdsS s10 (newP "c" 1) r

--     print $ freeCellsS s10
--     let s11 = popS s10 "c"
--     print $ freeCellsS s11

module Main where

import Palet
import Stack
import Route
import Truck

main :: IO ()
main = do
    -- Crear una ruta
    let r = newR ["bs as", "ny", "santiago", "mv"]
    print r

    -- Crear un camión con 2 bahías y altura 3
    let t1 = newT 2 3 r
    print t1
    print $ freeCellsT t1  -- Debería devolver 6 (2 bahías * 3 de altura)

    -- Crear palets
    let p1 = newP "bs as" 2
    let p2 = newP "ny" 3
    let p3 = newP "santiago" 1
    let p4 = newP "mv" 1

    -- Cargar palets en el camión
    let t2 = loadT t1 p1
    let t3 = loadT t2 p2
    let t4 = loadT t3 p3
    let t5 = loadT t4 p4

    print t5  -- Ver el estado del camión después de cargar
    print $ freeCellsT t5  -- Ver celdas libres después de carga
    print $ netT t5  -- Peso neto del camión

    -- Descargar en "santiago"
    let t6 = unloadT t5 "santiago"
    print t6
    print $ freeCellsT t6  -- Ver cuántas celdas quedaron libres después de la descarga

