import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The problem is quite different from part 2, so it gets its own file.

public class Combination {

    // an array of ranges for every attribute, from min to max
    // the total count of possible combinations is the product of each of the ranges
    long[][] xmas;

    String id;

    public Combination(String startId) {
        xmas = new long[4][2];
        for (int i = 0; i < xmas.length; i++) {
            xmas[i][0] = 1L;
            xmas[i][1] = 4000L;
        }

        this.id = startId;
    }

    // finds the number of combinations for a single possible path, starting from the end and moving backwards
    public BigInteger combinations(HashMap<String, List<Rule>> map) {
        if (map.get(id).stream().noneMatch(r -> r.workflow().equals("A"))) {
            return BigInteger.ZERO;
        }

        String target = "A";

        while (true) {
            for (Rule r : map.get(id)) {
                if (r.workflow().equals(target)) {
                    changeRange(r, true);
                    target = id;

                    id = getNextId(map);
                    break;
                } else {
                    changeRange(r, false);
                }
            }

            if (target.equals("in")) {
                break;
            }
        }

        BigInteger product = BigInteger.ONE;
        for (long[] r : xmas) {
            product = product.multiply(BigInteger.valueOf(r[1] - r[0] + 1));
        }

        return product;
    }

    // mutates the xmas variable depending on the rule set given
    //and if the new range should make the rule true or false
    void changeRange(Rule r, boolean shouldBe) {
        String[] l = r.rule().split("[<>]");
        int i = List.of("x", "m", "a", "s").indexOf(l[0]);

        if (r.rule().contains(">")) {
            if (shouldBe) {
                xmas[i][0] = Math.max(Long.parseLong(l[1]) + 1, xmas[i][0]);
            } else {
                xmas[i][1] = Math.min(Long.parseLong(l[1]), xmas[i][1]);
            }
        } else if (r.rule().contains("<")) {
            if (shouldBe) {
                xmas[i][1] = Math.min(Long.parseLong(l[1]) - 1, xmas[i][1]);
            } else {
                xmas[i][0] = Math.max(Long.parseLong(l[1]), xmas[i][0]);
            }
        }
    }

    String getNextId(HashMap<String, List<Rule>> map) {
        for (Map.Entry<String, List<Rule>> e : map.entrySet()) {
            if (e.getValue().stream().anyMatch(r -> r.workflow().equals(id))) {
                return e.getKey();
            }
        }

        return ""; // should not happen
    }
}