package org.example;
import javax.swing.JOptionPane;

public class SimpleGameMode extends GameMode {

    public SimpleGameMode(GameLogic gameLogic) {
        super(gameLogic);
    }

    @Override
    public boolean checkWinner(int row, int col, String currentPlayerLetter){
        return gameLogic.getBoard().checkSOS(row,col,currentPlayerLetter);
    }

    @Override
    public void gameEnd(int row, int col, boolean madeSOS){
        if (madeSOS){
            Player currentPlayer = gameLogic.getCurrentPlayer();
            currentPlayer.incrementSOS();

            JOptionPane.showMessageDialog(null, currentPlayer.getName() + "Player Won!");
            gameLogic.clearBoard();
        }else{
            gameLogic.switchPlayerTurn();
        }
    }
}
