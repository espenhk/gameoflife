/**
 * A single square of a finite board emulation of Conway's Game of Life.
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

public class GameSquare {
    /**
     * Current state of cell, live (true) or dead (false).
     */
    boolean state;
    /**
     * Upcoming state of cell, live (true) or dead (false).
     */
    boolean nextState;

    /**
     * Create a game square.
     * @param state   state to initialize square at
     */
    public GameSquare(boolean state) { // used to create live cell
        this.state = state;
        this.nextState = state; // TODO necessary? change?
    }

    /**
     * Create a dead game square.
     */
    public GameSquare() {
        this(false);
    }

    /**
     * Set next state to opposite of current.
     * @return next state of square.
     */
    public boolean shiftState() { // TODO return type needed? just void?
        nextState = !state;
        return nextState;
    }

    /**
     * Move forward one step in time, set current state to nextState.
     */
    public void updateState() {
        this.state = getNextState();
        // nextState is now same as state
    }

    /**
     * Get the current square state.
     */
    public boolean getState() {
        return this.state;
    }

    /**
     * Set the current square state, prefer using shiftState and
     * updateState.
     *
     * @param state   State to set.
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Get the upcoming state of the square.
     *
     * @return upcoming state of square.
     */
    public boolean getNextState() {
        return this.nextState;
    }

    /**
     * Set the upcoming state of the square, prefer using shiftState
     * and updateState.
     *
     * @param nextState   state to set.
     */
    public void setNextState(boolean nextState) {
        this.nextState = nextState;
    }

    /**
     * State of square in string form, to draw board. Live is "X",
     * dead blank.
     */
    public String toString() {
        if (state) {
            return "X";
        }
        return " ";
    }
}
