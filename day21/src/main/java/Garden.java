import java.util.*;
import java.util.stream.Stream;

record Pos(int i, int j) {}

public class Garden {
    Plot[][] grid;

    public Garden(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        this.grid = new Plot[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.grid[i][j] = new Plot(grid[i][j]);
            }
        }
    }

    // finds starting point and begins the recursive traverseR function
    public void traverse() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                if (grid[i][j].c == 'S') {
                    traverseR(new Pos(i, j), 0);
                    return;
                }
            }
        }
    }

    public int countPossibleEnds() {
        int sum = 0;

        for (Plot[] r : grid) {
            for (Plot c : r) {
                if (c.c == 'O') sum++;
            }
        }

        return sum;
    }

    void traverseR(Pos pos, int stepsTaken) {
        if (stepsTaken == Main.steps+1) {
            return;
        }

        if (stepsTaken % 2 == 0) {
            get(pos).c = 'O';
        } else {
            get(pos).c = '*';
        }

        get(pos).visited = true;

        nextMoves(pos).parallelStream().forEach(p -> traverseR(p, stepsTaken+1));
    }

    Plot get(Pos pos) {
        return grid[pos.i()][pos.j()];
    }

    boolean canMove(String dir, Pos pos) {
        int i = pos.i();
        int j = pos.j();

        return switch (dir) {
            case "North" -> i != 0 && !grid[i-1][j].isRock();
            case "South" -> i != grid.length-1 && !grid[i+1][j].isRock();
            case "West" -> j != 0 && !grid[i][j-1].isRock();
            case "East" -> j != grid[0].length && !grid[i][j+1].isRock();
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

        for (Plot[] r : grid) {
            for (Plot c : r) {
                s.append(c.c);
            }
            s.append('\n');
        }

        return s.toString();
    }
}
