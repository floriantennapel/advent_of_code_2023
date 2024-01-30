import java.util.List;

public class Part1 {
    public static int sumLines(List<String> input) throws RuntimeException {
        return input.stream().
                map(Part1::calibrationValue).
                reduce(0, Integer::sum);
    }

    /**
     * @param line
     * @return calibration value of line, -1 on failure
     */
    private static int calibrationValue(String line) throws RuntimeException {
        String reversed = (new StringBuilder(line)).reverse().toString();
        char first = getFirstNum(line);
        char last = getFirstNum(reversed);

        return Integer.parseInt(String.format("%c%c", first, last));

    }

    private static char getFirstNum(String line) {
        for (char c : line.toCharArray()) {
            if (Character.isDigit(c)) {
                return c;
            }
        }

        throw new RuntimeException("no number found on line");
    }
}
