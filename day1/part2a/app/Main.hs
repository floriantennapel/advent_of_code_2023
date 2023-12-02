import Data.List.Utils (replace)

main :: IO ()
main = do
    fil <- readFile "calibration.txt"
    putStrLn $ show $ calibrate fil

calibrate :: String -> Int 
calibrate = sum .
            map (\cs -> read ((head cs) : [last cs]) :: Int) .
            map (filter (`elem` ['0'..'9'])) .
            lines . 
            wordsToInts

wordsToInts = replace "one" "o1e" .
              replace "two" "t2o" .
              replace "three" "th3ee" .
              replace "four" "fo4r" .
              replace "five" "fi5e" .
              replace "six" "s6x" .
              replace "seven" "se7en" .
              replace "eight" "ei8ht" .
              replace "nine" "ni9e"
