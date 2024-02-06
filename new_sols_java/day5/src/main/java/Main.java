import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

        String firstLine = reader.readLine();
        List<String> lines = reader.lines().toList();

        reader.close();

        List<Long> seeds = parseFirstLine(firstLine);
        List<AlmanacEntry> mappings = getMappings(lines);

        Almanac almanac = new Almanac(seeds, mappings);

        System.out.println(almanac.part1());
        System.out.println(almanac.part2());
    }

    static List<Long> parseFirstLine(String line) {
        String numbers = line.split(":")[1].strip();

        return Arrays.stream(numbers.split("\\s+")).
                map(Long::parseLong).
                toList();
    }

    static List<AlmanacEntry> getMappings(List<String> lines) {
        List<AlmanacEntry> retList = new ArrayList<>();

        List<String> currentEntries = new ArrayList<>();
        for (String line : lines) {
            if (line.isBlank() && !currentEntries.isEmpty()) {
                retList.add(new AlmanacEntry(currentEntries));
                currentEntries.clear();
            }

            if (!line.contains(":") && !line.isBlank()) {
                currentEntries.add(line.strip());
            }
        }

        if (!currentEntries.isEmpty()) {
            retList.add(new AlmanacEntry(currentEntries));
        }

        return retList;
    }
}
