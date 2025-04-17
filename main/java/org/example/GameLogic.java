package org.example;
import javax.swing.JOptionPane;
import java.awt.Color;

public abstract class GameLogic {
    private Board board;
    private Player redPlayer;
    private Player bluePlayer;
    protected boolean playerTurn = true;
    private GameMode gameMode;

    public GameLogic(int size, String gameModeType) {
        this.board = new Board(size);
        this.redPlayer = new Player("Red", "S");
        this.bluePlayer = new Player("Blue", "S");
        this.playerTurn = true;

        if (gameModeType.equalsIgnoreCase("Simple")) {
            this.gameMode = new SimpleGameMode(this);
        }else{
            this.gameMode = new GeneralGameMode(this);
        }
    }

    public boolean playerMove(int row, int col){
        if(!board.isCellEmpty(row, col)){
            return false;
        }

        Player currentPlayer = getCurrentPlayer();
        String letter = currentPlayer.getLetterChoice();

        board.setMove(row,col,letter);
        boolean madeSOS = gameMode.checkWinner(row, col, letter);

        gameMode.gameEnd(row, col, madeSOS);
        return true;
    }

    public void clearBoard(){
        board.clear();
        redPlayer.resetSOS();
        bluePlayer.resetSOS();
        playerTurn = true;
    }

    public void switchPlayerTurn(){
        playerTurn = !playerTurn;
    }

    public Player getCurrentPlayer(){
        return playerTurn ? redPlayer : bluePlayer;
    }

    public Player getRed(){ return redPlayer; }
    public Player getBlue(){ return bluePlayer; }

    public Board getBoard(){ return board;}
    public boolean isPlayerTurn(){ return playerTurn; }

    public void setGameMode(GameMode gameMode){ this.gameMode = gameMode; }

}



