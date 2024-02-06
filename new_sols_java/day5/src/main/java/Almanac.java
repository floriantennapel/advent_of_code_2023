import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Almanac {
    List<AlmanacEntry> mappings;
    List<Long> seeds;

    public Almanac(List<Long> seeds, List<AlmanacEntry> mappings) {
        this.mappings = mappings;
        this.seeds = seeds;
    }

    public long part1() {
        long lowest = Long.MAX_VALUE;

        for (long seed : seeds) {
            lowest = Math.min(lowest, getLocation(seed));
        }

        return lowest;
    }

    public long part2() {
        List<List<Long>> seedPairs = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i += 2) {
            List<Long> pair = Arrays.asList(seeds.get(i), seeds.get(i+1));
            seedPairs.add(pair);
        }

        return seedPairs.parallelStream().mapToLong(pair -> {
            long start = pair.get(0);
            long range = pair.get(1);

            long lowest = Long.MAX_VALUE;

            for (long seed = start; seed <= start + range; seed++) {
                lowest = Math.min(lowest, getLocation(seed));
            }

            return lowest;
        }).min().getAsLong();
    }

    private long getLocation(long seed) {
        long current = seed;

        for (AlmanacEntry ae : mappings) {
            long mapping = ae.map(current);
            if (mapping != -1) {
                current = mapping;
            }
        }

        return current;
    }
}
