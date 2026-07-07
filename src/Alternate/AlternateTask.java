package Alternate;

public class AlternateTask implements Runnable {
    private final String message;
    private final boolean targetTurn;
    private final TurnState state;

    public AlternateTask(String message, boolean targetTurn, TurnState state) {
        this.message = message;
        this.targetTurn = targetTurn;
        this.state = state;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (state) {
                while (!state.isMyTurn(targetTurn)) {
                    if (state.isFinished()) {
                        state.notifyAll();
                        return;
                    }
                    try {
                        state.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (state.isFinished()) {
                    return;
                }

                System.out.print(message);
                state.switchTurn();
                state.notifyAll();
            }
        }
    }
}