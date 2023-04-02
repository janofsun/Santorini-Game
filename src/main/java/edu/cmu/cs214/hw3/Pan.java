package edu.cmu.cs214.hw3;
/**
 * You also win if your Worker moves down two or more levels.
 */
public class Pan implements God {
    @Override
    public String getName() {
        return "Pan";
    }
    @Override
    public Boolean getMethod(String name) {
        return false;
    }
    @Override
    public void checkWinState(int x, int y, int workerIdx) {
        Player currPlayer = Game.getCurrentPlayer();
        Block currBlock = currPlayer.getWorkers().get(workerIdx);
        int currLevel = currBlock.getLevel();
        int newLevel = Game.getBoard().getBlock(x, y).getLevel();
        if (currLevel - newLevel >= 2) {
            Game.winnerSet(currPlayer);
            System.out.println("Player " + Game.getPlayers().indexOf(currPlayer) + " wins!");
        }
        if (newLevel == Block.COMPLETE_TOWER) {
            Game.winnerSet(currPlayer);
            System.out.println("Player " + Game.getPlayers().indexOf(currPlayer) + " wins!");
        }
    }
}
