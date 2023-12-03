import Data.List

--safe versions of !! and tail
get :: Int -> [a] -> Maybe a
get i xs | i >= length xs  || i < 0 = Nothing
         | otherwise = Just $ xs !! i 

safeTail :: [a] -> [a]
safeTail []     = []
safeTail (x:xs) = xs 




--input is a string where the first element is known to be a numeric value
readInt :: String -> (Int, Int) --(value, number of digits)
readInt line = (read number, length number) 
    where 
        number = takeWhile(`elem` ['0'..'9']) line 

getSymbolIndices :: String -> [Int] 
getSymbolIndices = findIndices (`notElem` (['0'..'9'] ++ ['.', '\n']))

--This is so messy, but it works...
readLine :: Int -> Maybe String -> Maybe String -> Maybe Char -> String -> [Int]
readLine _ _ _ _ [] = []
readLine i over under prev (c:cs)  
    | cs /= [] && elem c ['0'..'9'] = 
        let num = readInt (c:cs) 
            val = fst num
            len = snd num
            i_1 = i + len 
            surrounding = case (prev, get (len-1) cs) of
                            (Just p, Just s) -> [p, s]
                            (Nothing, Just s) -> [s]
                            (Just p, Nothing) -> [p]
                            (_, _) -> [] 
            ifAdjacent | any (`elem` symbolIndices) [i-1..i_1] || 
                         any (`notElem` (['0'..'9'] ++ ['.', '\n'])) surrounding 
                         = [val]
                       | otherwise = [] 
            next = drop (len-1) cs
        in ifAdjacent ++ (readLine (i_1+1) over under (Just $ head next) (safeTail next)) 
    | otherwise = readLine (i+1) over under (Just c) cs

    where symbolIndices = case (over, under) of
                            (Just o, Just u) -> getSymbolIndices o ++ (getSymbolIndices u) 
                            (Nothing, Just u) -> getSymbolIndices u
                            (Just o, Nothing) -> getSymbolIndices o
                            (_, _) -> []
                                      
sumLines :: String -> Int
sumLines input = sum $
                 concat $
                 map (\(i, line) -> readLine 0 (get (i-1) lineList) (get (i+1) lineList) Nothing line) $
                 zip [0..] lineList
    where
        lineList = lines input 


