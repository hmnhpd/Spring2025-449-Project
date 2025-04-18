package org.example;
import javax.swing.JOptionPane;

public class SimpleGameMode extends GameMode {

    public SimpleGameMode(GameLogic gameLogic) {
        super(gameLogic);
    }

    @Override
    public boolean checkWinner(int row, int col, String currentPlayerLetter) {
        return gameLogic.getBoard().checkSOS(row, col, currentPlayerLetter);
    }

    @Override
    public void gameEnd(int row, int col, boolean madeSOS) {
        if (madeSOS) {
            String winner = gameLogic.isPlayerTurn() ? "Red" : "Blue";
            JOptionPane.showMessageDialog(null, winner + " Player Won!");
            gameLogic.setGameOver(true);
        } else {
            gameLogic.switchPlayerTurn();
        }
    }
}
