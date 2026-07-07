package Alternate;

public class TurnState {
    private boolean isFirstTurn = true;
    private int counter = 0;
    private final int maxIterations;

    public TurnState(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public synchronized boolean isMyTurn(boolean targetTurn) {
        return isFirstTurn == targetTurn && counter < maxIterations;
    }

    public synchronized void switchTurn() {
        isFirstTurn = !isFirstTurn;
        counter++;
    }

    public synchronized boolean isFinished() {
        return counter >= maxIterations;
    }
}