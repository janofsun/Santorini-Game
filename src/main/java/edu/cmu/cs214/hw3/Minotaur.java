package edu.cmu.cs214.hw3;
/**
 * Your Worker may move into an opponent Worker’s space, 
 * if their Worker can be forced one space straight backwards 
 * to an unoccupied space at any level.
 */
public class Minotaur implements God {
    @Override
    public String getName() {
        return "Minotaur";
    }
    @Override
    public Boolean getMethod(String name) {
        if (name == "move") return true;
        return false;
    }
    private boolean backward(Block curr, Block oppo) {
        Boolean backWard = Game.getBoard().validBlock(2*oppo.getX()-curr.getX(), 2*oppo.getY()-curr.getY(), oppo);
        Boolean unoccupied = Game.getBoard().getBlock(2*oppo.getX()-curr.getX(), 2*oppo.getY()-curr.getY()).getOccupancy() == Block.UNOCCUPIED;
        return backWard && unoccupied;
    }
    @Override
    public Block executeMove(int x, int y, Block workerBlock) {
        Player currPlayer = Game.getCurrentPlayer();
        Player oppoPlayer = Game.getPlayers().get(Game.getNextPlayer());
        int workerIdx = currPlayer.getWorkers().indexOf(workerBlock);
        Block oppoBlock = Game.getBoard().getBlock(x, y);
        if (oppoPlayer.getWorkers().contains(oppoBlock) && backward(workerBlock, oppoBlock)) {
            int oppoWorkerIdx = oppoPlayer.getWorkers().indexOf(oppoBlock);
            Block newOppoBlock =  Game.getBoard().getBlock(2*oppoBlock.getX()-workerBlock.getX(), 2*oppoBlock.getY()-workerBlock.getY());
            oppoPlayer.setWorkers(oppoWorkerIdx, newOppoBlock);
            currPlayer.setWorkers(workerIdx, oppoBlock);
            workerBlock.setOccupied(Block.UNOCCUPIED);
            newOppoBlock.setOccupied(Block.OCCUPIED);
            currPlayer.setActions(currPlayer.getActions() + 1);
            return oppoBlock;
        }
        return null;
    }
}
