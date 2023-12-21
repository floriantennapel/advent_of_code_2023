import java.io.*;
import java.util.stream.Stream;

public class Main {
    final static int steps = 64;

    public static void main(String[] args) throws IOException {
        Stream<String> lines = new BufferedReader(new FileReader("inputData.txt")).lines();

        Garden garden = new Garden(lines.map(String::toCharArray).toArray(char[][]::new));

        System.out.println(garden.traverse());
        System.out.println(garden);
    }
}