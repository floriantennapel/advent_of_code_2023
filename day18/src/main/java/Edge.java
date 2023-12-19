public class Edge {
    String dir;

    public Edge(String dir) {
        this.dir = dir;
    }

    public boolean isCorner() {
        return dir.length() == 2;
    }
}
