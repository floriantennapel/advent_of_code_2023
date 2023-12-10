import Data.List (elemIndex, findIndex)
import Data.Maybe (fromMaybe)

type Pos = (Int, Int)
data Dir = North | South | West | East
    deriving Eq
 
main :: IO ()
main = do
    input <- readFile "inputData.txt"
    let pipes = lines input
    let start = fromMaybe (-1,-1) $ findS pipes 
    putStrLn $ show $ div (firstStep start pipes) 2 

--We don't really need to check for out-of-bounds since the input is known to be safe
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

firstStep :: Pos -> [[Char]] ->  Int
firstStep start pipes = travel next dir 1 pipes
    where
        dir = South --I cheated ;)
        next = move start dir 

travel :: Pos -> Dir -> Int -> [[Char]] -> Int
travel (i, j) lastDir count pipes
    | pipes !! i !! j == 'S' = count
    | otherwise = travel next dir (count+1) pipes

    where
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
