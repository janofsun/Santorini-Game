package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        Block workerBlock1 = new Block(1, 2, 0, 0);
        Block workerBlock2 = new Block(3, 4, 0, 0);
        player = new Player(workerBlock1, workerBlock2);
    }

    @Test
    public void getMthods() {
        assertEquals(0, player.getActions());
        assertEquals(1, player.getWorkers().get(0).getOccupancy());
    }

    @Test
    public void setMethods() {
        player.setActions(2);
        assertEquals(2, player.getActions());
    }

    @Test
    public void setBlock() {
        Block block = new Block(4, 4, 0, 0);
        Block oldBlock = player.getWorkers().get(0);
        player.setWorkers(0, block);
        assertEquals(1, block.getOccupancy());
        assertEquals(0, oldBlock.getOccupancy());
        assertEquals(4, player.getWorkers().get(0).getX());
        assertEquals(4, player.getWorkers().get(0).getY());
    }

    @Test
    public void illegalTest() {
        try {
            new Player(new Block(0, 0, 0, 0), new Block(-1, 0, 0, 0));
        } catch (IllegalArgumentException e) {
            assertEquals("Out of bounds.", e.getMessage());
        }
        try {
            new Player(new Block(0, 0, 0, 0), new Block(0, 0, 0, 0));
        } catch (IllegalArgumentException e) {
            assertEquals("Please select different positions for your workers!", e.getMessage());
        }
    }
}
