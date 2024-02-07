import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* EXPLANATION OF SOLUTION
*
* The formula for the distance d(t) given time t and total race time T is:
*   d(t) = t * (T - t) = -t² + Tt
* Given any record R we can find the hold time of that record using the quadratic equation:
*   T -/+ sqrt(T² - 4R) / 2
* The number of record-breaking times is simply the all digits in-between these two answers.
 */

public class Main {
    static final List<Integer> time = new ArrayList<>(
            Arrays.asList(49, 78, 79, 80)
    );
    static final List<Integer> distance = new ArrayList<>(
            Arrays.asList(298, 1185, 1066, 1181)
    );


    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }

    static long part1() {
        long product = 1;

        for (int i = 0; i < time.size(); i++) {
            product *= waysToBeat(time.get(i), distance.get(i));
        }

        return product;
    }

    static long part2() {
        long time = listToInt(Main.time);
        long dist = listToInt(Main.distance);

        return waysToBeat(time, dist);
    }

    static long listToInt(List<Integer> l) {
        StringBuilder s = new StringBuilder();
        l.forEach(s::append);

        return Long.parseLong(s.toString());
    }

    static double recordHoldTime(long time, long dist) {
        // solving quadratic equation
        return (time - Math.sqrt(time * time - 4 * dist)) / 2.0;
    }

    static long waysToBeat(long time, long dist) {
        double record = recordHoldTime(time, dist);
        int firstOver = (int) Math.ceil(record + 0.01); // record of 6.999 should give firstOver of 8, not 7

        long firstHalf = time / 2 - firstOver;

        return (time % 2 == 0)
                ? firstHalf * 2 + 1
                : (firstHalf + 1) * 2;
    }
}
