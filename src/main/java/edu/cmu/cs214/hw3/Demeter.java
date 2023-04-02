package edu.cmu.cs214.hw3;
/**
 * Demeter God card:
 * Your Worker may build one additional time, but not on the same space.
 */
public class Demeter implements God {
    private Block prevBuild;
    private int counter;
    @Override
    public String getName() {
        return "Demeter";
    }
    @Override
    public Boolean getMethod(String name) {
        if (name == "build") return true;
        return false;
    }
    /**
     * Method for Demeter building.
     * @param x x position to build
     * @param y y position to build
     */
    @Override
    public boolean executeBuild(int x, int y, int workerIdx) {
        Player currPlayer = Game.getCurrentPlayer();
        if (counter == 0) {
            prevBuild = Game.getBoard().getBlock(x, y);
            counter++;
            return true;
        } else if (counter == 1) {
            if (prevBuild.getX() == x && prevBuild.getY() == y) {
                System.out.println("Illegal build.");
                // currPlayer.setActions(currPlayer.getActions() + 1);
                return false;
            }
            counter = 0;
            currPlayer.setActions(currPlayer.getActions() + 1);
            return true;
        }
        return false;
    }
}
