import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("testData.txt");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),
                        StandardCharsets.UTF_8
                )
        );
        String[] lines = reader.lines().toArray(String[]::new);
        reader.close();

        DigPlan plan = new DigPlan(lines);

        System.out.println(plan.trenchSize());

    }
}

