import Data.List (transpose, sort)

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let diffs = getDiffs $ expand $ lines input 
    putStrLn $ show $ (sumDiffs $ fst diffs) + 
                      (sumDiffs $ snd diffs)

expand :: [[Char]] -> [[Char]]
expand = transpose .
         expandRows .
         transpose . 
         expandRows
    where
        expandRows = concat . 
                     map (\r -> if all (=='.') r 
                                   then replicate 1000000 r 
                                   else [r])

getDiffs :: [[Char]] -> ([Int], [Int])
getDiffs xs = (\poss -> ((map fst poss),(sort $ map snd poss))) $
              map (\(_,i,j) -> (i,j)) $
              filter (\(x,_,_) -> x == '#') $
              concat $
              [[(x,i,j) | (x,j) <- zip row [0..]] | (row, i) <- zip xs [0..]]

-- finds the difference between all combinations of pairs and sums them
sumDiffs :: [Int] -> Int
sumDiffs ns = (sum $ zipWith (*) ns [0..]) -
              (sum $ zipWith (*) (reverse ns) [0..])
