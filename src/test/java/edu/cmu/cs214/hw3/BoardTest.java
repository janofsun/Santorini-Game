package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BoardTest {
    private Board board;
    private Block block;

    @Before
    public void setUp() {
        board = new Board();
        block = new Block(1, 1, 0, 1);
    }

    @Test
    public void checkSurrounding() {
        boolean valid_dia = board.validSurround(0, 0, block);
        boolean valid_adj = board.validSurround(2, 0, block);
        boolean invalid = board.validSurround(3, 3, block);
        assertEquals(true, valid_adj);
        assertEquals(true, valid_dia);
        assertEquals(false, invalid);
    }

    @Test
    public void checkBounds() {
        boolean valid = board.validBounds(0, 0);
        boolean invalid = board.validBounds(5, 4);
        assertEquals(true, valid);
        assertEquals(false, invalid);
    }

    @Test
    public void moveTest() {
        assertEquals(1, block.getOccupancy());
        assertEquals(true, board.move(0, 0, block).equals(board.getBlock(0, 0)));
        assertEquals(true, board.move(2, 0, block).equals(board.getBlock(2, 0)));
        assertEquals(0, block.getOccupancy());
    }

    // @Test
    // public void moveInvalidTest() {
    //     // Invalid surrounding blocks.
    //     try {
    //         board.move(3, 0, block);
    //     } catch (IllegalStateException e) {
    //         assertEquals("Please find another position to build!", e.getMessage());
    //     }
    //     // Destination has been occupied.
    //     try {
    //         new Block(0, 0, 0, 1);
    //         board.move(0, 0, block);
    //     } catch (IllegalStateException e) {
    //         assertEquals("This position is already occupied!", e.getMessage());
    //     }
    //     // Destination is out of levels.
    //     try {
    //         new Block(1, 0, 2, 0);
    //         board.move(1, 0, block);
    //     } catch (IllegalStateException e) {
    //         assertEquals("Illegal move!", e.getMessage());
    //     }
    // }

    @Test
    public void moveInvalidTest() {
        // Invalid surrounding blocks.
        Block rtn = board.move(3, 0, block);
        assertEquals(null, rtn);
        // Destination has been occupied.
        board.getBlock(0, 0).setOccupied(1);
        Block rtn1 = board.move(0, 0, block);
        assertEquals(null, rtn1);
        // Destination is out of levels.
        board.getBlock(1, 0).setLevel();
        board.getBlock(1, 0).setLevel();
        Block rtn2 = board.move(1, 0, block);
        assertEquals(null, rtn2);
    }

    @Test
    public void buildTowerTest() {
        board.buildTower(0, 0, block);
        assertEquals(1, board.getBlock(0, 0).getLevel());
        board.buildTower(0, 0, block);
        assertEquals(2, board.getBlock(0, 0).getLevel());
    }

    @Test
    public void buildTowerFail() {
        board.getBlock(0, 0).setLevel();
        board.getBlock(0, 0).setLevel();
        board.getBlock(0, 0).setLevel();
        Boolean rtn = board.buildTower(0, 0, block);
        assertEquals(false, rtn);
    }

    @Test
    public void buildDomeTest() {
        board.getBlock(0, 0).setLevel();
        board.getBlock(0, 0).setLevel();
        board.getBlock(0, 0).setLevel();
        board.buildDome(0, 0, block);
        assertEquals(4, board.getBlock(0, 0).getLevel());
    }

    @Test
    public void buildDomeFail() {
        board.getBlock(0, 0).setLevel();
        board.getBlock(0, 0).setLevel();
        Boolean rtn = board.buildDome(0, 0, block);
        assertEquals(false, rtn);
    }
}
