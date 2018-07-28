
import java.util.Scanner;

public class Main {
    static int turn = 0;
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


        //game.drawBoard();
        //game.drawGridBoard();

        //runGame(game);

    }

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

    public final static void printMenu() {
        System.out.println("Select from the menu:");
        System.out.println("[1] Input cells");
        System.out.println("[2] Run Game of Life");
        System.out.println("[q] Quit game");
    }
}
