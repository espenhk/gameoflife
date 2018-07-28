public class GameSquare {
    boolean state;
    boolean nextState;

    public GameSquare(boolean state) { // used to create live cell
        this.state = state;
        this.nextState = state; // TODO necessary? change?
    }

    public GameSquare() {
        this(false);
    }

    // Set next state to opposite of current
    public boolean shiftState() { // TODO return type needed? just void?
        nextState = !state;
        return getNextState();
    }

    // Move forward one step in time
    public void updateState() {
        this.state = getNextState();
        // nextState is now mirror of state
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getNextState() {
        return this.nextState;
    }

    public void setNextState(boolean nextState) {
        this.nextState = nextState;
    }

    public String toString() {
        if (state) {
            return "X";
        }
        return " ";
    }
}
