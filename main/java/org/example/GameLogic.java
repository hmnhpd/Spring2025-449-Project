package org.example;
import javax.swing.JOptionPane;
import java.awt.Color;

public class GameLogic {
    private String[][] board;
    public boolean playerTurn = true;
    public static String bluePlayerLetterChoice;
    public static String redPlayerLetterChoice;
    private int boardSize;
    public static String gameMode = "Simple";
    public static String currentLetter;
    public static int blueSOSCounter = 0;
    public static int redSOSCounter = 0;


    //GET COMMANDS

    public int getBoardSize(){
        return boardSize;
    }

    public static String getBluePlayerLetterChoice(){
        return bluePlayerLetterChoice;
    }

    public static String getRedPlayerLetterChoice(){
        return redPlayerLetterChoice;
    }

    public int getBlueSOSCounter(){
        return blueSOSCounter;
    }

    public int getRedSOSCounter(){
        return redSOSCounter;
    }

    public String getCurrentPlayerLetter() {
        return playerTurn ? redPlayerLetterChoice : bluePlayerLetterChoice;
    }

    public String[][] getBoard() {
        return board;
    }

    public static String getGameMode(){
        return gameMode;
    }

    public boolean isBoardFull(){
        System.out.println("Checking if board full, Size:" + getBoardSize());
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].equals(" ") || board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public GameLogic(int size) {
        this.boardSize = size;
        this.board = new String[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = " ";
            }
        }
    }

    public void switchPlayerTurn(){
        System.out.println(playerTurn);
        playerTurn = !playerTurn;
        System.out.println(playerTurn);
    }

    public boolean playerMove(int row, int col){
        System.out.println("Checking game mode: " + gameMode);

        if (board[row][col] == null) {
            board[row][col] = " ";
        }

        if (!board[row][col].equals(" ")){
            return false;
        }

        currentLetter = getCurrentPlayerLetter();

        board[row][col] = currentLetter;

        boolean findingSOS = checkWinner(row, col, currentLetter);

        if (findingSOS){
            if(playerTurn){
                blueSOSCounter++;
            }else {
                redSOSCounter++;
            }
        }

        //CHECK FOR SIMPLE SOS WIN
        if (gameMode.equals("Simple") && findingSOS){
            if (checkWinner(row,col,currentLetter)){
                String winner = playerTurn ? "Blue" : "Red";
                JOptionPane.showMessageDialog(null, winner +" Player Won");

                clearBoard(boardSize);

                return true;
            }
        }else{//GENERAL GAME WIN
            if (isBoardFull()){
                String result;
                if (redSOSCounter > blueSOSCounter) {
                    result = "Red Player Won. Score: Blue: " + blueSOSCounter + " Red: " + redSOSCounter;
                } else if (blueSOSCounter > redSOSCounter) {
                    result = "Blue Player Won. Score: Blue: " + blueSOSCounter + " Red: " + redSOSCounter;
                } else{
                    result = "Draw Game. Score: Blue: " + blueSOSCounter + " Red: " + redSOSCounter;
                }
                JOptionPane.showMessageDialog(null, result);
                clearBoard(boardSize);
                return true;
            }
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
        blueSOSCounter = 0;
        redSOSCounter = 0;
        playerTurn = true;

    }

    public static void setBluePlayerLetterChoice(String playerLetterChoice){
        bluePlayerLetterChoice = playerLetterChoice;
    }

    public static void setRedPlayerLetterChoice(String playerLetterChoice){
        redPlayerLetterChoice = playerLetterChoice;
    }

    public static void setGameMode(String gameMode){
        GameLogic.gameMode = gameMode;
    }

    public boolean checkWinner(int row, int col ,String currentPlayerLetter){
        //HORIZONTAL
        if (col >= 2 && board[row][col-2].equals("S")){ //two left from current position
            if (board[row][col-1].equals("O")){ //one left from current position
                if (board[row][col].equals("S")){ //current position
                    return true;
                }
            }
        }
        if (col <= boardSize - 3 && board[row][col].equals("S")){ //current position
            if (board[row][col+1].equals("O")){ //one right from current position
                if (board[row][col+2].equals("S")){ //two right from current position
                    return true;
                }
            }
        }
        if (col >= 1 && col <= boardSize - 2 && board[row][col-1].equals("S")){ //one left from current position
            if (board[row][col].equals("O")){ //current position
                if (board[row][col+1].equals("S")){ //one right from current position
                    return true;
                }
            }
        }
        //VERTICAL
        if (row >= 2 && board[row-2][col].equals("S")){ //two below current position
            if (board[row-1][col].equals("O")){ //one below current position
                if (board[row][col].equals("S")){ //current position
                    return true;
                }
            }
        }
        if (row <= boardSize - 3 && board[row][col].equals("S")){ //current position
            if (board[row+1][col].equals("O")){ //one above current position
                if (board[row + 2][col].equals("S")){ //two above current position
                    return true;
                }
            }
        }
        if (row >= 1 && row <= boardSize - 2 && board[row-1][col].equals("S")){ //one below current position
            if (board[row][col].equals("O")){ //current position
                if (board[row + 1][col].equals("S")){ //one above current position
                    return true;
                }
            }
        }
        //DIAGONALS
        if (row >= 2 && col >= 2 && board[row-2][col-2].equals("S")){ //down two left two
            if (board[row-1][col-1].equals("O")){ //two down left one
                if (board[row][col].equals("S")){ //current position
                    return true;
                }
            }
        }
        if (row <= boardSize - 3 && col <= boardSize - 3 && board[row][col].equals("S")){ //current position
            if (board[row + 1][col + 1].equals("O")){ //up one right one
                if (board[row + 2][col + 2].equals("S")){ //up two right two
                    return true;
                }
            }
        }
        if (row >= 1 && row <= boardSize - 2 && col >= 1  &&  col <= boardSize - 2 && board[row-1][col-1].equals("S")){ //down one left one
            if (board[row][col].equals("O")){ //current position
                if (board[row+1][col+1].equals("S")){ //up one right one
                    return true;
                }
            }
        }
        if (row >= 2 && col <= boardSize - 3 && board[row - 2][col + 2].equals("S")){ //down 2 right 2
            if (board[row - 1][col + 1].equals("O")){ //down 1 right 1
                if (board[row][col].equals("S")){ //current position
                    return true;
                }
            }
        }
        if (row <= boardSize - 3 && col >= 2 && board[row][col].equals("S")){ //current position
            if (board[row + 1][col - 1].equals("O")){ //up one left one
                if (board[row + 2][col - 2].equals("S")){ //up two left two
                    return true;
                }
            }
        }
        if (row >= 1 && row <= boardSize - 2 && col <= boardSize - 2 && col >= 1 && board[row - 1][col + 1].equals("S")){ //down one right one
            if (board[row][col].equals("O")){ //current position
                if (board[row + 1][col - 1].equals("S")){ //up one left one
                    return true;
                }
            }
        }

        return false;
    }
}



