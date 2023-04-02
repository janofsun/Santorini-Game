package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class GodSequenceTest {
    private GodGame game;
    private List<God> godCards;

    @Before
    public void setUp() {
        game = new GodGame(2, 2, 3, 2, 1, 1, 2, 1);
        godCards = new ArrayList<God>();
        godCards.add(new Minotaur());
        godCards.add(new Pan());
        game.switchPlayer();
        game.selectGod(Game.getCurrentPlayer(), godCards.get(0));
        game.switchPlayer();
        game.selectGod(Game.getCurrentPlayer(), godCards.get(1));
        game.switchPlayer();
    }

    @Test
    public void sequenceTest() {
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(0));
        // first player.
        game.move(3, 1, 1);
        game.buildTower(2, 0, 1);
        assertEquals(Game.getCurrentPlayer(), Game.getPlayers().get(1));
        // second player.
        game.move(1, 0, 0);
        game.buildTower(2, 0, 0);
        assertEquals(2, Game.getBoard().getBlock(2,0).getLevel());
        // first player.
        // Minaotaur's power.
        game.move(2,1,0);
        game.buildTower(2, 2, 0);
        //second player.
        game.move(0, 0, 0);
        game.buildTower(0, 1, 0);
        //first player.
        game.move(4, 1, 1);
        game.buildTower(4, 0, 1);
        //second player.
        // Pan's power.
        game.move(1, 0, 1);
        assertEquals(Game.getCurrentPlayer(), Game.getWinner());
    }
}
