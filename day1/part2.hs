import Data.List (isInfixOf)

calibrate :: String -> Int 
calibrate = sum .
            map (\cs -> read (firstNum "" cs : [lastNum cs ""]) :: Int) .
            lines

numInString :: String -> Char
numInString cs | any (`isInfixOf` cs) ["one", "1"]   = '1' 
               | any (`isInfixOf` cs) ["two", "2"]   = '2'
               | any (`isInfixOf` cs) ["three", "3"] = '3'
               | any (`isInfixOf` cs) ["four", "4"]  = '4'
               | any (`isInfixOf` cs) ["five", "5"]  = '5'
               | any (`isInfixOf` cs) ["six", "6"]   = '6'
               | any (`isInfixOf` cs) ["seven", "7"] = '7'
               | any (`isInfixOf` cs) ["eight", "8"] = '8'
               | any (`isInfixOf` cs) ["nine", "9"]  = '9'
               | otherwise = '0'

firstNum :: String -> String -> Char
firstNum fsts []   = numInString fsts
firstNum fsts rest = case numInString fsts of
                         '0' -> firstNum (fsts ++ [head rest]) (tail rest)
                         num -> num 

lastNum :: String -> String -> Char
lastNum [] lasts   = numInString lasts 
lastNum rest lasts = case numInString lasts of  
                         '0' -> lastNum (init rest) ((last rest) : lasts)
                         num -> num 
