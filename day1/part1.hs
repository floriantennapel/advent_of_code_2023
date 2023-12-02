calibrate :: String -> Int 
calibrate = sum .
            map (\cs -> read (head cs : [last cs]) :: Int) .
            map (filter (`elem` ['0'..'9'])) . 
            lines
