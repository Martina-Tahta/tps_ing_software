module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [Stack] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT amount height route = Tru (replicate amount (newS height)) route

freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru s r) = length [x | x <- s, freeCellsS x > 0]


checkS :: Stack -> Route -> Palet -> Bool
checkS s r pal | freeCellsS s == 0 = False
                     | netS s + netP pal > 10 = False
                     | holdsS s pal r == False = False
                     | otherwise = True

loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion
loadT (Tru s r) pal = Tru (stackS (head [x | x <- s, checkS x r pal]) pal) r


unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT (Tru s r) c = Tru [popS x c | x <- s] r


netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru s r) = sum [netS x | x <- s]
