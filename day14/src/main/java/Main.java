import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("inputData.txt");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),
                        StandardCharsets.UTF_8
                )
        );
        RollBoard board = new RollBoard(reader.lines().map(String::toCharArray).toArray(char[][]::new));
        reader.close();

        // part1
        // board.fullRoll("North");
        // System.out.println(board.getLoad());

        // part2

        // after first stabilizing the pattern repeats with a cycle length of 42
        // after some quick checks, 118 is the first number in this sequence, followed by 160, 202, 244...
        // 34 and 76 are too small, and the cycle is not yet stable
        board.runCycle(118); // 118 = 1000000000 - 23809521*42
        System.out.println(board.getLoad());
    }
}
