import Data.List (transpose, sort)

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let gals = getGalaxies $ lines input 
    putStrLn $ show $ (sumDiffs $ fst gals) + 
                      (sumDiffs $ snd gals)

getGalaxies :: [[Char]] -> ([Integer], [Integer])
getGalaxies css = (\poss -> ((map fst poss),(sort $ map snd poss))) $
                  map (\(_,i,j) -> (i,j)) $
                  filter (\(x,_,_) -> x == '#') $
                  concat $
                  [[(x,i,j) | (x, j) <- zip cs (getRowIndices 0 $ transpose css)] | 
                   (cs, i) <- zip css (getRowIndices 0 css)]

getRowIndices :: Integer -> [[Char]] -> [Integer] 
getRowIndices _ []  = []
getRowIndices i (cs:css) | all (=='.') cs = i:(getRowIndices (i+2) css) 
                         | otherwise = i:(getRowIndices (i+1) css)

-- finds the difference between all combinations of pairs and sums them
sumDiffs :: [Integer] -> Integer
sumDiffs ns = (sum $ zipWith (*) ns [0..]) -
              (sum $ zipWith (*) (reverse ns) [0..])

