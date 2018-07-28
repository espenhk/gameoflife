/**
 * A run at creating a finite board emulation of Conway's Game of Life.
 * <p>
 * Rules: 
 * 1. Any live cell with fewer than two live neighbours dies, 
 *    as if caused by under-population.
 * 2. Any live cell with two or three live neighbours lives on to the next generation.
 * 3. Any live cell with more than three live neighbours dies, as if by over-population.
 * 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 * @author  Espen H. Kristensen <espenhk@student.matnat.uio.no>
 * @version 0.1
 * @since   2016-03-08
 */

public class GameOfLife {
    GameSquare[][] squares;
    int dimension;

    public GameOfLife(int dimension) {
        this.dimension = dimension;
        this.squares = new GameSquare[dimension][dimension];
        for (int y = 0; y < dimension; y++) {
            for (int x = 0; x < dimension; x++) {
                squares[y][x] = new GameSquare();
            }
        }
    }

    public GameSquare[][] getSquares() {
        return squares;
    }

    public int getDimension() {
        return dimension;
    }

    public GameSquare getSquareAt(int x, int y) {
        return squares[y][x]; 
    }

    public void setSquareAt(int x, int y, boolean state){
        squares[y][x].setState(state);
    }

    // Grid w/o numbering
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

    // Grid w/numbering, for debug
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

    // Calculate next states, update to next states, ready to draw board again
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


        /*
        // NW cell: (x-1, y-1)
        if (checkBounds(x-1, y-1)) {
            if (getSquareAt(x-1, y-1).getState()) {
                sum++;
            }
        }
        // N  cell: (x, y-1)
        if (checkBounds(x, y-1)) {
            if (getSquareAt(x, y-1).getState()) {
                sum++;
            }
        }

        // NE cell: (x+1, y-1)
        if (checkBounds(x+1, y-1)) {
            if (getSquareAt(x+1, y-1).getState()) {
                sum++;
            }
        }

        // W  cell: (x-1, y)
        if (checkBounds(x-1, y)) {
            if (getSquareAt(x-1, y).getState()) {
                sum++;
            }
        }

        // E  cell: (x+1, y)
        if (checkBounds(x+1, y)) {
            if (getSquareAt(x+1, y).getState()) {
                sum++;
            }
        }

        // SW cell: (x-1, y+1)
        if (checkBounds(x-1, y+1)) {
            if (getSquareAt(x-1, y+1).getState()) {
                sum++;
            }
        }

        // S  cell: (x, y+1)
        if (checkBounds(x, y+1)) {
            if (getSquareAt(x, y+1).getState()) {
                sum++;
            }
        }

        // SE cell: (x+1, y+1)
        if (checkBounds(x+1, y+1)) {
            if (getSquareAt(x+1, y+1).getState()) {
                sum++;
            }
        }
        */

        return sum;
    }

    public boolean checkBounds(int x, int y) {
        if (x >= 0 && x < dimension &&
            y >= 0 && y < dimension) {

            return true;
        }
        return false;
    }
}
