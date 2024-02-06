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
        long lowest = Long.MAX_VALUE;

        for (int i = 0; i < seeds.size(); i += 2) {
            long start = seeds.get(i);
            long range = seeds.get(i+1);

            for (long seed = start; seed <= start + range; seed++) {
                lowest = Math.min(lowest, getLocation(seed));
            }
        }

        return lowest;
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
