import Data.Char (isDigit)

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    putStrLn $ show $ calibrate input

calibrate :: String -> Int 
calibrate = sum .
            map (\cs -> read $ head cs : [last cs]) .
            map (filter isDigit) . 
            lines
