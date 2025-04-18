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
    private boolean playerTurn = false; // TRUE = RED'S TURN, FALSE = BLUE'S TURN
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
    private static GameLogic gameLogic; // ADD STATIC REFERENCE TO CURRENT GAME INSTANCE

    public GameLogic(int size) {
        this.board = new Board(size);
        this.redPlayer = new Player("Red", redPlayerLetterChoice);
        this.bluePlayer = new Player("Blue", bluePlayerLetterChoice);
        this.playerTurn = false; // START WITH BLUE PLAYER'S TURN
        this.gameOver = false;
        gameLogic = this; // SET THE STATIC REFERENCE TO THIS INSTANCE

        // RE-APPLY COMPUTER PLAYER SETTINGS
        isRedComputerPlayer = GameLogic.isRedComputerPlayer();
        isBlueComputerPlayer = GameLogic.isBlueComputerPlayer();

        if (gameModeType.equalsIgnoreCase("Simple")) {
            this.gameMode = new SimpleGameMode(this);
        } else {
            this.gameMode = new GeneralGameMode(this);
        }

        System.out.println("Game initialized - Blue starts (playerTurn = " + playerTurn + 
                         "), isBlueComputer = " + isBlueComputerPlayer + 
                         ", isRedComputer = " + isRedComputerPlayer);
    }

    public boolean playerMove(int row, int col) {
        // CHECK IF IT'S THE CORRECT PLAYER'S TURN
        if (!isHumanTurn()) {
            System.out.println("Move blocked - not human's turn");
            return false;
        }

        if (!board.isCellEmpty(row, col) || gameOver) {
            return false;
        }

        // GET THE CURRENT PLAYER AND THEIR LETTER CHOICE
        Player currentPlayer = getCurrentPlayer();
        String letter = currentPlayer.getLetterChoice();
        Color color = playerTurn ? Color.RED : Color.BLUE;

        System.out.println("Making move for " + (playerTurn ? "Red" : "Blue") + 
                         " player at (" + row + "," + col + ") with letter " + letter);

        // SET THE MOVE ON THE BOARD
        board.setMove(row, col, letter, color);
        
        // CHECK FOR WINNER
        boolean madeSOS = gameMode.checkWinner(row, col, letter);
        
        // HANDLE GAME END LOGIC
        gameMode.gameEnd(row, col, madeSOS);
        
        return true;
    }

    public void makeComputerMove() {
        System.out.println("Attempting computer move - playerTurn: " + playerTurn + 
                         ", isRedComputer: " + isRedComputerPlayer +
                         ", isBlueComputer: " + isBlueComputerPlayer);
        
        // VERIFY IT'S ACTUALLY THE COMPUTER'S TURN
        if (!isComputerTurn()) {
            System.out.println("Computer move skipped - not computer's turn");
            return;
        }
        
        if (board.isFull() || gameOver) {
            System.out.println("Computer move skipped - board full or game over");
            return;
        }

        int size = board.getSize();
        int row, col;
        
        // KEEP TRYING RANDOM POSITIONS UNTIL WE FIND AN EMPTY ONE
        do {
            row = random.nextInt(size);
            col = random.nextInt(size);
        } while (!board.isCellEmpty(row, col));
        
        // GET THE CURRENT PLAYER AND THEIR LETTER CHOICE
        Player currentPlayer = getCurrentPlayer();
        String letter = currentPlayer.getLetterChoice();
        Color color = playerTurn ? Color.RED : Color.BLUE;
        
        System.out.println("Making computer move for " + (playerTurn ? "Red" : "Blue") + 
                         " player at (" + row + "," + col + ") with letter " + letter);
        
        // SET THE MOVE ON THE BOARD
        board.setMove(row, col, letter, color);
        
        // CHECK FOR WINNER
        boolean madeSOS = gameMode.checkWinner(row, col, letter);
        System.out.println("SOS made: " + madeSOS);
        
        // HANDLE GAME END LOGIC
        gameMode.gameEnd(row, col, madeSOS);
    }

    public boolean isComputerTurn() {
        boolean result = (playerTurn && isRedComputerPlayer) || (!playerTurn && isBlueComputerPlayer);
        System.out.println("Checking computer turn - playerTurn: " + playerTurn + 
                         ", isRedComputer: " + isRedComputerPlayer + 
                         ", isBlueComputer: " + isBlueComputerPlayer +
                         ", result: " + result);
        return result;
    }

    public boolean isHumanTurn() {
        // If it's Red's turn and Red is not a computer, or if it's Blue's turn and Blue is not a computer
        return (playerTurn && !isRedComputerPlayer) || (!playerTurn && !isBlueComputerPlayer);
    }

    public void clearBoard() {
        board.clear();
        redPlayer.resetSOS();
        bluePlayer.resetSOS();
        playerTurn = false; // Start with blue player's turn
        System.out.println("Board cleared - Blue starts (playerTurn = " + playerTurn + ")");
    }

    public void switchPlayerTurn() {
        playerTurn = !playerTurn;
        Player nextPlayer = getCurrentPlayer();
        System.out.println("Turn switched to: " + (playerTurn ? "Red" : "Blue") + 
                         ", next letter will be: " + nextPlayer.getLetterChoice());
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
        if (gameLogic != null) {
            gameLogic.bluePlayer.setLetterChoice(letter);
        }
    }

    public static void setRedPlayerLetterChoice(String letter) {
        redPlayerLetterChoice = letter;
        if (gameLogic != null) {
            gameLogic.redPlayer.setLetterChoice(letter);
        }
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
        // Create new players with the current letter choices
        this.redPlayer = new Player("Red", redPlayerLetterChoice);
        this.bluePlayer = new Player("Blue", bluePlayerLetterChoice);
        this.playerTurn = false; // Start with blue player's turn
        this.gameOver = false;
        
        System.out.println("Game reset - Blue starts (playerTurn = " + playerTurn + 
                         "), isBlueComputer = " + isBlueComputerPlayer + 
                         ", isRedComputer = " + isRedComputerPlayer +
                         ", Blue letter = " + bluePlayer.getLetterChoice() +
                         ", Red letter = " + redPlayer.getLetterChoice());
    }

    public static String getBluePlayerLetterChoice() {
        return bluePlayerLetterChoice;
    }
    
    public static String getRedPlayerLetterChoice() {
        return redPlayerLetterChoice;
    }
}



