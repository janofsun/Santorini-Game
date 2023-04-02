package edu.cmu.cs214.hw3;

import static edu.cmu.cs214.hw3.Board.BOARDSIZE;

public class Block {
    private int x;
    private int y;
    private int level;
    private int occupancy;
    public static final int DOME = 4;
    public static final int COMPLETE_TOWER = 3;
    public static final int DEFAULT_LEVEL = 0;
    public static final int OCCUPIED = 1;
    public static final int UNOCCUPIED = 0;
    public static final int HASHCODE1 = 17;
    public static final int HASHCODE2 = 31;

    public Block(int x, int y, int level, int occupancy) {
        if (x < 0 || y < 0 || x >= BOARDSIZE|| y >= BOARDSIZE) {
            System.out.println("Out of bounds.");
            // System.exit(0);
            return;
        }
        this.x = x;
        this.y = y;
        this.level = level;
        this.occupancy = occupancy;
    }
    /**
     * return the x position of the block.
     * @return int the x position of the block
     */
    public int getX() {
        return x;
    }
    /**
     * return the y position of the block.
     * @return int the y position of the block
     */
    public int getY() {
        return y;
    }
    /**
     * return the level of the block.
     * @return int the level of the block
     */
    public int getLevel() {
        return level;
    }
    /**
     * return the occupancy of the block.
     * @return int the occupancy of the block
     */
    public int getOccupancy() {
        return occupancy;
    }
    /**
     * set the level of the block.
     */
    public void setLevel() {
        level += 1;
    }
    /**
     * Set the occupancy of the block.
     * @param status
     */
    public void setOccupied(int status) {
        occupancy = status;
    }
    /**
     * Override equals method for comparing two blocks.
     * @param o comparing block
     * @return true if the two blocks are same.g
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        Block block = (Block) o;
        boolean equal = block.getLevel() == this.getLevel() && block.getX() == this.getX() 
        && block.getY() == this.getY() && block.getOccupancy() == this.getOccupancy();
        return equal;
    }
    /**
     * Override hashCode method for comparing.
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = HASHCODE1;
        result = HASHCODE2 * result + this.getX();
        result = HASHCODE2 * result + this.getY();
        result = HASHCODE2 * result + this.getLevel();
        result = HASHCODE2 * result + this.getOccupancy();
        return result;
    }
}
