import java.util.ArrayList;
import java.util.List;

public class Part1 {

    static long sumParts() {
        List<SchematicNumber> read = new ArrayList<>();
        long sum = 0;

        for (List<SchematicNumber> row : Main.numbers) {
            for (SchematicNumber sn : row) {
                if (sn != null && isPart(sn) && !read.contains(sn)) {
                    sum += sn.n;
                    read.add(sn);
                }
            }
        }

        return sum;
    }

    static boolean isPart(SchematicNumber sn) {
        List<List<Character>> schematic = Main.schematic;

        int startRow = sn.row == 0 ? 0 : sn.row - 1;
        int endRow = sn.row == schematic.size() - 1 ? sn.row : sn.row + 1;
        int startCol = sn.start_col == 0 ? 0 : sn.start_col - 1;
        int endCol = sn.end_col == schematic.get(0).size() - 1 ? sn.end_col : sn.end_col + 1;

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {

                // current cell is not surrounding a cell, but part of it
                if (i == sn.row && j >= sn.start_col && j <= sn.end_col) {
                    continue;
                }

                char current = schematic.get(i).get(j);

                if (current != '.') {
                    return true;
                }
            }
        }

        return false;
    }
}
