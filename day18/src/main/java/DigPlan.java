import java.util.List;
import java.util.Arrays;

public class DigPlan {
    Edge[][] grid;
    String[][] instrs;

    public DigPlan(String[] lines) {
        instrs = Arrays.stream(lines).
                map(l -> Arrays.stream(l.split("\\s|\\(|\\)")).
                        filter(s -> !s.isEmpty()).
                        toArray(String[]::new)).
                toArray(String[][]::new);

        int maxUp = 0;
        int maxDown = 0;
        int maxLeft = 0;
        int maxRight = 0;

        int hor = 0;
        int ver = 0;

        for (String[] l : instrs) {
            // part 1
            /*
            String dir = l[0];
            int count = Integer.parseInt(l[1]); */

            String dir = switch (l[2].substring(l[2].length() - 1)) {
                case "0" -> "R";
                case "1" -> "D";
                case "2" -> "L";
                case "3" -> "U";
                default -> "N";
            };

            int count = Integer.parseInt(l[2].substring(1, l[2].length() - 1), 16);

            for (int i = count; i > 0; i--) {
                switch (dir) {
                    case "U" -> ver--;
                    case "D" -> ver++;
                    case "L" -> hor--;
                    case "R" -> hor++;
                }
            }

            maxUp = Math.min(maxUp, ver);
            maxDown = Math.max(maxDown, ver);
            maxLeft = Math.min(maxLeft, hor);
            maxRight = Math.max(maxRight, hor);
        }

        grid = new Edge[maxDown-maxUp+1][maxRight-maxLeft+1];

        int i = -maxUp;
        int j = -maxLeft;

        String prev = "U";
        for (String[] l : instrs) {

            // part 1
            /*
            String dir = l[0];
            int count = Integer.parseInt(l[1]); */

            String dir = switch (l[2].substring(l[2].length() - 1)) {
                case "0" -> "R";
                case "1" -> "D";
                case "2" -> "L";
                case "3" -> "U";
                default -> "N";
            };

            int count = Integer.parseInt(l[2].substring(1, l[2].length() - 1), 16);

            for (int c = count; c > 0; c--) {
                if (c == count) {
                    grid[i][j] = new Edge(prev+dir); //first element is always a corner
                } else {
                    grid[i][j] = new Edge(dir);
                }

                switch (dir) {
                    case "U" -> i--;
                    case "D" -> i++;
                    case "L" -> j--;
                    case "R" -> j++;
                }
            }

            prev = dir;
        }
    }

    public long trenchSize() {
        long size = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Edge e = grid[i][j];

                if (e != null || isInside(i, j)) size++;
            }
        }

        return size;
    }

    private boolean isInside(int i, int j) {
        if (grid[i][j] != null) {
            return false;
        }

        List<String> verticals = Arrays.asList("UR", "LD", "RD", "UL", "U", "D");
        int n = 0;

        for (; j < grid[i].length; j++) {
            Edge e = grid[i][j];

            if (e == null || !verticals.contains(e.dir)) {
                continue;
            }

            n++;
        }

        return n % 2 == 1;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != null) {
                    s.append('#');
                } else if (isInside(i, j)) {
                    s.append('*');
                } else {
                    s.append('.');
                }
            }
            s.append('\n');
        }

        return s.toString();
    }
}
