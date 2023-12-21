public class Plot {
    char c;
    boolean visited = false;

    public Plot(char c) {
        this.c = c;
    }

    public boolean isRock() {
        return c == '#';
    }
}
