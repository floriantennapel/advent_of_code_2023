import Data.List.Utils (replace)

main :: IO ()
main = do
    file <- readFile "calibration.txt"
    putStrLn $ show $ calibrate file

calibrate :: String -> Int 
calibrate = sum .
            map (\cs -> read ((head cs) : [last cs]) :: Int) .
            map (filter (`elem` ['0'..'9'])) .
            lines . 
            wordsToInts

wordsToInts = replace "one" "o1e" .
              replace "two" "t2o" .
              replace "three" "t3e" .
              replace "four" "4" .
              replace "five" "5e" .
              replace "six" "6" .
              replace "seven" "7n" .
              replace "eight" "e8t" .
              replace "nine" "n9e"
