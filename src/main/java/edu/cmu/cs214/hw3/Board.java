package edu.cmu.cs214.hw3;

import static edu.cmu.cs214.hw3.Block.DEFAULT_LEVEL;
import static edu.cmu.cs214.hw3.Block.OCCUPIED;
import static edu.cmu.cs214.hw3.Block.UNOCCUPIED;
import static edu.cmu.cs214.hw3.Block.COMPLETE_TOWER;

public class Board {
    /**
     * The board contains 5 x 5 grids.
     */
    private Block[][] board;
    public static final int BOARDSIZE = 5;
    /**
     * Construct and set up a new board.
     */
    public Board() {
        this.board = new Block[BOARDSIZE][BOARDSIZE];

        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                Block block = new Block(row, col, DEFAULT_LEVEL, UNOCCUPIED);
                board[row][col] = block;
            }
        }
    }
    /**
     * Move the worker to the specified block, and update blocks status.
     * @param row
     * @param col
     * @param workerBlock
     * @return the new block
     */
    public Block move(int row, int col, Block workerBlock) {
        if (!validBlock(row, col, workerBlock)) return null;
        Block moveBlock = getBlock(row, col);
        int occupancy = moveBlock.getOccupancy();
        int level = moveBlock.getLevel();
        if (occupancy == OCCUPIED) {
            System.out.println("This position is already occupied!");
            return null;
        }
        // Only move when workerBlock.getLevel() - level >= -1
        if (workerBlock.getLevel() - level < -1) {
            System.out.println("Illegal move!");
            return null;
        }
        workerBlock.setOccupied(UNOCCUPIED);
        moveBlock.setOccupied(OCCUPIED);
        return moveBlock;
    }
    /**
     * Build a tower on the specified position.
     * @param row
     * @param col
     * @param workerBlock
     * @return boolean representing whether the tower was built successfully
     */
    public boolean buildTower(int row, int col, Block workerBlock) {
        if (!validBlock(row, col, workerBlock)) return false;
        Block towerBlock = getBlock(row, col);
        int occupancy = towerBlock.getOccupancy();
        int level = towerBlock.getLevel();
        if (occupancy == OCCUPIED) {
            System.out.println("This position is already occupied!");
            // System.exit(0);
            return false;
        }
        if (level >= COMPLETE_TOWER) {
            System.out.println("Tower can not be built!");
            // System.exit(0);
            return false;
        }
        towerBlock.setLevel();
        System.out.println("Build Tower successfully!");
        return true;
    }
    /**
     * Build a Dome on the specified position.
     * @param row
     * @param col
     * @param workerBlock
     * @return boolean representing whether the Dome was built successfully
     */
    public boolean buildDome(int row, int col, Block workerBlock) {
        if (!validBlock(row, col, workerBlock)) return false;
        Block domeBlock = getBlock(row, col);
        int occupancy = domeBlock.getOccupancy();
        int level = domeBlock.getLevel();
        if (occupancy == OCCUPIED) {
            System.out.println("This position is already occupied!");
            // System.exit(0);
            return false;
        }
        if (level == COMPLETE_TOWER) {
            domeBlock.setLevel();
            System.out.println("Build Dome successfully!");
            return true;
        } else {
            System.out.println("Dome can not be built!");
            // System.exit(0);
            return false;
        }
    }
    /**
     * Check whether this block can be built with a tower.
     * @param row
     * @param col
     * @param workerBlock
     * @return boolean representing whether this block can be built with a tower
     */
    public boolean validBlock(int row, int col, Block workerBlock) {
        if (!validBounds(row, col)) {
            System.out.println("This position is out of Bounds!");
            return false;
        }
        if (!validSurround(row, col, workerBlock)) {
            System.out.println("Please find another position to build!");
            return false;
        }
        return true;
    }
    /**
     * Get the block at the specified position.
     * @param row
     * @param col
     * @return
     */
    Block getBlock(int row, int col) {
        if (!validBounds(row, col)) {
            System.out.println("This position is out of Bounds!");
            return null;
            // System.exit(0);
        }
        return board[row][col];
    }
    /**
     * Check whether the passing-in position is valid.
     * @param row
     * @param col
     * @param workerBlock
     * @return boolean indicating whether the position is valid
     */
    public boolean validSurround(int row, int col, Block workerBlock) {
        int currx = workerBlock.getX();
        int curry = workerBlock.getY();
        boolean valid = (Math.abs(currx - row) <= 1) && (Math.abs(curry - col) <= 1);
        return valid;
    }
    /**
     *  Checks whether the passing-in position is within the board bounds.
     * @param row
     * @param col
     * @return boolean indicating whether the position is within the board bounds
     */
    public boolean validBounds(int row, int col) {
        boolean valid = (0 <= row) && (row < BOARDSIZE) && (0 <= col) && (col < BOARDSIZE);
        return valid;
    }
}
