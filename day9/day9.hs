main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let ls = parseLines input 
    putStrLn $ show $ sum $ map getNext ls
    putStrLn $ show $ sum $ map (getNext . reverse) ls

parseLines :: String -> [[Integer]]
parseLines = map (map read . words) . lines

getNext :: (Eq a, Num a) => [a] -> a
getNext = last . getNext'

getNext' :: (Eq a, Num a) => [a] -> [a]
getNext' (n:ns) | all (==n) ns = n:n:ns 
                | otherwise = n:(ns ++ [next])  
    where
        diffs = zipWith (-) ns (n:ns)
        next = (last (n:ns)) + (last $ getNext' diffs) 

