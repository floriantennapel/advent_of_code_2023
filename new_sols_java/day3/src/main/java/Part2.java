import java.util.ArrayList;
import java.util.List;

public class Part2 {
    public static Integer sumGears() {
        int sum = 0;

        for (int i = 0; i < Main.schematic.size(); i++) {
            for (int j = 0; j < Main.schematic.get(0).size(); j++) {

                if (Main.schematic.get(i).get(j) == '*') {
                    sum += gearValue(i, j);
                }
            }
        }

        return sum;
    }

    /** @return gear value, 0 if entry is not a gear */
    private static int gearValue(int row, int col) {
        int startRow = row == 0 ? 0 : row - 1;
        int startCol = col == 0 ? 0 : col - 1;
        int endRow = row == Main.schematic.size() - 1 ? row : row + 1;
        int endCol = col == Main.schematic.get(0).size() - 1 ? col : col + 1;

        List<SchematicNumber> surrounding = new ArrayList<>();

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                SchematicNumber numberEntry = Main.numbers.get(i).get(j);
                if (numberEntry != null && !surrounding.contains(numberEntry)) {
                    surrounding.add(numberEntry);
                }
            }
        }

        if (surrounding.size() == 2) {
            return surrounding.get(0).n * surrounding.get(1).n;
        } else {
            return 0;
        }
    }
}
