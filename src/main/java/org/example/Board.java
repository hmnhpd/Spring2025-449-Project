package org.example;

import java.awt.Color;

public class Board {
    private String[][] board;
    private Color[][] colors;
    private int size;
    private Color lastMoveColor = Color.BLACK;

    public Board(int size) {
        if (size < 3) {
            throw new IllegalArgumentException("Board size must be at least 3x3");
        }
        this.size = size;
        board = new String[size][size];
        colors = new Color[size][size];
        clear();
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = " ";
                colors[i][j] = Color.BLACK;
            }
        }
        lastMoveColor = Color.BLACK;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isCellEmpty(int row, int col) {
        validateIndices(row, col);
        return board[row][col].equals(" ");
    }

    public boolean checkSOS(int row, int col, String playerLetter) {
        validateIndices(row, col);
        if (!playerLetter.equals("S") && !playerLetter.equals("O")) {
            throw new IllegalArgumentException("Player letter must be 'S' or 'O'");
        }
        
        // Check all possible SOS patterns
        if (playerLetter.equals("S")) {
            // When placing an S, check if it completes any SOS pattern
            // either as the first S or the last S
            return checkSOSAsFirstS(row, col) || checkSOSAsLastS(row, col);
        } else {
            // When placing an O, check if it can be part of an SOS pattern
            return checkSOSWithO(row, col);
        }
    }

    private boolean checkSOSAsFirstS(int row, int col) {
        // Check right
        if (col <= size - 3) {
            if (board[row][col + 1].equals("O") && board[row][col + 2].equals("S")) {
                return true;
            }
        }
        // Check down
        if (row <= size - 3) {
            if (board[row + 1][col].equals("O") && board[row + 2][col].equals("S")) {
                return true;
            }
        }
        // Check diagonal down-right
        if (row <= size - 3 && col <= size - 3) {
            if (board[row + 1][col + 1].equals("O") && board[row + 2][col + 2].equals("S")) {
                return true;
            }
        }
        // Check diagonal down-left
        if (row <= size - 3 && col >= 2) {
            if (board[row + 1][col - 1].equals("O") && board[row + 2][col - 2].equals("S")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSOSAsLastS(int row, int col) {
        // Check left
        if (col >= 2) {
            if (board[row][col - 2].equals("S") && board[row][col - 1].equals("O")) {
                return true;
            }
        }
        // Check up
        if (row >= 2) {
            if (board[row - 2][col].equals("S") && board[row - 1][col].equals("O")) {
                return true;
            }
        }
        // Check diagonal up-left
        if (row >= 2 && col >= 2) {
            if (board[row - 2][col - 2].equals("S") && board[row - 1][col - 1].equals("O")) {
                return true;
            }
        }
        // Check diagonal up-right
        if (row >= 2 && col <= size - 3) {
            if (board[row - 2][col + 2].equals("S") && board[row - 1][col + 1].equals("O")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSOSWithO(int row, int col) {
        // Check horizontal
        if (col >= 1 && col <= size - 2) {
            if (board[row][col - 1].equals("S") && board[row][col + 1].equals("S")) {
                return true;
            }
        }
        // Check vertical
        if (row >= 1 && row <= size - 2) {
            if (board[row - 1][col].equals("S") && board[row + 1][col].equals("S")) {
                return true;
            }
        }
        // Check diagonal (top-left to bottom-right)
        if (row >= 1 && row <= size - 2 && col >= 1 && col <= size - 2) {
            if (board[row - 1][col - 1].equals("S") && board[row + 1][col + 1].equals("S")) {
                return true;
            }
        }
        // Check diagonal (top-right to bottom-left)
        if (row >= 1 && row <= size - 2 && col >= 1 && col <= size - 2) {
            if (board[row - 1][col + 1].equals("S") && board[row + 1][col - 1].equals("S")) {
                return true;
            }
        }
        return false;
    }

    private void validateIndices(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Invalid board position: (" + row + ", " + col + ")");
        }
    }

    //GETTERS
    public String get(int row, int col) {
        validateIndices(row, col);
        return board[row][col];
    }
    
    public int getSize() { return size; }
    
    // Return a defensive copy of the board
    public String[][] getGrid() {
        String[][] copy = new String[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    //SETTER
    public void setMove(int row, int col, String letter, Color color) {
        validateIndices(row, col);
        if (!letter.equals("S") && !letter.equals("O")) {
            throw new IllegalArgumentException("Letter must be 'S' or 'O'");
        }
        board[row][col] = letter;
        colors[row][col] = color;
        lastMoveColor = color;
    }

    public Color getColor(int row, int col) {
        validateIndices(row, col);
        return colors[row][col];
    }

    public Color getLastMoveColor() {
        return lastMoveColor;
    }
}
