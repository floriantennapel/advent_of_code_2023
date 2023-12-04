sumCards :: [Int] -> [Int] -> Int 
sumCards [] []         = 0
sumCards (m:ms) (n:ns) = n + sumCards ms (addN m n ns) 
    where
        addN nFirsts addAmount cardCount =
            map (+ addAmount) (take nFirsts cardCount) ++
            drop nFirsts cardCount   

parseLines :: String -> [([Int], [Int])]
parseLines = map (tupleSplit . tail . dropWhile (/= ':')) .
             lines
    where
        tupleSplit line = (map read $ words $ takeWhile (/= '|') line, 
                           map read $ words $ tail $ dropWhile (/= '|') line)

getCardCount :: String -> Int
getCardCount input = sumCards matches [1 | _ <- matches] 
    where
        matches = map (\(win, own) -> length $ filter (`elem` win) own) $
                  parseLines input
