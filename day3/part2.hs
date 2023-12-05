import Data.Maybe (isJust, fromMaybe)
import Data.List (nub)
import Data.Char (isDigit)

type IndexedNum = (Maybe Int, Int)

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    putStrLn $ show $ sumGears input

--safe version of !!
get :: Int -> [a] -> Maybe a
get i xs | i >= length xs || i < 0 = Nothing
         | otherwise = Just $ xs !! i
         
--input is a string where the first element is known to be a numeric value
parseInt :: String -> (Int, Int) --(value, number of digits)
parseInt line = (read number, length number) 
    where 
        number = takeWhile isDigit line
        
-- lists all numbers on line, including starting index
-- "..58" -> [(Nothing, 0), (Nothing, 1), (Just 58, 2), (Just 58, 2)]
numsOnLine :: String -> [IndexedNum]
numsOnLine = numsOnLine' 0

numsOnLine' :: Int -> String -> [IndexedNum]
numsOnLine' _ []     = []
numsOnLine' i (c:cs) | isDigit c = [(Just val, i) | _ <- [1..len]] ++
                       (numsOnLine' (i+len) (drop len (c:cs)))
                     | otherwise = (Nothing, i) : (numsOnLine' (i+1) cs)
    where
        (val, len) = parseInt (c:cs)

--returns a list of all elements surrounding given position
getAdjacent :: Int -> Int -> [[IndexedNum]] -> [IndexedNum]
getAdjacent row col nums = concat $
                           map (take 3 . drop (col - 1) . fromMaybe []) $
                           filter (isJust) $
                           get (row-1) nums : get row nums : [get (row+1) nums]

--given list of surrounding elements, either returns
--the product of the two surrounding numbers, or 0 if 
--less than two unique numbers are in the list
gearProduct :: [IndexedNum] -> Int
gearProduct =  gearNum . 
               map (fromMaybe 0 . fst) .
               nub .
               filter (isJust . fst)
    where
        gearNum surrounding | length surrounding == 2 = product surrounding
                            | otherwise = 0
                            
--given an engine schematic, returns the sum of all gear-ratios
sumGears :: String -> Int
sumGears input = sumGears' 0 0 (lines input) nums
    where
        nums = map numsOnLine $ lines input 

sumGears' :: Int -> Int -> [String] -> [[IndexedNum]] -> Int
sumGears' _ _ [] _ = 0
sumGears' row col ((c:cs):ls) nums 
    | c == '*' = (gearProduct $ getAdjacent row col nums) + next 
    | otherwise = next
    
    where
        next = case cs of
                 [] -> sumGears' (row+1) 0 ls nums 
                 _  -> sumGears' row (col+1) (cs:ls) nums
