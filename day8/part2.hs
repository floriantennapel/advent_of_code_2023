import Data.Map (Map)
import qualified Data.Map as Map
import Data.Char (isAlphaNum, isSpace)
import Data.List (notElem, cycle)

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let dirs = parseDir input
    let nodes = parseNodes input
    putStrLn $ show $ countSteps nodes dirs


parseNodes :: String -> Map String (String, String) 
parseNodes = foldl (\m (k:l:r:[]) -> Map.insert k (l,r) m) Map.empty .
             map (words . filter (\c -> isAlphaNum c || isSpace c)) .
             dropWhile (notElem '=') .
             lines

parseDir :: String -> [Char] --returns infinite cycle
parseDir =  cycle . takeWhile (not . isSpace) 

countSteps :: Map String (String, String) -> String -> Int
countSteps nodeMap dirs = fst $ countSteps' nodeMap dirs (0, startNodes)
    where
        startNodes = filter (\k -> (=='A') $ last k) $ map (\(k,_) -> k) $ Map.toList nodeMap

countSteps' :: Map String (String, String) -> String -> 
               (Int, [String]) -> (Int, [String]) 
countSteps' nodeMap (d:ds) (i, ks) 
    | all (=='Z') $ map last ks = (i, ks) 
    | otherwise = countSteps' nodeMap ds (i+1, nextNodes)

    where
        nextNodes = map unsafeLRLookup ks
        unsafeLRLookup :: String -> String 
        unsafeLRLookup k = case Map.lookup k nodeMap of
                             Just (l, r) -> if d == 'L' then l else r 
                             Nothing -> "" -- should not happen given our data
