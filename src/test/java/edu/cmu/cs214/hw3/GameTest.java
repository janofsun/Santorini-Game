package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

public class GameTest {
    private Game game;
    private Board board;
    private List<Player> players;

    @Before
    public void setUp() {
        game = new Game(0, 0, 1, 1, 4, 4, 3, 3);
        board = Game.getBoard();
        players = Game.getPlayers();
        game.switchPlayer();
    }

    @Test
    public void initializingTest() {
        assertEquals(2, players.size());
        assertEquals(players.get(0), Game.getCurrentPlayer());
        assertEquals(1, board.getBlock(0, 0).getOccupancy());
        assertEquals(1, board.getBlock(1, 1).getOccupancy());
        assertEquals(1, board.getBlock(4, 4).getOccupancy());
        assertEquals(1, board.getBlock(3, 3).getOccupancy());
    }

    @Test
    public void initializingTestFail() {
        // Two players choose the same block.
        Game game1 = new Game();
        game1.initiatePlayer(0, 0, 1, 1);
        assertEquals(false, game1.initiatePlayer(0, 0, 3, 3));
        // Two workers choose the same block.
        Game game2 = new Game();
        assertEquals(false, game2.initiatePlayer(0, 0, 0, 0));
        // Initial position is out of bounds.
        Game game3 = new Game();
        assertEquals(false, game3.initiatePlayer(0, 0, 5, 3));
    }

    @Test
    public void moveTest() {
        game.move(2, 2, 1);
        assertEquals(0, board.getBlock(1, 1).getOccupancy());
        assertEquals(1, board.getBlock(2, 2).getOccupancy());
        assertEquals(board.getBlock(2, 2), Game.getCurrentPlayer().getWorkers().get(1));
        assertEquals(1, Game.getCurrentPlayer().getActions());
    }

    @Test
    public void moveInvalidTest() {
        // Out of bounds.
        // try {
        //     game.move(-1, 0, 0);
        // } catch (IllegalStateException e) {
        //     assertEquals("This position is out of Bounds!", e.getMessage());
        // }
        // Destination is not adjacent.
        game.move(0, 2, 0);
        assertEquals(0, board.getBlock(0, 2).getOccupancy());
        // Destination is more than one level away.
        board.getBlock(0, 1).setLevel();
        board.getBlock(0, 1).setLevel();
        game.move(0, 1, 0);
        assertEquals(0, board.getBlock(0, 1).getOccupancy());
        // Destination is already occupied.
        board.getBlock(0, 1).setOccupied(1);
        game.move(0, 1, 0);
        assertNotEquals(board.getBlock(0, 1), Game.getCurrentPlayer().getWorkers().get(0));
    }

    @Test
    public void winnerTest() {
        for (int i = 0; i < 2; i++) {
            board.getBlock(1, 1).setLevel();
            board.getBlock(2, 2).setLevel();
        }
        board.getBlock(2, 2).setLevel();
        assertEquals(null, Game.getWinner());
        game.move(2, 2, 1);
        assertEquals(Game.getCurrentPlayer(), Game.getWinner());
    }

    @Test
    public void buildTowerTest() {
        for (int i = 0; i < 2; i++) {
            Game.getCurrentPlayer().setActions(1);
            game.buildTower(1, 2, 1);
            assertEquals(1, Game.getPlayers().indexOf(Game.getCurrentPlayer()));
            game.switchPlayer();
        }
        assertEquals(2, board.getBlock(1, 2).getLevel());
        // Action is not allowed.
        Game.getCurrentPlayer().setActions(2);
        game.buildTower(1, 2, 1);
        assertEquals(2, board.getBlock(1, 2).getLevel());
        // Level is not allowed.
        for (int i = 0; i < 2; i++) {
            Game.getCurrentPlayer().setActions(1);
            game.buildTower(1, 2, 1);
            game.switchPlayer();
        }
        assertEquals(3, board.getBlock(1, 2).getLevel());
    }

    @Test
    public void buildDome() {
        Game.getCurrentPlayer().setActions(1);
        game.buildDome(1, 2, 1);
        assertEquals(0, board.getBlock(1, 2).getLevel());

        for (int i = 0; i < 3; i++) {
            Game.getCurrentPlayer().setActions(1);
            game.buildTower(1, 2, 1);
            assertEquals(1, Game.getPlayers().indexOf(Game.getCurrentPlayer()));
            game.switchPlayer();
        }
        Game.getCurrentPlayer().setActions(1);
        game.buildDome(1, 2, 1);
        assertEquals(4, board.getBlock(1, 2).getLevel());
    }
}
