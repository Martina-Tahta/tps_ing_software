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

checkS :: Stack -> Route -> Palet -> Bool
checkS s r pal = (freeCellsS s > 0) && (netS s + netP pal <= 10) && (holdsS s pal r)

findS :: Truck -> Palet -> Int -> Int
findS (Tru s r) pal i | (null s) || (i == length s) = -1
                      | checkS (s !! i) r pal = i
                      | otherwise = findS (Tru s r) pal (i+1) 

loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion
loadT (Tru s r) pal | freeCellsT (Tru s r) == 0 = (Tru s r) 
                    | newI == -1 = (Tru s r) 
                    | otherwise = Tru ((take newI s) ++ [stackS (s !! newI) pal] ++ (drop (newI+1) s)) r 
                    where 
                      newI = findS (Tru s r) pal 0


unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT (Tru s r) c = Tru [popS x c | x <- s] r


netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru s r) = sum [netS x | x <- s]
