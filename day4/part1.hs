parseLines :: String -> [([Int], [Int])]
parseLines = map (tupleSplit . tail . dropWhile (/= ':')) .
             lines
    where
        tupleSplit line = (map read $ words $ takeWhile (/= '|') line, 
                           map read $ words $ tail $ dropWhile (/= '|') line)

sumCards :: String -> Int
sumCards = sum .
           map (\(win, own) -> points $ filter (`elem` win) own) .
           parseLines
    where
        points matches | length matches == 0 = 0
                       | otherwise = 2^(length matches - 1)  
