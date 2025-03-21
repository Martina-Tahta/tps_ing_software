module Route ( Route, newR, inOrderR, inRouteR) where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [String] -> Route                    -- construye una ruta según una lista de ciudades
newR destinations = Rou destinations


inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR (Rou []) _ _ = False
inOrderR (Rou (x:xs)) c1 c2 | x == c1 = True
                            | x == c2 = False
                            | otherwise = inOrderR (newR xs) c1 c2

inRouteR :: Route -> String -> Bool -- indica si la ciudad consultada está en la ruta
inRouteR (Rou d) c = elem c d
