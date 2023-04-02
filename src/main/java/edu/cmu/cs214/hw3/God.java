package edu.cmu.cs214.hw3;

public interface God {
    String getName();
    Boolean getMethod(String name);
    default void checkWinState(int x, int y, int workerIdx) {
        if (Game.getBoard().getBlock(x, y).getLevel() == Block.COMPLETE_TOWER) {
            Player currPlayer = Game.getCurrentPlayer();
            Game.winnerSet(currPlayer);
            System.out.println("Player " + Game.getPlayers().indexOf(currPlayer) + " wins!");
        }
    }
    default Block executeMove (int x, int y, Block currBlock) {
        Block newBlock = new Block(x, y, Block.DEFAULT_LEVEL, Block.OCCUPIED);
        System.out.println("Move success!");
        return newBlock;
    }
    default boolean executeBuild(int x, int y, int workerIdx) {
        Player currPlayer = Game.getCurrentPlayer();
        currPlayer.setActions(currPlayer.getActions() + 1);
        System.out.println("Build success!");
        return true;
    }
}
