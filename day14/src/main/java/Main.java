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
        board.runCycle(1000); // just a shot in the dark, it did work though :)

        System.out.println(board.getLoad());
    }
}
