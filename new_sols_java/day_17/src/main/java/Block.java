import java.util.Objects;

record Block(Pos pos, int heatLoss, int dirCount, Dir dir, Block prev) {
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Block o)) {
      return false;
    } else {
      return
          o.pos.equals(pos) &&
          o.dirCount == dirCount &&
          o.dir.equals(dir);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(pos, dirCount, dir);
  }
}
