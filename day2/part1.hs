import qualified Data.Text as Text 
import qualified Data.List as List

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    putStrLn $ show $ sumValidLines input

lineToCubeList :: String -> [String]
lineToCubeList = map (Text.unpack . Text.strip) . 
                 Text.split (\c -> c == ',' || c == ';') .
                 Text.pack .
                 tail .
                 dropWhile (/= ':')
                 
parseInt :: String -> Int
parseInt cubes = read $ takeWhile (`elem` ['0'..'9']) cubes

validCubeCount :: String -> Bool
validCubeCount cubes | List.isSuffixOf "red" cubes = parseInt cubes <= 12
                     | List.isSuffixOf "green" cubes = parseInt cubes <= 13 
                     | List.isSuffixOf "blue" cubes = parseInt cubes <= 14 
                     | otherwise = True --should not happen 

validLines :: String -> [Bool] 
validLines = map (all validCubeCount . lineToCubeList) . 
             lines

sumValidLines :: String -> Int
sumValidLines inputData = sum $
                          map snd $
                          filter fst $
                          zip (validLines inputData) [1..]
