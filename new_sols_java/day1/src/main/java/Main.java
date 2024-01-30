import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        List<String> lines = new ArrayList<>(reader.lines().toList());

        System.out.println(Part1.sumLines(lines));
        System.out.println(Part2.sumLines(lines));
    }
}
