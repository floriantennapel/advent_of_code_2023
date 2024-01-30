import java.util.List;

public class Part1 {
    public static int sumLines(List<String> input) {
        return input.stream().
                map(Part1::calibrationValue).
                reduce(0, Integer::sum);
    }

    private static int calibrationValue(String line) {
        String reversed = (new StringBuilder(line)).reverse().toString();
        char first = getFirstNum(line);
        char last = getFirstNum(reversed);

        return Integer.parseInt(String.format("%c%c", first, last));

    }

    private static char getFirstNum(String line) throws RuntimeException {
        for (char c : line.toCharArray()) {
            if (Character.isDigit(c)) {
                return c;
            }
        }

        throw new RuntimeException("no number found on line");
    }
}
