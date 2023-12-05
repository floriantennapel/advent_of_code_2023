import Data.List (isSuffixOf)

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let ls = lines input
    let mappings = getAllMappings 7 ls
    let seeds = parseSeeds $ ls !! 0
    let minLocation = minimum $ map (seedToLocation mappings) $ seeds
    putStrLn $ show minLocation
        

parseSeeds :: String -> [Int]
parseSeeds = map read . 
             words . 
             tail . 
             dropWhile (/= ':')

parseMapping :: [String] -> [[Int]]
parseMapping = map (map read . words) .
               takeWhile (/= "") .
               tail .
               dropWhile (not . isSuffixOf "map:")

getAllMappings :: Int -> [String] -> [[[Int]]]
getAllMappings 0 _  = []
getAllMappings n ls = parseMapping ls : (getAllMappings (n-1) next)
    where
        next = tail $ 
               dropWhile (not . isSuffixOf "map:") $
               ls

getVal :: [[Int]] -> Int -> Int
getVal [] k = k
getVal ((dst:src:ran:[]):ls) k | diff >= 0 && diff < ran = dst + diff 
                               | otherwise = getVal ls k
    where
        diff = k - src

seedToLocation :: [[[Int]]] -> Int -> Int
seedToLocation (m:[]) k = getVal m k
seedToLocation (m:ms) k = seedToLocation ms (getVal m k) 
