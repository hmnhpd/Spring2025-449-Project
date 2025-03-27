
import org.example.GameLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest3 {
    private GameLogic gameLogic;

    @Test
    void testPlayerMoveWithAllSelections() {
        // Set up game mode and player choices
        GameLogic.setGameMode("Simple");
        GameLogic.setBluePlayerLetterChoice("S");
        GameLogic.setRedPlayerLetterChoice("O");

        // Initialize game board
        gameLogic = new GameLogic(8);

        // Check board is initialized correctly
        assertNotNull(gameLogic.getBoard(), "Board should be initialized");
        assertEquals(8, gameLogic.getBoardSize(), "Board size should be 8x8");

        // Make a valid move
        boolean moveResult = gameLogic.playerMove(3, 3);

        // Validate move was successful
        assertTrue(moveResult, "Move should be successful");
        assertEquals("S", gameLogic.getBoard()[3][3], "Board should reflect player's move");

        // Validate turn changes
        assertFalse(gameLogic.playerTurn, "Turn should switch after move");
    }

}
