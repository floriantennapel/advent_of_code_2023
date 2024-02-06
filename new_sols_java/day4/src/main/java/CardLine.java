import java.util.Arrays;
import java.util.List;

public class CardLine {
    List<Integer> winning;
    List<Integer> own;
    private int copies = 1;

    public CardLine(String line) {
        String[] splitLine = line.split("[:|]");
        winning = Arrays.stream(splitLine[1].strip().split("\\s+")).
                map(Integer::parseInt).
                toList();
        own = Arrays.stream(splitLine[2].strip().split("\\s+")).
                map(Integer::parseInt).
                toList();
    }

    public void addCopies(int n) {
        copies += n;
    }

    public int getCopies() {
        return copies;
    }

    @Override
    public String toString() {
        return copies + ": " + winning + " | " + own;
    }
}
