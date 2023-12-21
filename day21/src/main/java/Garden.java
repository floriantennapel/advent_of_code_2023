import java.util.*;
import java.util.stream.Stream;

record Pos(int i, int j) {}

public class Garden {
    char[][] grid;

    public Garden(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        this.grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(grid[i], 0, this.grid[i], 0, cols);
        }
    }

    // finds starting point
    int traverse() {
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
        int endPoints = 1;

        Map<Pos, Integer> level = new HashMap<>();
        level.put(start, 0);

        List<Pos> frontier = List.of(start);
        for (int i = 1; i <= Main.steps; i++) {
            List<Pos> next = new ArrayList<>();

            for (Pos u : frontier) {
                for (Pos v : nextMoves(u)) {
                    if (!level.containsKey(v)) {
                        if (i % 2 == 0) {
                            grid[v.i()][v.j()] = 'O';
                            endPoints++;
                        }

                        level.put(v, i);
                        next.add(v);
                    }
                }
            }

            frontier = next;
        }

        return endPoints;
     }

    boolean canMove(String dir, Pos pos) {
        int i = pos.i();
        int j = pos.j();

        return switch (dir) {
            case "North" -> i != 0 && grid[i-1][j] != '#';
            case "South" -> i != grid.length-1 && grid[i+1][j] != '#';
            case "West" -> j != 0 && grid[i][j-1] != '#';
            case "East" -> j != grid[0].length-1 && grid[i][j+1] != '#';
            default -> false;
        };
    }

    Pos move(String dir, Pos pos) {
        int i = pos.i();
        int j = pos.j();

        return switch (dir) {
            case "North" -> new Pos(i-1, j);
            case "South" -> new Pos(i+1, j);
            case "East" -> new Pos(i, j+1);
            case "West" -> new Pos(i, j-1);
            default -> pos;
        };
    }

    List<Pos> nextMoves(Pos pos) {
        return Stream.of("North", "South", "East", "West").
                filter(d -> canMove(d, pos)).
                map(d -> move(d, pos)).
                toList();
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
