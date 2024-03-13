import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day17 {
  private static final String FILE_NAME = "input.txt";

  private final int[][] city;
  private final Set<Block> visited;
  private final int rows;
  private final int cols;
  private final int minMoves;
  private final int maxMoves;

  public static void main(String[] args) {
    Day17 part1 = new Day17(0, 3);
    System.out.println(part1.traverse());
  }

  public Day17(int minMoves, int maxMoves) {
    this.minMoves = minMoves;
    this.maxMoves = maxMoves;
    visited = new HashSet<>();
    city = parseInput();
    rows = city.length;
    cols = city[0].length;
  }

  private int traverse() {
    Queue<Block> pq = new PriorityQueue<>(Comparator.comparingInt(Block::heatLoss));

    // initializing queue
    for (Dir dir : List.of(Dir.E, Dir.S)) {
      Pos pos = Dir.getDeltaPos(dir);
      pq.add(new Block(
          pos,
          city[pos.row()][pos.col()],
          1,
          dir,
          null
      ));
    }

    while (true) {
      Block current = pq.poll();
      visited.add(current);
      //System.out.println("at " + current);

      if (current == null) {
        throw new RuntimeException("Could not find endpoint");
      }
      if (current.pos().equals(new Pos(rows - 1, cols - 1))) {
        printPath(current);
        return current.heatLoss();
      }

      pq.addAll(getNext(current));
    }
  }

  private Set<Block> getNext(Block from) {
    Dir fromDir = from.dir();
    int dirCount = from.dirCount();

    if (dirCount < minMoves) {
      Pos pos = from.pos();
      if (isValidPos(pos.add(Dir.getDeltaPos(fromDir)))) {
        return new HashSet<>(Set.of(newBlock(from, from.dir())));
      } else {
        return new HashSet<>();
      }
    }

    Set<Block> next = new HashSet<>();

    for (Dir dir : Dir.values()) {
      if (dir.getOpposite().equals(fromDir)) {
        continue;
      }

      if (dir.equals(fromDir) && from.dirCount() >= maxMoves) {
        continue;
      }

      Pos nextPos = from.pos().add(Dir.getDeltaPos(dir));

      if (!isValidPos(nextPos)) {
        continue;
      }

      Block b = newBlock(from, dir);
      if (!visited.contains(b)) {
        next.add(b);
      }
    }

    return next;
  }

  private boolean isValidPos(Pos pos) {
    int r = pos.row();
    int c = pos.col();

    return r >= 0 && r < rows && c >= 0 && c < cols;
  }

  private int[][] parseInput() {
    try {
      InputStream inputStream = Day17.class.getClassLoader().getResourceAsStream(FILE_NAME);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      List<String> lines = reader.lines().toList();
      inputStream.close();

      int rows = lines.size();
      int cols = lines.get(0).length();

      int[][] city = new int[rows][cols];
      for (int i = 0; i < rows; i++) {
        String line = lines.get(i);
        for (int j = 0; j < cols; j++) {
          city[i][j] = Character.getNumericValue(line.charAt(j));
        }
      }

      return city;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Block newBlock(Block from, Dir dir) {
    Pos to = from.pos().add(Dir.getDeltaPos(dir));

    return new Block(
        to,
        from.heatLoss() + city[to.row()][to.col()],
        dir == from.dir() ? from.dirCount() + 1 : 1,
        dir,
        from
    );
  }

  private void printPath(Block end) {
    char[][] path = new char[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        //path[i][j] = Character.forDigit(city[i][j], 10);
        path[i][j] = '.';
      }
    }

    Block current = end;
    while (current != null) {
      char c = switch(current.dir()) {
        case N -> '^';
        case S -> 'V';
        case E -> '>';
        case W -> '<';
      };

      path[current.pos().row()][current.pos().col()] = c;

      current = current.prev();
    }

    for (char[] line : path) {
      System.out.println(new String(line));
    }
  }
}
