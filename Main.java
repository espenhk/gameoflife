import java.util.Scanner;
/**
 * Main program of a finite board emulation of Conway's Game of Life.
 * <p>
 * Usage:
 * java Main [dimension] [waitTimeInMSec]
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
public class Main {
    /**
     * Current turn of the game.
     */
    static int turn = 0;

    /**
     * Main program.
     * @param args  command line arguments.
     */
    public static void main(String[] args) {
        GameOfLife game;
        try {
            game = new GameOfLife(Integer.parseInt(args[0]));
        } catch (ArrayIndexOutOfBoundsException e) {
            // default boardsize: 20
            game = new GameOfLife(20);
        }

        int mSecWait;
        try {
            mSecWait = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            mSecWait = 500;
        }

        Scanner scanIn = new Scanner(System.in);

        System.out.println("Welcome to Conway's Game of Life!");

        while (true) {
            printMenu();
            String cmd = scanIn.next();
            switch(cmd) {
                case "1":
                    System.out.println("Enter x coordinate: ");
                    int x = Integer.parseInt(scanIn.next());
                    System.out.println("Enter y coordinate: ");
                    int y = Integer.parseInt(scanIn.next());
                    game.setSquareAt(x, y, true);
                    game.drawGridBoard();
                    break;
                case "2":
                    runGame(game, mSecWait);
                    break;
                case "q":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select one of the menu options!");
                    printMenu();
            }
        }


        /*
        // Set entire col
        for(GameSquare[] col : game.getSquares()) {
            col[0].setState(true);
        }
        // Set single square
        game.setSquareAt(1, 3, true);
        */

    }

    /**
     * Start and run the game until interrupted by ctrl+C.
     *
     * @param game      game board to run.
     * @param mSecWait  milliseconds to wait between turns.
     */
    public final static void runGame(GameOfLife game, int mSecWait) {
        while(true) {
            game.updateBoard();
            System.out.println("=== Turn " + turn++ + " ===");
            game.drawBoard();
            //game.drawGridBoard();
            try {
                Thread.sleep(mSecWait); // sleep 1s
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Print the game menu.
     */
    public final static void printMenu() {
        System.out.println("Select from the menu:");
        System.out.println("[1] Input cells");
        System.out.println("[2] Run Game of Life");
        System.out.println("[q] Quit game");
    }
}
