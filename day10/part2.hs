import Data.List (elemIndex, findIndex)
import Data.Maybe (fromMaybe)

type Pos = (Int, Int)
data Dir = North | South | West | East
    deriving (Eq, Show) 

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let pipes = lines input
    let start = fromMaybe (-1,-1) $ findS pipes 
    let outline = getOutline start pipes 
    putStrLn $ show $ countInside outline 
 
-- (isOutline, direction)
update :: Pos -> Maybe Dir -> [[(Bool, Maybe Dir)]] -> [[(Bool, Maybe Dir)]] 
update (i,j) dir bss = (take i bss) ++ 
                       [(take j row) ++ [(True, dir)] ++ (drop (j+1) row)] ++ 
                       (drop (i+1) bss)  
    where
        row = bss !! i

-- We don't really need to check for out-of-bounds since the input is known to be safe
move :: Pos -> Dir -> Pos 
move (i, j) North = (i-1, j  ) 
move (i, j) South = (i+1, j  ) 
move (i, j) West  = (i  , j-1) 
move (i, j) East  = (i  , j+1)

findS :: [[Char]] -> Maybe Pos
findS pipes = do
    i <- findIndex (any (=='S')) pipes
    j <- elemIndex 'S' (pipes !! i) 
    return (i, j)

countInside :: [[(Bool, Maybe Dir)]] -> Int
countInside outline = sum $ 
                      map (length . 
                           filter (\(isOutline, edgeCount) -> odd edgeCount && not isOutline) . 
                           insides) 
                      outline 
    where
        insides line = (init $ 
                        scanr (\(o, dir) (_, c) -> case dir of 
                                                     Just North -> (o, c+1)
                                                     Just South -> (o, c-1)
                                                     Nothing -> (o, c)) 
                              (False, 0) line) 

getOutline :: Pos -> [[Char]] -> [[(Bool, Maybe Dir)]] 
getOutline start pipes = getOutline' next dir pipes (update start (Just dir) empty) 
    where
        dir = South --I cheated ;)
        next = move start dir 
        empty = [[(False, Nothing) | _ <- pipes !! 0] | _ <- pipes]

getOutline' :: Pos -> Dir -> [[Char]] -> [[(Bool, Maybe Dir)]] -> [[(Bool, Maybe Dir)]] 
getOutline' (i, j) lastDir pipes outline 
    | pipes !! i !! j == 'S' = outline 
    | otherwise = getOutline' next dir pipes (update (i,j) mDir outline) 

    where
        mDir = if elem (pipes !! i !! j) "|F7" 
                        then if elem dir [North, South] then Just dir else Just lastDir 
                        else Nothing
        (next, dir) = case pipes !! i !! j of
                        '|' -> if lastDir == South
                                  then (move (i,j) South, South) 
                                  else (move (i,j) North, North)
                        '-' -> if lastDir == East
                                  then (move (i,j) East, East) 
                                  else (move (i,j) West, West) 
                        'L' -> if lastDir == South 
                                  then (move (i,j) East, East) 
                                  else (move (i,j) North, North)
                        'J' -> if lastDir == South 
                                  then (move (i,j) West, West) 
                                  else (move (i,j) North, North)
                        '7' -> if lastDir == North 
                                  then (move (i,j) West, West) 
                                  else (move (i,j) South, South) 
                        'F' -> if lastDir == North 
                                  then (move (i,j) East, East) 
                                  else (move (i,j) South, South)
