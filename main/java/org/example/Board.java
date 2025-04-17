package org.example;

public class Board {
    private String[][] board;
    private int size;

    public Board(int size) {
        this.size = size;
        board = new String[size][size];
        clear();
    }

    public void clear(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = " ";
            }
        }
    }

    public boolean isFull(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].equals(" ") || board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isCellEmpty(int row, int col) {
        String cell = board[row][col];
        return cell == null || cell.equals(" ");
    }

    public boolean checkSOS(int row, int col, String playerLetter){
        // Horizontal SOS check
        if (col >= 2 && board[row][col - 2].equals("S") && board[row][col - 1].equals("O") && board[row][col].equals("S")) {
            return true;
        }
        if (col <= board.length - 3 && board[row][col].equals("S") && board[row][col + 1].equals("O") && board[row][col + 2].equals("S")) {
            return true;
        }
        if (col >= 1 && col <= board.length - 2 && board[row][col - 1].equals("S") && board[row][col].equals("O") && board[row][col + 1].equals("S")) {
            return true;
        }

        // Vertical SOS check
        if (row >= 2 && board[row - 2][col].equals("S") && board[row - 1][col].equals("O") && board[row][col].equals("S")) {
            return true;
        }
        if (row <= board.length - 3 && board[row][col].equals("S") && board[row + 1][col].equals("O") && board[row + 2][col].equals("S")) {
            return true;
        }
        if (row >= 1 && row <= board.length - 2 && board[row - 1][col].equals("S") && board[row][col].equals("O") && board[row + 1][col].equals("S")) {
            return true;
        }

        // Diagonal SOS check
        if (row >= 2 && col >= 2 && board[row - 2][col - 2].equals("S") && board[row - 1][col - 1].equals("O") && board[row][col].equals("S")) {
            return true;
        }
        if (row <= board.length - 3 && col <= board.length - 3 && board[row][col].equals("S") && board[row + 1][col + 1].equals("O") && board[row + 2][col + 2].equals("S")) {
            return true;
        }
        if (row >= 1 && row <= board.length - 2 && col >= 1 && col <= board.length - 2 && board[row - 1][col - 1].equals("S") && board[row][col].equals("O") && board[row + 1][col + 1].equals("S")) {
            return true;
        }
        if (row >= 2 && col <= board.length - 3 && board[row - 2][col + 2].equals("S") && board[row - 1][col + 1].equals("O") && board[row][col].equals("S")) {
            return true;
        }
        if (row <= board.length - 3 && col >= 2 && board[row][col].equals("S") && board[row + 1][col - 1].equals("O") && board[row + 2][col - 2].equals("S")) {
            return true;
        }
        if (row >= 1 && row <= board.length - 2 && col <= board.length - 2 && col >= 1 && board[row - 1][col + 1].equals("S") && board[row][col].equals("O") && board[row + 1][col - 1].equals("S")) {
            return true;
        }

        return false;
    }


    //GETTERS
    public String get(int row, int col){ return board[row][col]; }
    public int getSize(){ return size; }
    public String[][] getGrid(){ return board; }

    //SETTER
    public void set(int row, int col, String val){ board[row][col] = val; }
    public void setMove(int row, int col, String letter){
        board[row][col] = letter;
    }
}
