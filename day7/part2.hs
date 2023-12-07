import Data.List (sort, elemIndex, sortBy)
import Data.Char (isDigit)
import Data.Maybe (fromMaybe)

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
    | a == 'J' = LT
    | b == 'J' = GT
    | otherwise = case (isDigit a, isDigit b) of
                    (False, False) -> compare (elemIndex a faces) (elemIndex b faces) 
                    (_    , _    ) -> compare a b
    where
        faces = "TQKA"

countCards :: [Char] -> [Int] 
countCards cs | elem 'J' cs = countJCards cs
              | otherwise = tail $ scanl (\_ c -> length $ filter (==c) cs) 0 cs

countJCards :: [Char] -> [Int]
countJCards cs
    | all (=='J') cs = [5] 
    | otherwise = (take fstHighI withoutJ) ++ 
                  [(maximum withoutJ) + countJs] ++ 
                  (drop (fstHighI + 1) withoutJ)  
    where fstHighI = fromMaybe 0 $ elemIndex (maximum withoutJ) withoutJ 
          withoutJ = countCards $ filter (/= 'J') cs 
          countJs  = length $ filter (=='J') cs 

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
        --wow, this is even worse than before
        --all of these (seemingly random) requirements are taken from writing down all possible combinations on paper
        --for instance; it just happened to be that all pairs have exactly three one's 
        five = \(cs, _) -> (maximum $ countCards cs) == 5 
        four = \(cs, _) -> (maximum $ countCards cs) == 4 
        house = \(cs, _) -> ((maximum $ countCards cs) == 3) && (notElem 1 $ countCards cs) 
        three = \(cs, _) -> ((maximum $ countCards cs) == 3) && (elem 1 $ countCards cs) 
        twoPair = \(cs, _) -> (4 == (length $ filter (==2) $ countCards cs)) && (notElem 'J' cs)
        pair = \(cs, _) -> (length $ filter (== 1) $ countCards cs) == 3 
        high = \(cs, _) -> all (==1) $ countCards cs 
