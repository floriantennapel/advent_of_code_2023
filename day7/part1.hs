import Data.List (sort, elemIndex, sortBy)
import Data.Char (isDigit)

type HandRank = ([Char], Integer)

main :: IO ()
main = do
    hands <- readFile "inputData.txt"
    putStrLn $ show $ sumRanks $ parseHandRanks hands

sumRanks :: [HandRank] -> Integer
sumRanks = sum . 
           map (\(i, (_, r)) -> r*i) . 
           zip [1..] .
           sortHands

parseHandRanks :: String -> [HandRank]
parseHandRanks = map ((\line -> (line !! 0, read $ line !! 1)) . words) .
                 lines

sortHands :: [HandRank] -> [HandRank]
sortHands = concat .
            map (sortBy compareHands) .
            sortCategories 

compareHands :: HandRank -> HandRank -> Ordering
compareHands ([], _) ([], _) = EQ
compareHands ((a:as), r1) ((b:bs), r2) 
    | a == b = compareHands (as, r1) (bs, r2) 
    | otherwise = case (isDigit a, isDigit b) of
                    (False, False) -> compare (elemIndex a faces) (elemIndex b faces) 
                    (_    , _    ) -> compare a b
    where
        faces = "TJQKA"

countCards :: [Char] -> [Int] 
countCards cs = tail $ scanl (\_ c -> length $ filter (==c) cs)  0 cs

--a monster of a function
sortCategories :: [HandRank] -> [[HandRank]] 
sortCategories hs = filter high hs : 
                    filter pair hs :
                    filter twoPair hs :
                    filter three hs :
                    filter house hs :
                    filter four hs :
                    filter five hs :
                    [] 
    where 
        five = \(c:cs, _) -> all (==c) cs 
        four = \(cs  , _) -> elem 4 $ countCards cs
        house = \(cs , _) -> (elem 3 $ countCards cs) && (elem 2 $ countCards cs) 
        three = \(cs , _) -> (elem 3 $ countCards cs) && (elem 1 $ countCards cs)
        twoPair = \(cs, _) -> 4 == (length $ filter (==2) $ countCards cs)
        pair = \(cs  , _) -> (elem 2 $ countCards cs) && (3 == (length $ filter (==1) $ countCards cs)) 
        high = \(cs   , _) -> all (==1) $ countCards cs
