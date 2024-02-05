import java.util.*;
import java.io.*;

public class Main {
    static List<List<Character>> schematic;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        List<String> lines = new ArrayList<>(reader.lines().toList());
        reader.close();

        schematic = new ArrayList<>();
        lines.forEach(l -> schematic.add(l.chars().
                mapToObj(c -> (char) c).
                toList())
        );

        List<SchematicNumber> numbers = readNumbers();

        System.out.println(Part1.sumParts(numbers));
    }

    static List<SchematicNumber> readNumbers() {
        List<SchematicNumber> retList = new ArrayList<>();

        for (int i = 0; i < schematic.size(); i++) {
            StringBuilder n = new StringBuilder();

            for (int j = 0; j < schematic.get(0).size(); j++) {
                char current = schematic.get(i).get(j);

                if (Character.isDigit(current)) {
                    n.append(current);
                }

                if (!Character.isDigit(current) && !n.isEmpty()) {
                    int numToAdd = Integer.parseInt(n.toString());
                    int start_col = j - n.length();
                    int end_col = j - 1;

                    retList.add(new SchematicNumber(numToAdd, i, start_col, end_col));

                    n = new StringBuilder();
                }
            }

            // number on end of line
            if (!n.isEmpty()) {
                int numToAdd = Integer.parseInt(n.toString());
                int start_col = schematic.get(0).size() - n.length();
                int end_col = schematic.get(0).size() - 1;

                retList.add(new SchematicNumber(numToAdd, i, start_col, end_col));
            }
        }

        return retList;
    }
}