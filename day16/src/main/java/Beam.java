public class Beam {
    public String dir;
    public int i;
    public int j;

    public Beam(String dir, int i, int j) {
        this.dir = dir;
        this.i = i;
        this.j = j;
    }

    public void move() {
        switch (dir) {
            case "North" -> --i;
            case "South" -> ++i;
            case "East" -> ++j;
            case "West" -> --j;
        }
    }
}
