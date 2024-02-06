public class RangeMap {
    long startKey, startVal, rangeLength, toAdd;

    public RangeMap(String line) {
        String[] splitted = line.split("\\s+");

        startKey = Long.parseLong(splitted[1]);
        startVal = Long.parseLong(splitted[0]);
        rangeLength = Long.parseLong(splitted[2]);

        toAdd = startVal - startKey;
    }

    boolean canMap(long key) {
        long mapping = key - startKey;

        return mapping >= 0 && mapping < rangeLength;
    }

    /**
     * Must only be called after canMap
     */
    long map(long key) {
        return key + toAdd;
    }
}
