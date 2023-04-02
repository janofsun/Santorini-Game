package edu.cmu.cs214.hw3;

import java.util.HashMap;
import java.util.Map;

public class GodGame extends Game {
    private final Map<Player, God> godCards;
    /**
     * Default constructor for GodGame.
     */
    public GodGame() {
        super();
        godCards = new HashMap<Player, God>();
        for (Player player: Game.getPlayers()) {
            godCards.put(player, null);
        }
    }
    public Map<Player, God> getGodCards() {
        return godCards;
    }
    /**
     * Constructor for a GodGame.
     * @param x11
     * @param y11
     * @param x12
     * @param y12
     * @param x21
     * @param y21
     * @param x22
     * @param y22
     */
    public GodGame (int x11, int y11, int x12, int y12, int x21, int y21, int x22, int y22) {
        super (x11, y11, x12, y12, x21, y21, x22, y22);
        godCards = new HashMap<Player, God>();
        for (Player player: Game.getPlayers()) {
            godCards.put(player, null);
        }
    }
    /**
     * Select a god card for the each player.
     * @param curr
     * @param god
     */
    public void selectGod (Player curr, God god) {
        Player oppo = Game.getPlayers().get(1 - Game.getPlayers().indexOf(curr));
        if (godCards.get(curr) == null) {
            if (god != godCards.get(oppo)) godCards.put(curr, god);
            else System.out.println("You cannot select the same god as your opponent.");
        } else {
            System.out.println("You have already selected a god.");
        }
    }
    /**
     * Override the move method in Game with God Cards.
     * @param row
     * @param col
     * @param workerIndex
     */
    @Override
    public void move(int row, int col, int workerIndex) {
        Player currPlayer = getCurrentPlayer();
        God currGod = godCards.get(currPlayer);
        Block currBlock = currPlayer.getWorkers().get(workerIndex);
        if (Game.getWinner() != null || currPlayer.getActions() != 0) {
            System.out.print("Illegal moving.");
            return;
        }
        // if the Minotaur god card is selected, and the move method would be used
        if (getBoard().getBlock(row, col).getOccupancy()==Block.OCCUPIED) {
            if (currGod.getMethod("move")) {
                Block update = currGod.executeMove(row, col, currBlock);
                if (update == null) return;
            } else {
                System.out.println("Illegal moving.");
                return;
            }
        } else {
            currGod.checkWinState(row, col, workerIndex);
            Block update = getBoard().move(row, col, currBlock);
            if (update == null) return;
            currPlayer.setWorkers(workerIndex, update);
            currPlayer.setActions(1);
        }
        // default method for checkWinState is same as the Game with no god cards
        // Pan god card has its own checkWinState method
        currGod.checkWinState(row, col, workerIndex);
    }
    /**
     * Override the buildTower method in Game with God Cards.
     * @param row
     * @param col
     * @param workerIdx
     */
    @Override
    public void buildTower(int row, int col, int workerIdx) {
        Player currPlayer = getCurrentPlayer();
        God currGod = godCards.get(currPlayer);
        if (Game.getWinner() != null || currPlayer.getActions() != 1) {
            System.out.print("Illegal building.");
            return;
        }
        if (currGod.getMethod("build")) {
            if (!currGod.executeBuild(row, col, workerIdx)) return;
            Block currBlock = currPlayer.getWorkers().get(workerIdx);
            if (!getBoard().buildTower(row, col, currBlock)) return;
        } else {
            Block currBlock = currPlayer.getWorkers().get(workerIdx);
            if (!getBoard().buildTower(row, col, currBlock)) return;
            currPlayer.setActions(2);
        }
        // System.out.println(currPlayer.getActions());
        if (isRoundOver()) switchPlayer();
    }
    /**
     * Override the buildDome method in Game with God Cards.
     * @param row
     * @param col
     * @param workerIdx
     */
    @Override
    public void buildDome(int row, int col, int workerIdx) {
        Player currPlayer = getCurrentPlayer();
        God currGod = godCards.get(currPlayer);
        if (Game.getWinner() != null || currPlayer.getActions() != 1) {
            System.out.print("Illegal building.");
            return;
        }
        if (currGod.getMethod("build")) {
            if (!currGod.executeBuild(row, col, workerIdx)) return;
            Block currBlock = currPlayer.getWorkers().get(workerIdx);
            if (!getBoard().buildDome(row, col, currBlock)) return;
        } else {
            Block currBlock = currPlayer.getWorkers().get(workerIdx);
            if (!getBoard().buildDome(row, col, currBlock)) return;
            currPlayer.setActions(2);
        }
        if (isRoundOver()) switchPlayer();
    }
}
