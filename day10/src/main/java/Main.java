import java.io.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        Stream<String> lines = new BufferedReader(new FileReader("inputData.txt")).lines();

        PipeMaze maze = new PipeMaze(lines.map(String::toCharArray).toArray(char[][]::new));

        // part 1
        System.out.println("part 1: " + maze.traverse());

        // part 2
        // relies on the traverse function in part 1 to work
        System.out.println("part 2: " + maze.countContained());
    }
}
