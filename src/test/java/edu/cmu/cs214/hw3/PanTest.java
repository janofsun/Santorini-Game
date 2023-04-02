package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class PanTest {
    private GodGame game;
    private List<God> godCards;

    @Before
    public void setUp() {
        game = new GodGame( 2, 2, 3, 2, 1, 1, 2, 1);
        godCards = new ArrayList<God>();
        godCards.add(new Pan());
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
        assertEquals("Pan", game.getGodCards().get(Game.getCurrentPlayer()).getName());
        game.switchPlayer();
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(1));
        assertEquals("No God", game.getGodCards().get(Game.getCurrentPlayer()).getName());
    }

    @Test
    public void winTest() {
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(0));
        Game.getBoard().getBlock(3, 2).setLevel();
        Game.getBoard().getBlock(3, 2).setLevel();
        assertEquals(2, Game.getBoard().getBlock(3, 2).getLevel());
        game.move(3, 1, 1);
        assertEquals(Game.getCurrentPlayer(), Game.getWinner());
    }
}
