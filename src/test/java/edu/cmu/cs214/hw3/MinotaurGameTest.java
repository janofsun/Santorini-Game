package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class MinotaurGameTest {
    private GodGame game;
    private List<God> godCards;

    @Before
    public void setUp() {
        game = new GodGame( 2, 2, 3, 2, 1, 1, 2, 1);
        godCards = new ArrayList<God>();
        godCards.add(new Minotaur());
        godCards.add(new NoGod());
        game.switchPlayer();
        game.selectGod(Game.getCurrentPlayer(), godCards.get(0));
        game.switchPlayer();
        game.selectGod(Game.getCurrentPlayer(), godCards.get(1));
        game.switchPlayer();
    }

    @Test
    public void initializingTest() {
        assertEquals(2, game.getGodCards().size());
        assertEquals(2, Game.getPlayers().size());
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(0));
        assertEquals("Minotaur", game.getGodCards().get(Game.getCurrentPlayer()).getName());
        game.switchPlayer();
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(1));
        assertEquals("No God", game.getGodCards().get(Game.getCurrentPlayer()).getName());
    }
    /**
     * Test the currentPlayer move into an opponent Worker’s space, and
     * force the opponent Worker one space straight backwards to an unoccupied space at any level..
     */
    @Test
    public void moveTest() {
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(0));
        game.move(2, 1, 0);
        assertEquals(Block.UNOCCUPIED, Game.getBoard().getBlock(2, 2).getOccupancy());
        assertEquals(Block.OCCUPIED, Game.getBoard().getBlock(2, 0).getOccupancy());
        assertEquals(Game.getBoard().getBlock(2, 1), Game.getCurrentPlayer().getWorkers().get(0));
        game.switchPlayer();
        assertEquals(Game.getBoard().getBlock(2, 0), Game.getCurrentPlayer().getWorkers().get(1));
    }
    /**
     * Test the currentPlayer move into an opponent Worker’s space, and the opponent Worker cannot be forced
     * one space straight backwards to an occupied space.
     */
    @Test
    public void moveFailTest() {
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(0));
        Game.getBoard().getBlock(2, 0).setOccupied(Block.OCCUPIED);
        game.move(2, 1, 0);
        assertEquals(Block.OCCUPIED, Game.getBoard().getBlock(2, 2).getOccupancy());
        assertEquals(Game.getBoard().getBlock(2, 2), Game.getCurrentPlayer().getWorkers().get(0));
        game.switchPlayer();
        assertEquals(Game.getBoard().getBlock(2, 1), Game.getCurrentPlayer().getWorkers().get(1));
    }
    /**
     * Test the currentPlayer move into an opponent Worker’s space, and
     * force the opponent Worker one space straight backwards to the Highest Level.
     */
    @Test
    public void moveToCompleteTower() {
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(0));
        Game.getBoard().getBlock(2, 0).setLevel();
        Game.getBoard().getBlock(2, 0).setLevel();
        Game.getBoard().getBlock(2, 0).setLevel();
        game.move(2, 1, 0);
        assertEquals(Block.UNOCCUPIED, Game.getBoard().getBlock(2, 2).getOccupancy());
        assertEquals(Block.OCCUPIED, Game.getBoard().getBlock(2, 0).getOccupancy());
        assertEquals(Game.getBoard().getBlock(2, 1), Game.getCurrentPlayer().getWorkers().get(0));
        game.switchPlayer();
        assertEquals(Game.getBoard().getBlock(2, 0), Game.getCurrentPlayer().getWorkers().get(1));
        assertEquals(null, Game.getWinner());
    }
}
