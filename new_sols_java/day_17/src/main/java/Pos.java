public record Pos(int row, int col) {
  public Pos add(Pos pos) {
    return new Pos(
        row + pos.row(),
        col + pos.col()
    );
  }
}
