module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS ) where

import Palet
import Route

data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
newS capacity = Sta [] capacity


freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta p c) = c - length(p)

stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta p c) pal | freeCellsS (Sta p c) > 0 = (Sta (p ++ [pal]) c)
                     | otherwise = (Sta p c) --VER ESTO QUE DEVOLVERIA SI NO PUEDE SUMARLO

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta p c) = sum([netP x | x <- p])

holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta p c) pal r | 
                       |elem (destinationP pal) (destinationsR r) /= True = False
                        --chequeo de si esta vacio, en ese caso que lo meta
                       | inOrderR r (destinationP pal) (destinationP(last p)) = True
                       | otherwise = False

popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
popS (Sta p c) city | null p = (Sta p c)
                    | destinationP(last p) /= city = (Sta p c)
                    | otherwise = Sta (init p) c

