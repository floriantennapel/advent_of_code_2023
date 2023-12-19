// solution inspired by HyperNeutrino
// https://www.youtube.com/watch?v=bGWK76_e-LM

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

record Point(long x, long y) {}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                                    new FileReader("inputData.txt"));

        List<String[]> lines = reader.lines().
                                      map(l -> Arrays.stream(l.split(" ")).
                                                      toArray(String[]::new)).
                                      toList();
        reader.close();

        List<Point> points = new ArrayList<>(List.of(new Point(0, 0)));

        // part 1
        // -------------------------------------------
        /*
        long edgeCount = 0;

        for (String[] l : lines) {
            String dir = l[0];
            int n = Integer.parseInt(l[1]);

            points.add(move(dir, n, points.getLast()));
            edgeCount += n;
        }
        */
        // -------------------------------------------


        // part 2
        // -------------------------------------------
        long edgeCount = 0;

        String[] dirs = {"R", "D", "L", "U"};
        for (String[] l : lines) {
            String clean = l[2].replaceAll("[()#]", "");
            int splitAt = clean.length() - 1;
            String[] code = {clean.substring(0, splitAt), clean.substring(splitAt)};

            String dir = dirs[Integer.parseInt(code[1])];
            long n = Integer.parseInt(code[0], 16);

            points.add(move(dir, n, points.getLast()));
            edgeCount += n;
        }
        // -------------------------------------------

        long a = shoelace(points);

        System.out.println(lagoonArea(a, edgeCount));
    }

    static Point move(String dir, long n, Point p) {
        return switch (dir) {
            case "U" -> new Point(p.x() - n, p.y());
            case "D" -> new Point(p.x() + n, p.y());
            case "L" -> new Point(p.x(), p.y() - n);
            case "R" -> new Point(p.x(), p.y() + n);
            default -> p;
        };
    }

    // shoelace formula A = 1/2 * ABS {SUM {x_i(y_(i+1) - y_(i-1))}}
    // https://en.wikipedia.org/wiki/Shoelace_formula
    static long shoelace(List<Point> points) {
        long sum = 0;

        for (int i = 0; i < points.size(); i++) {
            long x = points.get(i).x();
            long y_next = i == points.size()-1 ? points.getFirst().y() : points.get(i+1).y();
            long y_prev = i == 0 ? points.getLast().y() : points.get(i-1).y();

            sum += x * (y_next - y_prev);
        }

        return Math.abs(sum) / 2;
    }

    // total area of lagoon using Pick's theorem i = A - b/2 + 1
    // https://en.wikipedia.org/wiki/Pick%27s_theorem
    // total size will be i + b, where A is found using the shoelace formula
    static long lagoonArea(long a, long b) {
        return a - b/2 + 1 + b;
    }

}
