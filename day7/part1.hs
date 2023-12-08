import Data.List (sort, elemIndex, sortBy)
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
compareHands (as,_) (bs,_) = compare (toNums as) (toNums bs)
    where
        toNums = map (fromMaybe 0 . flip elemIndex cards)
        cards = "23456789TJQKA"

countCards :: [Char] -> [Int] 
countCards cs = map (\c -> length $ filter (==c) cs) cs

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
        five = \(cs,_)    -> elem 5 $ countCards cs 
        four = \(cs,_)    -> elem 4 $ countCards cs 
        house = \(cs,_)   -> matchHand cs [2,2,3,3,3] 
        three = \(cs,_)   -> matchHand cs [1,1,3,3,3] 
        twoPair = \(cs,_) -> matchHand cs [1,2,2,2,2] 
        pair = \(cs,_)    -> matchHand cs [1,1,1,2,2] 
        high = \(cs,_)    -> all (==1) $ countCards cs 

        matchHand cs hand = (sort $ countCards cs) == hand 
