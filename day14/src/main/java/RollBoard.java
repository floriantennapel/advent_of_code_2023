public class RollBoard {
    char[][] board;

    public RollBoard(char[][] board) {
        this.board = board;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (char[] r : board) {
            for (char c : r) {
                ret.append(c);
            }
            ret.append('\n');
        }
        return ret.toString();
    }
    
    public int getLoad() {
        int counter = 0;
        int load = board.length;

        for (char[] r : board) {
            for (char c : r) {
                if (c == 'O') counter += load;
            }
            load--;
        }

        return counter;
    }

    public void runCycle(long n) {
        String[] dirs = {"North", "West", "South", "East"};

        for (int i = 0; i < n; i++) {
            for (String dir : dirs) {
                fullRoll(dir);
            }

            // looking for cyclic patterns
            // after stabilizing, every number is repeated after 42 cycles
            // System.out.println(i+1 + " " + getLoad());
        } 
    }

    public void fullRoll(String dir) {
        while (true) {
            if (rollOnce(dir) == 0) break;
        }
    }

    boolean canRoll(int i, int j, String dir) {
        switch (dir) {
            case "North" -> {
                return i != 0 && board[i - 1][j] == '.';
            }
            case "South" -> {
                return i != board.length - 1 && board[i + 1][j] == '.';
            }
            case "West" -> {
                return j != 0 && board[i][j - 1] == '.';
            }
            case "East" -> {
                return j != board[0].length - 1 && board[i][j + 1] == '.';
            }
            default -> {
                System.out.println("invalid direction");
                System.exit(1);
                return false;
            }
        }
    }

    int rollOnce(String dir) {
        int counter = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (board[i][j] == 'O' && canRoll(i, j, dir)) {
                    counter++;

                    switch (dir) {
                        case "North" -> {
                            board[i - 1][j] = 'O';
                            board[i][j] = '.';
                        }
                        case "South" -> {
                            board[i + 1][j] = 'O';
                            board[i][j] = '.';
                        }
                        case "West" -> {
                            board[i][j - 1] = 'O';
                            board[i][j] = '.';
                        }
                        case "East" -> {
                            board[i][j + 1] = 'O';
                            board[i][j] = '.';
                        }
                        default -> {
                            System.out.println("invalid direction");
                            System.exit(1);
                        }
                    }
                }
            }
        }

        return counter;
    }
}
