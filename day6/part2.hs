main :: IO ()
main = do
    input <- readFile "inputData.txt"
    putStrLn $ show $ 
               betterTimes $
               parseInput input

parseInput :: String -> (Integer, Integer)
parseInput = (\(t:d:[]) -> (t, d)) .
             map (read . 
                  concat . 
                  words . 
                  tail . dropWhile (/= ':')) . 
             lines 

-- using the quadratic equation
betterTimes :: (Integer, Integer) -> Integer
betterTimes (b, c) | even b    = ans - 1 
                   | otherwise = ans  
    where
        b' = fromIntegral b
        c' = fromIntegral c
        e = 0.0001 --accounting for rounding errors
        root :: Double = (b' - (sqrt (b'*b' - 4*c'))) / 2.0 + e
        ans = (div b 2 - (floor root))*2

