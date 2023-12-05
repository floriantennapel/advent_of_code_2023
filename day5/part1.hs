import qualified Data.Map as Map
import Data.Map (Map)
import Data.List (isSuffixOf)
import System.Environment (getArgs)

main :: IO ()
main = do
    args <- getArgs
    input <- readFile $ args !! 0
    let ls = lines input
    let seeds = parseSeeds $ ls !! 0
    putStrLn $ show $ lowestLocation ls seeds

lowestLocation :: [String] -> [Int] -> Int
lowestLocation ls = minimum .
                    map (walkPath 7 ls)

parseSeeds :: String -> [Int]
parseSeeds = map read . 
             words . 
             tail . 
             dropWhile (/= ':')

parseMap :: [String] -> Map Int Int
parseMap = Map.unions .
           map (Map.fromList .
                (\l -> take (l!!2) $ zip [l!!1..] [l!!0..]) . 
                map read . 
                words) .
           takeWhile (/= "") .
           tail .
           dropWhile (not . isSuffixOf "map:")

{-
getMaps :: [String] -> [Map Int Int]
getMaps = getMaps' 7  


getMaps' :: Int -> [String] -> [Map Int Int]
getMaps' 0 _ = []
getMaps' n ls = parseMap ls : (getMaps' (n-1) (dropLines ls))
    where
        dropLines = tail . 
                    dropWhile (not . isSuffixOf "map:")
-}

getVal k m = case Map.lookup k m of
                Just v -> v
                Nothing -> k

walkPath :: Int -> [String] -> Int -> Int
walkPath n ls k = case n of
    1 -> getVal k currentMap 
    n -> walkPath (n-1) (dropLines ls) (getVal k currentMap) 
    where
        currentMap = parseMap ls
        dropLines = tail . 
                    dropWhile (not . isSuffixOf "map:")

{-
walkPath :: Int -> [Map Int Int] -> Int
walkPath k (m:[]) = getVal k m
walkPath k (m:ms) = walkPath (getVal k m) ms -}
