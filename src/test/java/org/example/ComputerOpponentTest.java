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
        // SET BOTH PLAYERS AS COMPUTERS
        GameLogic.setRedPlayerComputer(true);
        GameLogic.setBluePlayerComputer(true);
        
        // MAKE FIRST COMPUTER MOVE
        gameLogic.makeComputerMove();
        
        // VERIFY THAT A MOVE WAS MADE
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
        // SET BLUE PLAYER AS COMPUTER (SINCE GAME STARTS WITH BLUE'S TURN)
        GameLogic.setBluePlayerComputer(true);
        
        // MAKE SEVERAL MOVES AND VERIFY THEY'RE VALID
        for (int i = 0; i < 5; i++) {
            gameLogic.makeComputerMove();
            
            // VERIFY THE MOVE WAS MADE IN AN EMPTY CELL
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
            
            // SWITCH TURNS
            gameLogic.switchPlayerTurn();
        }
    }

    @Test
    void testComputerRespectsGameOver() {
        // SET BOTH PLAYERS AS COMPUTERS
        GameLogic.setRedPlayerComputer(true);
        GameLogic.setBluePlayerComputer(true);
        
        // FILL THE BOARD
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameLogic.getBoard().setMove(i, j, "S", Color.RED);
            }
        }
        
        // TRY TO MAKE A MOVE
        gameLogic.makeComputerMove();
        
        // VERIFY NO NEW MOVES WERE MADE
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
        // SET RED PLAYER AS COMPUTER WITH LETTER S
        GameLogic.setRedPlayerComputer(true);
        GameLogic.setRedPlayerLetterChoice("S");
        
        // MAKE A MOVE
        gameLogic.makeComputerMove();
        
        // VERIFY THE CORRECT LETTER WAS USED
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
        // SET BOTH PLAYERS AS COMPUTERS
        GameLogic.setRedPlayerComputer(true);
        GameLogic.setBluePlayerComputer(true);
        
        // MAKE FIRST MOVE (SHOULD BE BLUE)
        gameLogic.makeComputerMove();
        boolean blueMoveMade = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!gameLogic.getBoard().isCellEmpty(i, j)) {
                    assertEquals(Color.BLUE, gameLogic.getBoard().getColor(i, j),
                        "First move should be blue player's move");
                    blueMoveMade = true;
                    break;
                }
            }
        }
        assertTrue(blueMoveMade, "Blue player should make the first move");
        
        // MAKE SECOND MOVE (SHOULD BE RED)
        gameLogic.makeComputerMove();
        boolean redMoveMade = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!gameLogic.getBoard().isCellEmpty(i, j) && 
                    gameLogic.getBoard().getColor(i, j).equals(Color.RED)) {
                    redMoveMade = true;
                    break;
                }
            }
        }
        assertTrue(redMoveMade, "Red player should make the second move");
    }
} 