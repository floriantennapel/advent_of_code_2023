import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {
    private static final Map<String, Integer> numbers = new HashMap<>(Map.of(
            "zero", 0, "one", 1, "two", 2, "three", 3, "four", 4,
            "five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9
    ));

    public static int sumLines(List<String> lines) {
        return lines.stream().
                map(Part2::calibrationValue).
                reduce(0, Integer::sum);
    }

    private static int calibrationValue(String line) {
        int first = getFirstOrLastNum(line, true);
        int last = getFirstOrLastNum(line, false);

        return Integer.parseInt(String.format("%d%d", first, last));
    }

    private static int getFirstOrLastNum(String line, boolean first) throws RuntimeException {
        char[] chars = line.toCharArray();

        for (int i = first ? 0 : chars.length - 1;
             first ? i < chars.length : i >= 0;
             i += first ? 1 : -1) {

            if (Character.isDigit(chars[i])) {
                return Character.getNumericValue(chars[i]);
            }

            StringBuilder collectedChars = new StringBuilder();

            for (int j = i; j <= i + 5 && j < chars.length; j++) { // longest possible number-word is 5 characters
                collectedChars.append(chars[j]);

                if (numbers.containsKey(collectedChars.toString())) {
                    return numbers.get(collectedChars.toString());
                }
            }
        }

        throw new RuntimeException("no number found on line");
    }

}
