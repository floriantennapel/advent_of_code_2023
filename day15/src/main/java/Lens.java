public class Lens {
    public String label;
    public int focalLength = -1;
    boolean isRm;

    public Lens(String s) {
        if (s.contains("-")) {
            isRm = true;
            label = s.split("-")[0];
        } else {
            String[] split = s.split("=");
            label = split[0];
            focalLength = Integer.parseInt(split[1]);
            isRm = false;
        }
    }

    @Override
    public String toString() {
        return String.format("[%s %d]", label, focalLength);
    }

    public int hash() {
        int sum = 0;

        for (char c : label.toCharArray()) {
            sum += (int) c;
            sum *= 17;
            sum %= 256;
        }

        return sum;
    }
}
