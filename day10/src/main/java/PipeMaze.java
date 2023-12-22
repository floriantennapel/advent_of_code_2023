import java.util.List;

record Pos(int i, int j) {}

public class PipeMaze {
    char[][] grid;
    boolean[][] mainLoop;

    public PipeMaze(char[][] grid) {
        this.grid = grid;
        mainLoop = new boolean[grid.length][grid[0].length];
    }

    public int traverse() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                if (grid[i][j] == 'S') {
                    return traverse(new Pos(i, j));
                }
            }
        }

        return 0;
    }

    int traverse(Pos start) {
        String dir = "South";

        int steps = 1;
        mainLoop[start.i()][start.j()] = true;
        Pos pos = move(dir, start);

        while (grid[pos.i()][pos.j()] != 'S') {
            mainLoop[pos.i()][pos.j()] = true;

            dir = switch (grid[pos.i()][pos.j()]) {
                case '7' -> dir.equals("East") ? "South" : "West";
                case 'F' -> dir.equals("West") ? "South" : "East";
                case 'J' -> dir.equals("East") ? "North" : "West";
                case 'L' -> dir.equals("West") ? "North" : "East";
                default -> dir;
            };

            pos = move(dir, pos);
            steps++;
        }

        return steps / 2;
    }

    public int countContained() {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                if (isInside(new Pos(i, j))) {
                    count++;
                    grid[i][j] = '#'; // for printing/debugging
                }
            }
        }

        return count;
    }

    // For every point that is not part of the outer edge, sends a ray to the right and counts
    //  the number of vertical lines of the outer edge the ray hits. Only bottom corners are counted.
    // If the number of vertical edges hit is odd, we know that the current point is contained by the main loop.
    boolean isInside(Pos pos) {
        if (mainLoop[pos.i()][pos.j()]) {
            return false;
        }

        int edgeCount = 0;

        for (int j = pos.j() + 1; j < grid[0].length; j++) {
            if (List.of('L', '|', 'J').contains(grid[pos.i()][j]) && mainLoop[pos.i()][j]) {
                edgeCount++;
            }
        }

        return edgeCount % 2 == 1;
    }

    Pos move(String dir, Pos pos) {
        int i = pos.i();
        int j = pos.j();

        return switch (dir) {
            case "North" -> new Pos(i - 1, j);
            case "South" -> new Pos(i + 1, j);
            case "West" -> new Pos(i, j - 1);
            case "East" -> new Pos(i, j + 1);
            default -> pos;
        };
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (char[] r : grid) {
            for (char c : r) {
                s.append(c);
            }

            s.append('\n');
        }

        return s.toString();
    }
}
