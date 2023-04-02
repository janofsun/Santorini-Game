package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BlockTest {
    private Block block;

    @Before
    public void setUp() {
        block = new Block(1, 2, 0, 1);
    }

    @Test
    public void getMthods() {
        assertEquals(1, block.getX());
        assertEquals(2, block.getY());
        assertEquals(0, block.getLevel());
        assertEquals(1, block.getOccupancy());
    }

    @Test
    public void setMthods() {
        block.setLevel();
        block.setLevel();
        assertEquals(2, block.getLevel());
        block.setOccupied(0);
        assertEquals(0, block.getOccupancy());
    }

    // @Test
    // public void outOfBounds() {
    //     try {
    //         new Block(-1, 0, 0, 0);
    //     } catch (IllegalArgumentException e) {
    //         assertEquals("Out of bounds.", e.getMessage());
    //     }
    //     try {
    //         new Block(0, -2, 0, 0);
    //     } catch (IllegalArgumentException e) {
    //         assertEquals("Out of bounds.", e.getMessage());
    //     }
    //     try {
    //         new Block(10, 0, 0, 0);
    //     } catch (IllegalArgumentException e) {
    //         assertEquals("Out of bounds.", e.getMessage());
    //     }
    //     try {
    //         new Block(0, 5, 0, 0);
    //     } catch (IllegalArgumentException e) {
    //         assertEquals("Out of bounds.", e.getMessage());
    //     }
    // }
}
