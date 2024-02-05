public class SchematicNumber {
    int n, row, start_col, end_col;

    public SchematicNumber(int n, int row, int start_col, int end_col) {
        this.n = n;
        this.row = row;
        this.start_col = start_col;
        this.end_col = end_col;
    }

    @Override
    public String toString() {
        return String.format("%d", n);
    }
}
