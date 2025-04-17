package org.example;

import javax.swing.JOptionPane;

public class GeneralGameMode extends GameMode {

    public GeneralGameMode(GameLogic gameLogic) {
        super(gameLogic);
    }

    @Override
    public boolean checkWinner(int row, int col, String currentPlayerLetter) {
        return gameLogic.getBoard().checkSOS(row, col, currentPlayerLetter);
    }

    @Override
    public void gameEnd(int row, int col, boolean madeSOS) {
        if (madeSOS) {
            gameLogic.getCurrentPlayer().incrementSOS();
        }

        // Check if board is full — game over
        if (gameLogic.getBoard().isFull()) {
            Player red = gameLogic.getRedPlayer();
            Player blue = gameLogic.getBluePlayer();

            String result;
            if (red.getScore() > blue.getScore()) {
                result = "Red Player Won. Score: Blue: " + blue.getScore() + " Red: " + red.getScore();
            } else if (blue.getScore() > red.getScore()) {
                result = "Blue Player Won. Score: Blue: " + blue.getScore() + " Red: " + red.getScore();
            } else {
                result = "Draw Game. Score: Blue: " + blue.getScore() + " Red: " + red.getScore();
            }

            JOptionPane.showMessageDialog(null, result);
            gameLogic.clearBoard();
        } else {
            gameLogic.switchPlayerTurn();
        }
    }
}
