import java.io.*;
import java.util.*;
import java.math.BigInteger;

record Part(int x, int m, int a, int s) {
    public int get(String atr) {
        return switch (atr) {
            case "x" -> x;
            case "m" -> m;
            case "a" -> a;
            case "s" -> s;
            default -> -1;
        };
    }

    public int sum() {
        return x + m + a + s;
    }
}

record Rule(String rule, String workflow) {}
record Workflow(String id, List<Rule> rules) {}

public class Main {
    public static void main(String[] args) throws IOException {

        // reading input
        BufferedReader reader = new BufferedReader(
                                    new FileReader("inputData.txt"));

        List<String> lines = reader.lines().toList();
        reader.close();


        //parsing input
        HashMap<String, List<Rule>> workflows = new HashMap<>();
        lines.stream().
                takeWhile(l -> !l.isEmpty()).
                map(Main::parseWorkflow).
                forEach(w -> workflows.put(w.id(), w.rules()));

        List<Part> parts = lines.
                stream().
                dropWhile(l -> !l.isEmpty()).skip(1).
                map(Main::parsePart).toList();


        // part 1
        /*
        // summing all valid parts
        long sum = parts.
                stream().
                filter(p -> isAccepted(p, workflows)).
                map(Part::sum).
                reduce(0, Integer::sum);

        System.out.println(sum);
        */

        // part 2
        BigInteger sum = BigInteger.ZERO;

        for (Map.Entry<String, List<Rule>> e : workflows.entrySet()) {
            int aCount = (int) e.getValue().stream().filter(r -> r.workflow().equals("A")).count();

            if (aCount > 0) {
                for (int i = 0; i < aCount; i++) {
                    Combination p2 = new Combination(e.getKey());
                    BigInteger p = p2.combinations(workflows);

                    sum = sum.add(p);

                    // if current entry is a starting point, we change it, so that the next
                    // start on the same line can be found
                    List<Rule> current = e.getValue();
                    for (int j = 0; j < current.size(); j++) {
                        if (current.get(j).workflow().equals("A")) {
                            current.set(j, new Rule(current.get(j).rule(), "R"));
                            break;
                        }
                    }
                }
            }
        }

        System.out.println(sum);
    }


    static boolean isAccepted(Part p, HashMap<String, List<Rule>> workflows) {
        String id = "in";

        traversal:
        while (true) {
            List<Rule> current = workflows.get(id);

            for (Rule r : current) {
                if (eval(r.rule(), p)) {
                    id = r.workflow();

                    switch (id) {
                        case "R": return false;
                        case "A": return true;
                        default: continue traversal;
                    }
                }
            }
        }
    }

    static Workflow parseWorkflow(String s) {
        String[] line = s.split("[{},]");

        List<Rule> rules = new ArrayList<>();
        for (int i = 1; i < line.length; i++) {
            String r = line[i];

            if (r.contains(":")) {
                String[] split = r.split(":");
                rules.add(new Rule(split[0], split[1]));
            } else {
                rules.add(new Rule("a>0", r)); // a>0 == true
            }
        }

        return new Workflow(line[0], rules);
    }

    static Part parsePart(String str) {
        List<Integer> as = Arrays.
                stream(str.replaceAll("[{}xmas=]", "").
                split(",")).
                map(Integer::parseInt).toList();

        return new Part(as.get(0), as.get(1), as.get(2), as.get(3));
    }

    static boolean eval(String rule, Part p) {
        boolean isGt = rule.contains(">");
        String[] r = rule.split("[<>]");

        if (isGt) return p.get(r[0]) > Integer.parseInt(r[1]);
        else return p.get(r[0]) < Integer.parseInt(r[1]);
    }
}