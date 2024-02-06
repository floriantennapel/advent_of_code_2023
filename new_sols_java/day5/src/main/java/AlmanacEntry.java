import java.util.ArrayList;
import java.util.List;

public class AlmanacEntry {
    List<RangeMap> ranges;

    public AlmanacEntry(List<String> lines) {
        ranges = new ArrayList<>();

        for (String line : lines) {
            RangeMap rm = new RangeMap(line);
            ranges.add(rm);
        }
    }

    /**
     * returns -1 on failure
     */
    public long map(long key) {
        for (RangeMap r : ranges) {
            if (r.canMap(key)) {
                return r.map(key);
            }
        }

        return -1;
    }
}
