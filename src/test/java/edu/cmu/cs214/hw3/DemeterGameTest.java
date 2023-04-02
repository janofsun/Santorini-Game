package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class DemeterGameTest {
    private GodGame game;
    private List<God> godCards;

    @Before
    public void setUp() {
        game = new GodGame( 2, 2, 3, 2, 1, 1, 2, 1);
        godCards = new ArrayList<God>();
        godCards.add(new Demeter());
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
        assertEquals("Demeter", game.getGodCards().get(Game.getCurrentPlayer()).getName());
        game.switchPlayer();
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(1));
        assertEquals("No God", game.getGodCards().get(Game.getCurrentPlayer()).getName());
    }

    @Test
    public void buildTest() {
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(0));
        game.move(3, 1, 1);
        game.buildTower(3, 0, 1);
        assertEquals(1, Game.getBoard().getBlock(3, 0).getLevel());
        game.buildTower(4, 1, 1);
        assertEquals(1, Game.getBoard().getBlock(4, 1).getLevel());
    }

    @Test
    public void buildFailTest() {
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(0));
        game.move(3, 1, 1);
        game.buildTower(3, 0, 1);
        assertEquals(1, Game.getBoard().getBlock(3, 0).getLevel());
        game.buildTower(3, 0, 1);
        assertEquals(1, Game.getBoard().getBlock(3, 0).getLevel());
    }
}
