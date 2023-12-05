import Data.Char (isDigit)
import Data.Text (replace, pack, unpack)

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    putStrLn $ show $ calibrate input

--a bit verbose, but it works very well
calibrate :: String -> Int
calibrate = sum .
            map (\cs -> read $ (head cs) : [last cs]) .
            map (filter isDigit) .
            lines .
            unpack .
            replace (pack "one") (pack "o1e")  .
            replace (pack "two") (pack "t2o")  .
            replace (pack "three") (pack "t3e") .
            replace (pack "four") (pack "4") .
            replace (pack "five") (pack "5e") .
            replace (pack "six") (pack "6") .
            replace (pack "seven") (pack "7n") .
            replace (pack "eight") (pack "e8t") .
            replace (pack "nine") (pack "n9e") .
            pack
