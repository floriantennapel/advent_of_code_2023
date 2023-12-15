import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("inputData.txt");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),
                        StandardCharsets.US_ASCII
                )
        );
        String input = reader.readLine();
        reader.close();

        String[] steps = input.split(",");

        // part 1
        // int sum = Arrays.stream(steps).map(Main::hash).reduce(0, Integer::sum);
        // System.out.println(sum);

        // part 2
        LensBoxes boxes = new LensBoxes(steps);
        System.out.println(boxes.sumLenses());
    }

    /* static int hash(String s) {
        int sum = 0;

        for (char c : s.toCharArray()) {
            sum += (int) c;
            sum *= 17;
            sum %= 256;
        }

        return sum;
    } */
}
