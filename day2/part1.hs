import qualified Data.Text as Text 
import qualified Data.List as List

lineToCubeList :: String -> [String]
lineToCubeList = map (Text.unpack . Text.strip) . 
                 Text.split (\c -> c == ',' || c == ';') .
                 Text.pack

validCubeCount :: String -> Bool
validCubeCount cubes | List.isSuffixOf "red" cubes = (read (takeWhile (`elem` ['0'..'9']) cubes) :: Int) <= 12
                     | List.isSuffixOf "green" cubes = (read (takeWhile (`elem` ['0'..'9']) cubes) :: Int) <= 13 
                     | List.isSuffixOf "blue" cubes = (read (takeWhile (`elem` ['0'..'9']) cubes) :: Int) <= 14 
                     | otherwise = True --should not happen 

validLines :: String -> [Bool] 
validLines = map (all validCubeCount .
                  lineToCubeList . 
                  tail . 
                  dropWhile (/= ':')) . 
             lines

sumValidLines :: String -> Int
sumValidLines inputData = sum $
                          map snd $
                          filter fst $
                          zip (validLines inputData) [1..]
