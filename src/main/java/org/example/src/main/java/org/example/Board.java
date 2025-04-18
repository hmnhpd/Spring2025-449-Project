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
        
        // CHECK ALL POSSIBLE SOS PATTERNS
        if (playerLetter.equals("S")) {
            // WHEN PLACING AN S, CHECK IF IT COMPLETES ANY SOS PATTERN
            // EITHER AS THE FIRST S OR THE LAST S
            return checkSOSAsFirstS(row, col) || checkSOSAsLastS(row, col);
        } else {
            // WHEN PLACING AN O, CHECK IF IT CAN BE PART OF AN SOS PATTERN
            return checkSOSWithO(row, col);
        }
    }

    private boolean checkSOSAsFirstS(int row, int col) {
        // CHECK RIGHT
        if (col <= size - 3) {
            if (board[row][col + 1].equals("O") && board[row][col + 2].equals("S")) {
                return true;
            }
        }
        // CHECK DOWN
        if (row <= size - 3) {
            if (board[row + 1][col].equals("O") && board[row + 2][col].equals("S")) {
                return true;
            }
        }
        // CHECK DIAGONAL DOWN-RIGHT
        if (row <= size - 3 && col <= size - 3) {
            if (board[row + 1][col + 1].equals("O") && board[row + 2][col + 2].equals("S")) {
                return true;
            }
        }
        // CHECK DIAGONAL DOWN-LEFT
        if (row <= size - 3 && col >= 2) {
            if (board[row + 1][col - 1].equals("O") && board[row + 2][col - 2].equals("S")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSOSAsLastS(int row, int col) {
        // CHECK LEFT
        if (col >= 2) {
            if (board[row][col - 2].equals("S") && board[row][col - 1].equals("O")) {
                return true;
            }
        }
        // CHECK UP
        if (row >= 2) {
            if (board[row - 2][col].equals("S") && board[row - 1][col].equals("O")) {
                return true;
            }
        }
        // CHECK DIAGONAL UP-LEFT
        if (row >= 2 && col >= 2) {
            if (board[row - 2][col - 2].equals("S") && board[row - 1][col - 1].equals("O")) {
                return true;
            }
        }
        // CHECK DIAGONAL UP-RIGHT
        if (row >= 2 && col <= size - 3) {
            if (board[row - 2][col + 2].equals("S") && board[row - 1][col + 1].equals("O")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSOSWithO(int row, int col) {
        // CHECK HORIZONTAL
        if (col >= 1 && col <= size - 2) {
            if (board[row][col - 1].equals("S") && board[row][col + 1].equals("S")) {
                return true;
            }
        }
        // CHECK VERTICAL
        if (row >= 1 && row <= size - 2) {
            if (board[row - 1][col].equals("S") && board[row + 1][col].equals("S")) {
                return true;
            }
        }
        // CHECK DIAGONAL (TOP-LEFT TO BOTTOM-RIGHT)
        if (row >= 1 && row <= size - 2 && col >= 1 && col <= size - 2) {
            if (board[row - 1][col - 1].equals("S") && board[row + 1][col + 1].equals("S")) {
                return true;
            }
        }
        // CHECK DIAGONAL (TOP-RIGHT TO BOTTOM-LEFT)
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
    
    // RETURN A DEFENSIVE COPY OF THE BOARD
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
