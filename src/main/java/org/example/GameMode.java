package org.example;

import javax.swing.JOptionPane;

public abstract class GameMode {
    protected final GameLogic gameLogic;

    public GameMode(GameLogic gameLogic) {
        if (gameLogic == null) {
            throw new IllegalArgumentException("GameLogic cannot be null");
        }
        this.gameLogic = gameLogic;
    }

    public abstract boolean checkWinner(int row, int col, String currentPlayerLetter);

    public abstract void gameEnd(int row, int col, boolean madeSOS);

    public void handleGameOver(String message) {
        JOptionPane.showMessageDialog(null, message);
        gameLogic.clearBoard();
    }

    public void handleTurnSwitch() {
        gameLogic.switchPlayerTurn();
    }
}
