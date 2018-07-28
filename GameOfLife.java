/**
 * The game board of a finite board emulation of Conway's Game of Life.
 * <p>
 * Rules: 
 * <ol>
 * <li>Any live cell with fewer than two live neighbours dies, 
 * as if caused by under-population.</li>
 * <li>Any live cell with two or three live neighbours lives on to the
 * next generation.</li>
 * <li>Any live cell with more than three live neighbours dies, as if by
 * over-population.</li>
 * <li>Any dead cell with exactly three live neighbours becomes a live
 * cell, as if by reproduction.</li>
 * </ol>
 * </p>
 * @author  Espen H. Kristensen <espenhk@student.matnat.uio.no>
 * @version 1.0
 * @since   2016-03-08
 */

public class GameOfLife {
    /**
     * Double array of the game squares.
     */
    GameSquare[][] squares;
    /**
     * Size of the board. Board will always be a square, n x n.
     */
    int dimension;

    /**
     * Construct a new Game of Life board.
     *
     * @param dimension   The dimension of the board, default 20 x 20.
     */
    public GameOfLife(int dimension) {
        this.dimension = dimension;
        this.squares = new GameSquare[dimension][dimension];
        for (int y = 0; y < dimension; y++) {
            for (int x = 0; x < dimension; x++) {
                squares[y][x] = new GameSquare();
            }
        }
    }

    /**
     * @return the squares of the board in a double array.
     */
    public GameSquare[][] getSquares() {
        return squares;
    }

    /**
     * @return the dimension n of the board, board is n x n.
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * Get a specific GameSquare from the board.
     *
     * @param x   x dimension to look up.
     * @param y   y dimension to look up.
     * @return    square at position (x, y) in the board.
     */
    public GameSquare getSquareAt(int x, int y) {
        return squares[y][x]; 
    }

    /**
     * Set a certain square to live or dead.
     *
     * @param x     x dimension to set at.
     * @param y     y dimension to set at.
     * @param state true for live cell, false for dead.
     */
    public void setSquareAt(int x, int y, boolean state){
        squares[y][x].setState(state);
    }

    /**
     * Draw the board without grid. Used while running the game.
     */
    public void drawBoard() {
        // top row
        for (int i = 0; i < dimension; i++) {
            System.out.print("    ");
        }
        System.out.println(" ");
        for (GameSquare[] row : squares) {
            // cell row
            for (GameSquare square : row) {
                System.out.printf("  %s ", square);
            }
            System.out.println(" "); // end line

            // below cell row
            for (int i = 0; i < dimension; i++) {
                System.out.print("    ");
            }
            System.out.println(" "); // end line
        }
    }

    /**
     * Draw board with grid and numbering. Used for printing the board
     * when inputting squares, and for debugging purposes.
     */
    public void drawGridBoard() {
        int rowCount = 0;
        // top numbering row
        System.out.print("   ");
        for (int colCount = 0; colCount < dimension; colCount++) {
            System.out.print("  " + colCount%10 + " ");
        }
        System.out.println();
        // top row
        System.out.print("   ");
        for (int i = 0; i < dimension; i++) {
            System.out.print("|---");
        }
        System.out.println("|");
        for (GameSquare[] col : squares) {
            // cell row
            System.out.print(" " + rowCount%10 + " ");
            for (GameSquare square : col) {
                System.out.printf("| %s ", square);
            }
            System.out.println("|"); // end line

            // below cell row
            System.out.print("   ");
            for (int i = 0; i < dimension; i++) {
                System.out.print("|---");
            }
            System.out.println("|"); // end line
            rowCount++;
        }
    }

    /**
     * Updates next state of the board following the rules of Conway's
     * Game of Life.
     * <p>
     * Calculate next states, update to next states, ready to draw board
     * again. Rules:
     * <ol>
     * <li>Any live cell with fewer than two live neighbors dies, as if by
     * under population.</li>
     * <li>Any live cell with two or three live neighbors lives on to the
     * next generation.</li>
     * <li>Any live cell with more than three live neighbors dies, as if
     * by overpopulation.</li>
     * <li>Any dead cell with exactly three live neighbors becomes a live
     * cell, as if by reproduction.</li>
     * </ol>
     * </p>
     */
    public void updateBoard() {
        int neighbours;
        // Calculate new states
        for (int y = 0; y < dimension; y++) {
            for (int x = 0; x < dimension; x++) {
                // do sth for each cell
                GameSquare curr = getSquareAt(x, y);
                neighbours = sumNeighbours(x, y);

                if (curr.getState()) { // live cell
                    // less than two or more than three neighbours to a live cell kills it.
                    if (neighbours < 2 || neighbours > 3) {
                        curr.setNextState(false);
                    } else {
                        curr.setNextState(true);
                    }

                } else { // dead cell
                    // exactly three neighbours to a dead cell awakes it.
                    if (neighbours == 3) {
                        curr.setNextState(true);
                    } else {
                        curr.setNextState(false);
                    }
                }
                // DEBUG LINE
                /*
                System.out.printf("At square (%d, %d), neighbours: %d), state: %s, nextState: %s\n", 
                                    x, y, neighbours, curr.getState(), curr.getNextState());
                */

            } // end x loop
        } // end y loop

        // Update to new states
        for (GameSquare[] col : squares) {
            for (GameSquare square : col) {
                square.updateState();
            }
        }
    }

    /**
     * Sum up number of live neighbours, follwing the rules of
     * Conway's Game of Life.
     *
     * @param x   x coordinate of square
     * @param y   y coordinate of square
     * @return    number of live neighbours.
     */
    public int sumNeighbours(int x, int y) {
        int sum = 0;

        for (int x_ = x-1; x_ <= x+1; x_++) {
            for (int y_ = y-1; y_ <= y+1; y_++) {
                if ((x_ == x) && (y_ == y)) {
                    continue;
                }
                if (checkBounds(x_, y_)) {
                    if (getSquareAt(x_, y_).getState()) {
                        sum++;
                    }
                }
            }
        }

        return sum;
    }

    /**
     * Check if an (x,y) pair exists within the bounds of the board.
     *
     * @param x   x coordinate to check.
     * @param y   y coordinate to check.
     * @return    true for within board, false for outside.
     */
    public boolean checkBounds(int x, int y) {
        if (x >= 0 && x < dimension &&
            y >= 0 && y < dimension) {

            return true;
        }
        return false;
    }
}
