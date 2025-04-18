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
            Player currentPlayer = gameLogic.getCurrentPlayer();
            currentPlayer.incrementSOS();
        } else {
            gameLogic.switchPlayerTurn();
        }

        // CHECK IF THE GAME IS OVER AND DETERMINE THE WINNER
        if (gameLogic.getBoard().isFull()) {
            int redScore = gameLogic.getRedPlayer().getSOSCount();
            int blueScore = gameLogic.getBluePlayer().getSOSCount();
            
            String result;
            if (redScore > blueScore) {
                result = "Red Player Won! Score - Red: " + redScore + " Blue: " + blueScore;
            } else if (blueScore > redScore) {
                result = "Blue Player Won! Score - Red: " + redScore + " Blue: " + blueScore;
            } else {
                result = "Draw Game! Score - Red: " + redScore + " Blue: " + blueScore;
            }
            
            JOptionPane.showMessageDialog(null, result);
            gameLogic.setGameOver(true);
        }
    }
}
