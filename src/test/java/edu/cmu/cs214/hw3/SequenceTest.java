package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;

public class SequenceTest {
    private Game game;
    private List<Player> players;

    @Before
    public void setUp() {
        game = new Game(0, 0, 1, 1, 4, 4, 3, 3);
        players = Game.getPlayers();
        game.switchPlayer();
    }

    @Test
    public void sequenceTest() {
        // first player.
        game.move(0, 1, 0);
        game.buildTower(0, 0, 0);
        if (game.isRoundOver()) game.switchPlayer();
        assertEquals(players.get(1), Game.getCurrentPlayer());
        // second player.
        game.move(3, 4, 0);
        game.buildTower(4, 4, 0);
        if (game.isRoundOver()) game.switchPlayer();
        // first player.
        game.move(0, 0, 0);
        game.buildTower(0, 1, 0);
        if (game.isRoundOver()) game.switchPlayer();
        //second player.
        game.move(2,4,0);
        game.buildTower(3, 4, 0);
        if (game.isRoundOver()) game.switchPlayer();
        //first player.
        game.move(0, 1, 0);
        game.buildTower(0, 0, 0);
        if (game.isRoundOver()) game.switchPlayer();
        //second player.
        game.move(3, 4, 0);
        game.buildTower(4, 4, 0);
        if (game.isRoundOver()) game.switchPlayer();
        //first player.
        game.move(0, 0, 0);
        game.buildTower(0, 1, 0);
        if (game.isRoundOver()) game.switchPlayer();
        // second player.
        game.move(2, 4, 0);
        game.buildTower(3, 4, 0);
        if (game.isRoundOver()) game.switchPlayer();
        //first player
        game.move(0, 1, 0);
        game.buildTower(0, 0, 0);
        if (game.isRoundOver()) game.switchPlayer();
        //second player.
        game.move(1, 4, 0);
        game.buildTower(2, 4, 0);
        if (game.isRoundOver()) game.switchPlayer();
        assertEquals(null, Game.getWinner());
        //first player.
        game.move(0, 0, 0);
        if (game.isRoundOver()) game.switchPlayer();
        assertEquals(players.get(0), Game.getWinner());
    }
}
