package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

public class ComputerOpponentTest {
    private GameLogic gameLogic;
    private static final int BOARD_SIZE = 8;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic(BOARD_SIZE);
        gameLogic.resetGame();
    }

    @Test
    void testComputerVsComputerGame() {
        // Set both players as computers
        GameLogic.setRedPlayerComputer(true);
        GameLogic.setBluePlayerComputer(true);
        
        // Make first computer move
        gameLogic.makeComputerMove();
        
        // Verify that a move was made
        boolean moveMade = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!gameLogic.getBoard().isCellEmpty(i, j)) {
                    moveMade = true;
                    break;
                }
            }
        }
        assertTrue(moveMade, "Computer should make a move");
    }

    @Test
    void testComputerMakesValidMoves() {
        // Set red player as computer
        GameLogic.setRedPlayerComputer(true);
        
        // Make several moves and verify they're valid
        for (int i = 0; i < 5; i++) {
            gameLogic.makeComputerMove();
            
            // Verify the move was made in an empty cell
            boolean validMove = false;
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (!gameLogic.getBoard().isCellEmpty(row, col)) {
                        String cell = gameLogic.getBoard().get(row, col);
                        assertTrue(cell.equals("S") || cell.equals("O"), 
                            "Computer should place either S or O");
                        validMove = true;
                    }
                }
            }
            assertTrue(validMove, "Computer should make a valid move");
            
            // Switch turns
            gameLogic.switchPlayerTurn();
        }
    }

    @Test
    void testComputerRespectsGameOver() {
        // Set both players as computers
        GameLogic.setRedPlayerComputer(true);
        GameLogic.setBluePlayerComputer(true);
        
        // Fill the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameLogic.getBoard().setMove(i, j, "S", Color.RED);
            }
        }
        
        // Try to make a move
        gameLogic.makeComputerMove();
        
        // Verify no new moves were made
        int moveCount = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!gameLogic.getBoard().isCellEmpty(i, j)) {
                    moveCount++;
                }
            }
        }
        assertEquals(BOARD_SIZE * BOARD_SIZE, moveCount, 
            "Computer should not make moves when board is full");
    }

    @Test
    void testComputerUsesCorrectLetter() {
        // Set red player as computer with letter S
        GameLogic.setRedPlayerComputer(true);
        GameLogic.setRedPlayerLetterChoice("S");
        
        // Make a move
        gameLogic.makeComputerMove();
        
        // Verify the correct letter was used
        boolean foundS = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameLogic.getBoard().get(i, j).equals("S")) {
                    foundS = true;
                    break;
                }
            }
        }
        assertTrue(foundS, "Computer should use the assigned letter S");
    }

    @Test
    void testComputerAlternatesTurns() {
        // Set both players as computers
        GameLogic.setRedPlayerComputer(true);
        GameLogic.setBluePlayerComputer(true);
        
        // Make first move (should be red)
        gameLogic.makeComputerMove();
        boolean redMoveMade = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!gameLogic.getBoard().isCellEmpty(i, j)) {
                    assertEquals(Color.RED, gameLogic.getBoard().getColor(i, j),
                        "First move should be red player's move");
                    redMoveMade = true;
                    break;
                }
            }
        }
        assertTrue(redMoveMade, "Red player should make the first move");
        
        // Make second move (should be blue)
        gameLogic.makeComputerMove();
        boolean blueMoveMade = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!gameLogic.getBoard().isCellEmpty(i, j) && 
                    gameLogic.getBoard().getColor(i, j).equals(Color.BLUE)) {
                    blueMoveMade = true;
                    break;
                }
            }
        }
        assertTrue(blueMoveMade, "Blue player should make the second move");
    }
} 