public class BeamContraption {
    char[][] grid;
    String[][] energized;

    public BeamContraption(char[][] grid) {
        this.grid = grid;
        energized = new String[grid.length][grid[0].length];
    }

    public long getEnergized() {
        long sum = 0;
        for (String[] l : energized) {
            for (String s : l) {
                if (s != null) ++sum;
            }
        }
        return sum;
    }

    long maxEnergy() {
        long hi = 0;
        long eCount = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                // only checking edge positions
                if (i != 0 && i != grid.length - 1 && j != 0 && j != grid[0].length - 1) {
                    continue;
                }

                if (i == 0) {
                    traverse(new Beam("South", i, j));
                } else if (i == grid.length - 1) {
                    traverse(new Beam("North", i, j));
                }
                eCount = getEnergized();
                clear();
                hi = Math.max(hi, eCount);

                if (j == 0) {
                    traverse(new Beam("East", i, j));
                } else if (j == grid[0].length - 1) {
                    traverse(new Beam("West", i, j));
                }
                eCount = getEnergized();
                clear();
                hi = Math.max(hi, eCount);
            }
        }

        return hi;
    }

    public void traverse(Beam b) {
        while (true) {

            // checking for repeated cycles
            if (!(energized[b.i][b.j] == null) && energized[b.i][b.j].equals(b.dir)) {
                break;
            }
            energized[b.i][b.j] = b.dir;

            // animation, I only got this to work in linux
            /* System.out.print("\033[H\033[2J");
            System.out.println(this);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } */
            
            // changing direction for next move
            if (grid[b.i][b.j] == '|' && (b.dir.equals("East") || b.dir.equals("West"))) {
                traverse(new Beam("North", b.i, b.j));
                b.dir = "South";
            } else if (grid[b.i][b.j] == '-' && (b.dir.equals("North") || b.dir.equals("South"))) {
                traverse(new Beam("West", b.i, b.j));
                b.dir = "East";
            } else if (grid[b.i][b.j] == '/') {
                switch (b.dir) {
                    case "North" -> b.dir = "East";
                    case "South" -> b.dir = "West";
                    case "East" -> b.dir = "North";
                    case "West" -> b.dir = "South";
                }
            } else if (grid[b.i][b.j] == '\\') {
                switch (b.dir) {
                    case "North" -> b.dir = "West";
                    case "South" -> b.dir = "East";
                    case "East" -> b.dir = "South";
                    case "West" -> b.dir = "North";
                }
            }
            
            if (!canMove(b)) break;
            b.move();
        }
    }

    boolean canMove(Beam b) {
        return switch (b.dir) {
            case "North" -> !(b.i == 0);
            case "South" -> !(b.i == grid.length - 1);
            case "East" -> !(b.j == grid[0].length - 1);
            case "West" -> !(b.j == 0);
            default -> false;
        };
    }

    void clear() {
        for (int i = 0; i < energized.length; i++) {
            for (int j = 0; j < energized[0].length; j++) {
                energized[i][j] = null;
            }
        }
    }
}
