import Palet
import Stack
import Route
import Truck

import Control.Exception
import System.IO.Unsafe
import Debug.Trace (trace)

testAll :: Bool
testAll = and [
  testPalet,
  testRoute,
  testStack,
  allTestTruck
  ]


testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()


testPalet :: Bool
testPalet = and [
    testF (newP "bs as" (-3)),
    testF (newP "bs as" 0),
    destinationP (pal) == "bs as",
    destinationP (pal) /= "bs a",
    netP (pal) == 3,
    netP (pal) /= 2,

    destinationP (pal_weird_dest) == "12-~!#h",
    destinationP (pal_weird_dest) /= "12-~!#"
    ]
    where
      pal = newP "bs as" 3
      pal_weird_dest = newP "12-~!#h" 2


testRoute :: Bool
testRoute = and [
    inRouteR route "c" == True,
    inRouteR route "r" == False,
    inOrderR route "a" "b" == True,
    inOrderR route "b" "a" /= True,
    inOrderR route "a" "a" == True,
    inOrderR route "h" "j" /= True, 
    inOrderR route "a" "j" == True,
    inOrderR route "h" "b" /= True,

    inRouteR route_empty "r" == False,
    inOrderR route_empty "a" "b" == False
    ]
    where 
      route = newR ["a", "b", "c", "d"]
      route_empty = newR []


testStack :: Bool
testStack = and [
    testF (newS (-3)),

    freeCellsS (stack1') == 0,
    netS (stack1'') == netS (stack1'''), 
    netS (stack1'''') == 0,
    netS (stack1''''_extra_pop) == netS (stack1''''),

    freeCellsS (stack3) == 3,
    netS (stack3) == 0,
    holdsS stack3 pal_a route == True,
    holdsS stack3 pal_h route == False,

    netS (stack3') == 3,
    holdsS stack3' pal_a route == True, 
    holdsS stack3' pal_h route == False,
    holdsS stack3' pal_d route == False,
    holdsS stack3' pal_c route == True, 
    holdsS stack3'' pal_c route == False,
    netS (stack3'') == 4,
    netS (stack3_p1) == netS (stack3''),
    netS (stack3_p2) == netS (stack3''),
    netS (stack3_p3) == 3
    ]
    where
      pal_a = newP "a" 1
      pal_b = newP "b" 5
      pal_c = newP "c" 3
      pal_d = newP "d" 1
      pal_h = newP "h" 2
      route = newR ["a", "b", "c", "d"]
      stack1 = newS 1
      stack3 = newS 3
      
      stack1' = stackS stack1 pal_c
      stack1'' = stackS stack1' pal_a
      stack1''' = stackS stack1'' pal_a
      stack1'''' = popS stack1''' "c"
      stack1''''_extra_pop = popS stack1'''' "c"

      stack3' = stackS stack3 pal_c
      stack3'' = stackS stack3' pal_a
      stack3_p1 = popS stack3'' "h"
      stack3_p2 = popS stack3_p1 "c"
      stack3_p3 = popS stack3_p2 "a"


testTruckCreation :: Bool
testTruckCreation = and [
    testF(newT (-1) 1 route),
    testF(newT 1 (-1) route),
    testF(newT (-1) (-1) route),
    freeCellsT truck1 == 6,  
    netT truck1 == 0,       
    freeCellsT truck2 == 5,  
    netT truck2 == 3,       
    freeCellsT truck3 == 6,  
    netT truck3 == 0         
  ]
  where
    route = newR ["Buenos Aires", "Córdoba", "Rosario", "Mendoza"]
    pal = newP "Córdoba" 3  
    truck1 = newT 3 2 route 
    truck2 = loadT truck1 pal
    truck3 = unloadT truck2 "Córdoba" 


testNoSpace :: Bool
testNoSpace = and [
    freeCellsT truck00 == 0, 
    netT truck00 == 0,  

    freeCellsT truck10 == 0,  
    netT truck10' == 0,   
    freeCellsT truck10'' == 0,  
    netT truck10'' == 0,

    freeCellsT truck11 == 1, 
    freeCellsT truck11' == 0,  
    netT truck11' == 3,   
    freeCellsT truck11'' == 0,  
    netT truck11'' == 3,
    netT truck11''' == 5
  ]
  where
    route = newR ["Buenos Aires", "Córdoba", "Rosario", "Mendoza"]
    pal1 = newP "Córdoba" 3
    pal2 = newP "Mendoza" 5
    truck00 = loadT (newT 0 0 route) pal1

    truck10 = newT 1 0 route
    truck10' = loadT truck10 pal1
    truck10'' = loadT truck10' pal1

    truck11 = newT 1 1 route
    truck11' = loadT truck11 pal1
    truck11'' = loadT truck11' pal1
    truck11''' = loadT (unloadT truck11'' "Córdoba") pal2


testIncorrectRoute :: Bool
testIncorrectRoute = and [
    freeCellsT truck_no_route' == 1, 
    netT truck_no_route' == 0,

    freeCellsT truck1 == 6, 
    netT truck1 == 0,       
    freeCellsT truck1' == 6,  
    netT truck1' == 0,

    freeCellsT truck12 == 1, 
    netT truck12 == 5, 
    freeCellsT truck21 == 0, 
    netT truck21 == 8,

    freeCellsT truck22 == 2, 
    netT truck22 == 8,
    freeCellsT truck22' == 1, 
    netT truck22' == 11,
    freeCellsT truck22'' == 0, 
    netT truck22'' == 16  
  ]
  where
    truck_no_route = newT 1 1 (newR [])
    pal = newP "Córdoba" 3  
    truck_no_route' = loadT truck_no_route pal
    
    route = newR ["San Juan", "Mendoza", "La Rioja"]  
    truck1 = newT 3 2 route  
    truck1' = loadT truck1 pal 

    pal1 = newP "Mendoza" 3  
    pal2 = newP "San Juan" 5  
    pal3 = newP "La Rioja" 2
    truck12 = foldl loadT (newT 1 2 route) [pal2, pal1]
    truck21 = foldl loadT (newT 2 1 route) [pal2, pal1]
    truck22 = foldl loadT (newT 2 2 route) [pal2, pal1, pal3]
    truck22' = foldl loadT truck22 [pal1, pal1]
    truck22'' = foldl loadT truck22 [pal2, pal1]


testOverWeightStack :: Bool 
testOverWeightStack = and [
    freeCellsT truck == 2,
    netT truck == 0,       
    freeCellsT truck' == 1, 
    netT truck' == 5,
    freeCellsT truck1 == 0, 
    netT truck1 == 10,
    freeCellsT truck2 == 1, 
    netT truck2 == 16           
  ]
  where
    route = newR ["San Juan", "Mendoza", "La Rioja"]  
    pal1 = newP "San Juan" 4  
    pal2 = newP "Mendoza" 7 
    pal3 = newP "La Rioja" 5  
    pal4 = newP "La Rioja" 11
    pal5 = newP "Mendoza" 6

    truck = loadT (newT 1 2 route) pal4
    truck' = foldl loadT truck [pal3, pal2]
    truck1 = foldl loadT truck [pal5, pal1]
    truck2 = foldl loadT (newT 2 2 route) [pal3, pal2, pal1, pal2]


testUnload :: Bool
testUnload = and [
    freeCellsT truck == 0,  
    netT truck == 14,       
    freeCellsT truck' == 1,  
    netT truck' == 13,  
    freeCellsT truck'' == 4,  
    netT truck'' == 4,   
    freeCellsT truck''' == 6,  
    netT truck''' == 0,
      
    freeCellsT truck2' == 3,  
    netT truck2' == 6,  
    freeCellsT truck2'' == 3,  
    netT truck2'' == 6, 
    freeCellsT truck2''_bad == 3,  
    netT truck2''_bad == 6,  
    freeCellsT truck2''' == 6,  
    netT truck2''' == 0       
  ]
  where
    route = newR ["San Juan", "Mendoza", "La Rioja"]  
    pal1 = newP "San Juan" 1  
    pal2 = newP "Mendoza" 3 
    pal3 = newP "La Rioja" 2  
    truck = foldl loadT (newT 3 2 route) [pal3, pal2, pal3, pal2, pal2, pal1]
    truck' = unloadT truck "San Juan"
    truck'' = unloadT truck' "Mendoza"
    truck''' = unloadT truck'' "La Rioja"

    truck2 = foldl loadT (newT 3 2 route) [pal3, pal3, pal3, pal1]
    truck2' = unloadT truck2 "San Juan"
    truck2'' = unloadT truck2' "Mendoza"
    truck2''_bad = unloadT truck2'' "Buenos Aires"
    truck2''' = unloadT truck2'' "La Rioja"


allTestTruck :: Bool
allTestTruck = and [
    testTruckCreation,
    testNoSpace,
    testIncorrectRoute,
    testOverWeightStack,
    testUnload
  ]
