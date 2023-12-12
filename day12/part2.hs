import Control.Monad (mapM) 

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let ls = map parseLine $ lines input
    print $ sum $ map (\(cs, ns) -> countPossible ns cs) ls

parseLine :: String -> ([Char], [Int])
parseLine input = (cs, toNumList $ tail ns)
    where
        (cs, ns) = span (/=' ') input

toNumList :: String -> [Int]
toNumList "" = [] 
toNumList cs = (read n) : toNumList (dropWhile (== ',') rest) 
    where
        (n, rest) = break (==',') cs

isValid :: [Int] -> [Char] -> Bool
isValid [] []     = True
isValid [] rest   = all (=='.') rest
isValid (n:ns) cs = (length hashes) == n &&
                    isValid ns rest
    where
        stripped = dropWhile (/='#') cs 
        (hashes, rest) = span (=='#') stripped

allCmbs :: [Char] -> [[Char]]
allCmbs xs = mapM replace xs
  where
    replace '?' = ".#"
    replace c   = [c]

countPossible :: [Int] -> [Char] -> Int
countPossible ns = length . filter (isValid ns) . allCmbs 
