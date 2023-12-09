main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let ls = parseLines input 
    putStrLn $ show $ sum $ map getPrev ls

parseLines :: String -> [[Integer]]
parseLines = map (map read . words) . lines

getPrev :: (Eq a, Num a) => [a] -> a
getPrev = head . getPrev'

getPrev' :: (Eq a, Num a) => [a] -> [a]
getPrev' (n:ns) | all (==n) ns = n:n:ns 
                | otherwise = prev:n:ns 
    where
        diffs = map (\(a,b) -> b-a) $ zip (n:ns) ns 
        prev = n - (head $ getPrev' diffs) 

