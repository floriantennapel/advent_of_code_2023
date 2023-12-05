import qualified Data.Text as Text 
import qualified Data.List as List

main :: IO ()
main = do
    input <- readFile "inputData.txt"
    putStrLn $ show $ sumPowers input

lineToCubeList :: String -> [String]
lineToCubeList = map (Text.unpack . Text.strip) . 
                 Text.split (\c -> c == ',' || c == ';') .
                 Text.pack .
                 tail . 
                 dropWhile (/= ':')

parseInt :: String -> Int
parseInt = read . takeWhile (`elem` ['0'..'9'])

getLowestCount :: String -> [String] -> Int
getLowestCount color = maximum .
                       map parseInt .
                       filter (List.isSuffixOf color)

gamePower :: [String] -> Int  
gamePower game = product $
                 [getLowestCount "red" game,
                  getLowestCount "green" game,
                  getLowestCount "blue" game]

sumPowers :: String -> Int 
sumPowers = sum .
            map (gamePower . lineToCubeList) .
            lines
