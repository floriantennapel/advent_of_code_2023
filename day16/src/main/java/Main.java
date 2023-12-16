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
                        StandardCharsets.US_ASCII
                )
        );
        BeamContraption contraption = new BeamContraption(reader.lines()
                                                                .map(String::toCharArray)
                                                                .toArray(char[][]::new));
        reader.close();

        // part 1
        contraption.traverse(new Beam("East", 0, 0));
        System.out.println(contraption.getEnergized());

        // part 2
        System.out.println(contraption.maxEnergy());
    }
}
