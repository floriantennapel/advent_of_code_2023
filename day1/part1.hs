import Data.Char (isDigit)

calibrate :: String -> Int 
calibrate = sum .
            map (\cs -> read $ head cs : [last cs]) .
            map (filter isDigit) . 
            lines
