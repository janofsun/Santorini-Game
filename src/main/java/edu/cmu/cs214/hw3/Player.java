package edu.cmu.cs214.hw3;

import java.util.ArrayList;
import java.util.List;

import static edu.cmu.cs214.hw3.Block.OCCUPIED;
import static edu.cmu.cs214.hw3.Block.UNOCCUPIED;

public class Player {
    /**
     * Actions representing move, build, and nothing.
     * Actions are 0, 1, and 2, representing no action, move and build in order.
     */
    private int actions;
    /**
     * Every player has two workers, each worker occupies a different block.
     * Use a list to store the each worker's current block.
     */
    private List<Block> workers;
    /**
     * Initialize a Player with two workers in different blocks.
     * @param workerBlock1
     * @param workerBlock2
     */
    public Player(Block workerBlock1, Block workerBlock2) {
        if (workerBlock1.getX() == workerBlock2.getX() && workerBlock1.getY() == workerBlock2.getY()) {
            System.out.println("Please select different positions for your workers!");
            return;
        }
        workers = new ArrayList<Block>();
        workerBlock1.setOccupied(OCCUPIED);
        workerBlock2.setOccupied(OCCUPIED);
        workers.add(workerBlock1);
        workers.add(workerBlock2);
        actions = 0;
    }
    /**
     * Set new action state.
     * @param actions 0, 1, 2.
     */
    public void setActions(int actions) {
        this.actions = actions;
    }
    /**
     * Get current action state.
     * @return action state
     */
    public int getActions() {
        return actions;
    }
    /**
     * set the worker and associated block.
     * @param workerIdx the index of the worker
     * @param block the occupied block
     */
    public void setWorkers(int workerIdx, Block block) {
        block.setOccupied(OCCUPIED);
        workers.get(workerIdx).setOccupied(UNOCCUPIED);
        workers.set(workerIdx, block);
    }
    /**
     * get the workers associated block.
     * @return a list of blocks
     */
    public List<Block> getWorkers() {
        return workers;
    }
    /**
     * Get the index of the worker.
     * @param block
     * @return index of the worker
     */
    public int getWorkerIndex(Block block) {
        return workers.indexOf(block);
    }
    @Override
    public String toString() {
        String workerString = " worker 0_x: " +  workers.get(0).getX() + " worker 0_y: " + workers.get(0).getY() + 
                                " worker 1_x: " + workers.get(1).getX() + " worker 1_y: " + workers.get(1).getY();
        return "Player{ " +
                "actions = " + actions + " " +
                workerString + '}';
    }
}
