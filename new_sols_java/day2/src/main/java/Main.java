import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        List<String> lines = new ArrayList<>(
                reader.lines().toList()
        );
        reader.close();

        part1(lines);
        part2(lines);
    }


    static void part1(List<String> lines) {
        int gameSum = 0;

        for (int i = 0; i < lines.size(); i++) {
            if (isValidGame(lines.get(i))) {
                gameSum += i + 1;
            }
        }

        System.out.println(gameSum);
    }

    static void part2(List<String> lines) {
        System.out.println(
                lines.stream().
                        map(l -> highestCubeCounts(l).
                                values().stream().
                                reduce(1, (acc, n) -> acc * n)). // calculate power of set
                        reduce(0, Integer::sum)
        );
    }

    static boolean isValidGame(String line) {
        Map<String, Integer> highestCubeCounts = highestCubeCounts(line);

        return (
                highestCubeCounts.get("red") <= 12 &&
                        highestCubeCounts.get("green") <= 13 &&
                        highestCubeCounts.get("blue") <= 14
        );
    }

    static Map<String, Integer> highestCubeCounts(String line) {
        String noLineNumber = line.split(":")[1].strip();
        List<String> cubes = Arrays.stream(noLineNumber.split("[;,]")).map(String::strip).toList();

        Map<String, Integer> retMap = new HashMap<>();

        for (String cube : cubes) {
            String[] split = cube.split(" ");
            int currentCount = Integer.parseInt(split[0]);

            int highestSoFar = retMap.getOrDefault(split[1], 0);

            if (currentCount > highestSoFar) {
                retMap.put(split[1], currentCount);
            }
        }

        return retMap;
    }
}
