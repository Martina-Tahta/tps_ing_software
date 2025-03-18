module Palet ( Palet, newP, destinationP, netP ) where

data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet   -- construye un Palet dada una ciudad de destino y un peso en toneladas
newP city weight |  weight <= 0 = error "El peso no puede ser menor o igual a 0"
                 | otherwise = Pal city weight

destinationP :: Palet -> String  -- responde la ciudad destino del palet
destinationP (Pal c w) = c

netP :: Palet -> Int             -- responde el peso en toneladas del palet
netP (Pal c w) = w
