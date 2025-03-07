package org.example;

public class GameLogic {
    private String[][] board;
    public boolean playerTurn = true;
    public static String bluePlayerLetterChoice = "S";
    public static String redPlayerLetterChoice = "O";
    private int boardSize;

    //GET COMMANDS

    public int getBoardSize(){
        return boardSize;
    }

    public String getCurrentPlayerLetter() {
        if (playerTurn) {
            return bluePlayerLetterChoice;
        }else{
            return redPlayerLetterChoice;
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public GameLogic(int size) {
        this.boardSize = size;
        board = new String[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = " ";
            }
        }
    }

    public void switchPlayerTurn(){
        playerTurn = !playerTurn;
    }

    public boolean playerMove(int row, int col){
        if (!board[row][col].equals(" ")){ //this will place a letter when the space is empty
            return false;
        }
        if (playerTurn){
            board[row][col] = bluePlayerLetterChoice;
        }else{
            board[row][col] = redPlayerLetterChoice;
        }

        switchPlayerTurn();

        return true;
    }

    public void clearBoard(int size){
        this.boardSize = size;
        board = new String[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = " ";
            }
        }
        playerTurn = true;
    }

    public static void setBluePlayerLetterChoice(String playerLetterChoice){
        GameLogic.bluePlayerLetterChoice = playerLetterChoice;
    }

    public static void setRedPlayerLetterChoice(String playerLetterChoice){
        GameLogic.redPlayerLetterChoice = playerLetterChoice;
    }
}
