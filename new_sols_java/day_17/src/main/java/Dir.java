enum Dir {
  N, S, E, W;

  /** assumes only movement in cardinal directions, no diagonal movement */
  public static Dir getDir(Pos from, Pos to) {
    if (from.row() < to.row()) {
      return S;
    } else if (from.row() > to.row()) {
      return N;
    } else if (from.col() < to.col()) {
      return E;
    } else if (from.col() > to.col()){
      return W;
    } else {
      throw new IllegalArgumentException("from and to must be different positions");
    }
  }

  public static Pos getDeltaPos(Dir dir) {
    return switch(dir) {
      case N -> new Pos(-1,  0);
      case S -> new Pos( 1,  0);
      case E -> new Pos( 0,  1);
      case W -> new Pos( 0, -1);
    };
  }

  public Dir getOpposite() {
    return switch (this) {
      case N -> S;
      case S -> N;
      case E -> W;
      case W -> E;
    };
  }
}