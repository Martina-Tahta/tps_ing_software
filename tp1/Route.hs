module Route ( Route, newR, destinationsR, inOrderR) where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [String] -> Route                    -- construye una ruta segun una lista de ciudades
newR destinations = Rou destinations

destinationsR :: Route -> [ String ]
destinationsR (Rou d) = d

inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR (Rou d) c1 c2 | null d = False
                       | head d == c1 = True
                       | head d == c2 = False
                       | otherwise = inOrderR (newR (tail d)) c1 c2