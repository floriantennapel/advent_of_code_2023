calibrate :: String -> Int 
calibrate = sum .
            map (\cs -> read $ head cs : [last cs]) .
            map (filter (`elem` ['0'..'9'])) . 
            lines
