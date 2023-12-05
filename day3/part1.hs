import Data.Maybe (isJust, fromMaybe)
import Data.Char (isDigit)

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    putStrLn $ show $ sumParts input

--safe version of !!
get :: Int -> [a] -> Maybe a
get i xs | i >= length xs || i < 0 = Nothing
         | otherwise = Just $ xs !! i

--input is a string where the first element is known to be a numeric value
parseInt :: String -> (Int, Int) --(value, number of digits)
parseInt line = (read number, length number)
    where
        number = takeWhile isDigit line

--returns a list of all elements surrounding length of elements starting from a given point
getAdjacent :: Int -> Int -> Int -> [[Char]] -> [Char]
getAdjacent row col len ref =
    concat $
    map (take takeAmount . drop dropAmount . fromMaybe []) $
    filter (isJust) $
    get (row-1) ref : get row ref : [get (row+1) ref]
    
    where
        (takeAmount, dropAmount) | col == 0  = (len+1, 0)
                                 | otherwise = (len+2, col-1)

sumParts :: String -> Int
sumParts input = sumParts' 0 0 lineList lineList
    where lineList = lines input

sumParts' :: Int -> Int -> [String] -> [String] -> Int
sumParts' _ _ [] _ = 0
sumParts' row col ((c:cs):ls) ref
    | isDigit c = if isPart $ getAdjacent row col len ref
                    then val + next
                    else next
    | otherwise = next

    where
        (val, len) = parseInt (c:cs)
        isPart = any (`notElem` (['0'..'9'] ++ ['.', '\n']))
        next | cs == [] || len >= length cs =
                sumParts' (row+1) 0 ls ref
             | isDigit c =
                sumParts' row (col+len) ((drop len (c:cs)):ls) ref
             | otherwise =
                sumParts' row (col+1) (cs:ls) ref
