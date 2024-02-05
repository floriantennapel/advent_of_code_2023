import java.util.*;
import java.io.*;

public class Main {
    static List<List<Character>> schematic;
    static List<List<SchematicNumber>> numbers;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        List<String> lines = new ArrayList<>(reader.lines().toList());
        reader.close();

        schematic = new ArrayList<>();
        lines.forEach(l -> schematic.add(l.chars().
                mapToObj(c -> (char) c).
                toList())
        );

        numbers = readNumbers();

        System.out.println(Part1.sumParts());
        System.out.println(Part2.sumGears());
    }

    static List<List<SchematicNumber>> readNumbers() {
        // empty 2d List
        List<List<SchematicNumber>> retList = new ArrayList<>();
        for (int i = 0; i < schematic.size(); i++) {
            List<SchematicNumber> row = new ArrayList<>();

            for (int j = 0; j < schematic.get(0).size(); j++) {
                row.add(null);
            }

            retList.add(row);
        }

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

                    SchematicNumber sn = new SchematicNumber(numToAdd, i, start_col, end_col);

                    for (int k = start_col; k <= end_col; k++) {
                        retList.get(i).set(k, sn);
                    }

                    n = new StringBuilder();
                }
            }

            // number on end of line
            if (!n.isEmpty()) {
                int numToAdd = Integer.parseInt(n.toString());
                int start_col = schematic.get(0).size() - n.length();
                int end_col = schematic.get(0).size() - 1;
                SchematicNumber sn = new SchematicNumber(numToAdd, i, start_col, end_col);

                for (int j = start_col; j <= end_col; j++) {
                    retList.get(i).set(j, sn);
                }
            }
        }

        return retList;
    }
}