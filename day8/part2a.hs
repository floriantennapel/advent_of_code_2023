import Data.Map (Map)
import qualified Data.Map as Map
import Data.Char (isAlphaNum, isSpace)
import Data.List (notElem, cycle)

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let dirs = parseDir input
    let nodes = parseNodes input
    putStrLn $ show $ foldl1 lcm $ countSteps nodes dirs


parseNodes :: String -> Map String (String, String) 
parseNodes = foldl (\m (k:l:r:[]) -> Map.insert k (l,r) m) Map.empty .
             map (words . filter (\c -> isAlphaNum c || isSpace c)) .
             dropWhile (notElem '=') .
             lines

parseDir :: String -> [Char] --returns infinite cycle
parseDir =  cycle . takeWhile (not . isSpace) 

countSteps :: Map String (String, String) -> String -> [Int]
countSteps nodeMap dirs = map (\k -> fst $ countSteps' nodeMap dirs (0, k)) startNodes
    where startNodes = filter (\k -> (=='A') $ last k) $ 
                       map fst $ 
                       Map.toList nodeMap

countSteps' :: Map String (String, String) -> String -> (Int, String) -> (Int, String) 
countSteps' _ _ (i, _:_:'Z':"") = (i, "")
countSteps' nodeMap (d:ds) (i, k) = 
    case Map.lookup k nodeMap of
      Just (l, r) -> if d == 'L' 
                        then countSteps' nodeMap ds (i+1, l) 
                        else countSteps' nodeMap ds (i+1, r)
      _ -> (0, "") --should not happen 

