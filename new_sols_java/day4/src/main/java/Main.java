import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        List<String> lines = reader.lines().toList();
        reader.close();

        List<CardLine> cardLines = lines.stream().map(CardLine::new).toList();

        System.out.println(part1(cardLines));
        System.out.println(part2(cardLines));
    }

    static int part1(List<CardLine> cardLines) {
        return cardLines.stream().
                mapToInt(cl -> (int) Math.pow(2, countMatches(cl) - 1)).
                sum();
    }

    static int part2(List<CardLine> cardLines) {
        for (int i = 0; i < cardLines.size(); i++) {
            CardLine current = cardLines.get(i);
            int matches = countMatches(current);

            int maxSucIndex = Math.min(i + matches, cardLines.size() - 1);

            for (int j = i + 1; j <= maxSucIndex; j++) {
                cardLines.get(j).addCopies(current.getCopies());
            }
        }

        return cardLines.stream().
                mapToInt(CardLine::getCopies).
                sum();
    }

    static int countMatches(CardLine line) {
        int matches = 0;

        for (int ownNum : line.own) {
            if (line.winning.contains(ownNum)) {
                ++matches;
            }
        }

        return matches;
    }
}
