package org.example;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import java.awt.EventQueue;

public class GameLogic {
    private Board board;
    private Player redPlayer;
    private Player bluePlayer;
    private boolean playerTurn = true; // true = Red's turn, false = Blue's turn
    private GameMode gameMode;
    private static final String DEFAULT_GAME_MODE = "Simple";
    private static final String DEFAULT_LETTER = "S";
    private static String gameModeType = DEFAULT_GAME_MODE;
    private static String bluePlayerLetterChoice = DEFAULT_LETTER;
    private static String redPlayerLetterChoice = DEFAULT_LETTER;
    private static boolean isBlueComputerPlayer = false;
    private static boolean isRedComputerPlayer = false;
    private static Random random = new Random();
    private boolean gameOver = false;

    public GameLogic(int size) {
        this.board = new Board(size);
        this.redPlayer = new Player("Red", redPlayerLetterChoice);
        this.bluePlayer = new Player("Blue", bluePlayerLetterChoice);
        this.playerTurn = true;

        if (gameModeType.equalsIgnoreCase("Simple")) {
            this.gameMode = new SimpleGameMode(this);
        } else {
            this.gameMode = new GeneralGameMode(this);
        }
    }

    public boolean playerMove(int row, int col) {
        if (!board.isCellEmpty(row, col) || gameOver) {
            return false;
        }

        Player currentPlayer = getCurrentPlayer();
        String letter = currentPlayer.getLetterChoice();
        Color color = isPlayerTurn() ? Color.RED : Color.BLUE;

        // Set the move on the board
        board.setMove(row, col, letter, color);
        
        // Check for winner
        boolean madeSOS = gameMode.checkWinner(row, col, letter);
        
        // Handle game end logic
        gameMode.gameEnd(row, col, madeSOS);
        
        return true;
    }

    public void makeComputerMove() {
        if (!isComputerTurn() || board.isFull() || gameOver) {
            System.out.println("Computer move skipped - isComputerTurn: " + isComputerTurn() + 
                             ", isFull: " + board.isFull() + ", gameOver: " + gameOver);
            return;
        }

        int size = board.getSize();
        int row, col;
        
        // Keep trying random positions until we find an empty one
        do {
            row = random.nextInt(size);
            col = random.nextInt(size);
        } while (!board.isCellEmpty(row, col));
        
        System.out.println("Making computer move at (" + row + "," + col + ")");
        
        // Make the move
        Player currentPlayer = getCurrentPlayer();
        String letter = currentPlayer.getLetterChoice();
        Color color = isPlayerTurn() ? Color.RED : Color.BLUE;
        
        // Set the move on the board
        board.setMove(row, col, letter, color);
        System.out.println("Move set: " + letter + " at (" + row + "," + col + ") with color " + color);
        
        // Check for winner
        boolean madeSOS = gameMode.checkWinner(row, col, letter);
        System.out.println("SOS made: " + madeSOS);
        
        // Handle game end logic
        gameMode.gameEnd(row, col, madeSOS);

        // If it's still a computer's turn after the move (no SOS was made) and game isn't over, trigger the next move
        if (isComputerTurn() && !board.isFull() && !gameOver) {
            System.out.println("Scheduling next computer move");
            Timer timer = new Timer(500, ev -> {
                System.out.println("Executing next computer move");
                EventQueue.invokeLater(() -> {
                    makeComputerMove();
                    // Update the UI after the move
                    SOSgui.updateBoard();
                });
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            System.out.println("No next computer move - isComputerTurn: " + isComputerTurn() + 
                             ", isFull: " + board.isFull() + ", gameOver: " + gameOver);
        }
    }

    private boolean isComputerTurn() {
        return (isRedComputerPlayer && playerTurn) || (isBlueComputerPlayer && !playerTurn);
    }

    public void clearBoard() {
        board.clear();
        redPlayer.resetSOS();
        bluePlayer.resetSOS();
        playerTurn = true;
    }

    public void switchPlayerTurn() {
        playerTurn = !playerTurn;
    }

    public Player getCurrentPlayer() {
        return playerTurn ? redPlayer : bluePlayer;
    }

    public String getCurrentPlayerLetter() {
        return getCurrentPlayer().getLetterChoice();
    }

    public Player getRedPlayer() { return redPlayer; }
    public Player getBluePlayer() { return bluePlayer; }

    public Board getBoard() { return board; }
    public boolean isPlayerTurn() { return playerTurn; }
    public int getBoardSize() { return board.getSize(); }

    public static void setGameMode(String mode) {
        gameModeType = mode;
    }

    public static void setBluePlayerLetterChoice(String letter) {
        bluePlayerLetterChoice = letter;
    }

    public static void setRedPlayerLetterChoice(String letter) {
        redPlayerLetterChoice = letter;
    }

    public static void setBluePlayerComputer(boolean isComputer) {
        isBlueComputerPlayer = isComputer;
    }

    public static void setRedPlayerComputer(boolean isComputer) {
        isRedComputerPlayer = isComputer;
    }

    public static boolean isBlueComputerPlayer() {
        return isBlueComputerPlayer;
    }

    public static boolean isRedComputerPlayer() {
        return isRedComputerPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean over) {
        gameOver = over;
    }

    public void resetGame() {
        clearBoard();
        this.redPlayer = new Player("Red", redPlayerLetterChoice);
        this.bluePlayer = new Player("Blue", bluePlayerLetterChoice);
        this.playerTurn = true;
        this.gameOver = false;
    }
}



