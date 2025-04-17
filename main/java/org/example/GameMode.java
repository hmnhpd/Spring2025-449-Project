package org.example;

public abstract class GameMode {
    protected GameLogic gameLogic;

    public GameMode(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public abstract boolean checkWinner(int row, int col, String currentPlayerLetter);

    public abstract void gameEnd(int row, int col, boolean madeSOS);
}
