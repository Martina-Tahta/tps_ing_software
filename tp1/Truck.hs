module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [Stack] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT amount height route | (amount < 0) || (height < 0) = error "La cantidad de bahias no puede ser negativa"
                         | otherwise = Tru [newS height | i <- [1..amount]] route

freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru s r) = sum [freeCellsS x | x <- s]
--freeCellsT (Tru s r) = length [x | x <- s, (freeCellsS x > 0 && netS x < 10)] --VER ESTOO, EN BASE A QUE DISPONIBLE??


checkS :: Stack -> Route -> Palet -> Bool
checkS s r pal | freeCellsS s == 0 = False
               | netS s + netP pal > 10 = False
               | holdsS s pal r == False = False
               | otherwise = True

findS :: Truck -> Palet -> Int -> Int
findS (Tru s r) pal i | (null s) || (i == length s) = -1
                      | checkS (s !! i) r pal = i
                      | otherwise = findS (Tru s r) pal (i+1) 

loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion
loadT (Tru s r) pal | freeCellsT (Tru s r) == 0 = (Tru s r) --ver si hay celdas disponibles
                    | newI == -1 = (Tru s r) --si no se encontro bahia para ponerlo
                    | otherwise = Tru ([x | (x, i) <- zip s [0..] , newI /= i] ++ [stackS (s !! newI) pal]) r 
                    where 
                      newI = findS (Tru s r) pal 0


unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT (Tru s r) c = Tru [popS x c | x <- s] r


netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru s r) = sum [netS x | x <- s]
