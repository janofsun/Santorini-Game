package edu.cmu.cs214.hw3;

import java.util.ArrayList;
import java.util.List;

import static edu.cmu.cs214.hw3.Block.OCCUPIED;
import static edu.cmu.cs214.hw3.Block.COMPLETE_TOWER;

public class Game {
    private static Board board;
    private static List<Player> players;
    private static Player winner;
    private static Player currPlayer;
    /**
     * Default constructor.
     */
    public Game() {
        winner = null;
        Game.currPlayer = null;
        players = new ArrayList<Player>();
        initiateBoard();
    }
    /**
     * Constructor for a new game.
     * @param x11
     * @param y11
     * @param x12
     * @param y12
     * @param x21
     * @param y21
     * @param x22
     * @param y22
     */
    public Game(int x11, int y11, int x12, int y12, int x21, int y21, int x22, int y22) {
        winner = null;
        players = new ArrayList<Player>();
        initiateBoard();
        initiatePlayer(x11, y11, x12, y12);
        initiatePlayer(x21, y21, x22, y22);
    }
    /**
     * Move the current player.
     * @param row
     * @param col
     * @param workerIndex
     */
    public void move(int row, int col, int workerIndex) {
        Player player = getCurrentPlayer();
        if (winner != null || player.getActions() != 0) {
            System.out.print(String.valueOf(player.getActions()));
            System.out.print("Illegal moving.");
            // System.exit(0);
            return;
        }
        Block currBlock = player.getWorkers().get(workerIndex);
        Block update = board.move(row, col, currBlock);
        if (update == null) return;
        player.setWorkers(workerIndex, update);
        player.setActions(1);
        if (update.getLevel() == COMPLETE_TOWER) {
            winner = player;
            System.out.println("Player " + players.indexOf(winner) + " wins !");
        }
    }
    /**
     * Build a tower.
     * @param row
     * @param col
     * @param workerIndex
     */
    public void buildTower(int row, int col, int workerIndex) {
        Player player = getCurrentPlayer();
        if (winner != null || player.getActions() != 1) {
            System.out.print("Illegal building.");
            // System.exit(0);
            return;
        }
        Block currBlock = player.getWorkers().get(workerIndex);
        if (!board.buildTower(row, col, currBlock)) return;
        player.setActions(2);
        if (isRoundOver()) switchPlayer();
    }
    /**
     * Build a dome.
     * @param row
     * @param col
     * @param workerIndex
     */
    public void buildDome(int row, int col, int workerIndex) {
        Player player = getCurrentPlayer();
        if (winner != null || player.getActions() != 1) {
            System.out.print("Illegal building.");
            // System.exit(0);
            return;
        }
        Block currBlock = player.getWorkers().get(workerIndex);
        if (!board.buildDome(row, col, currBlock)) return;
        player.setActions(2);
        if (isRoundOver()) switchPlayer();
    }
    /**
     * Initialize a new Player by positions.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return player object
     */
    public boolean initiatePlayer(int x1, int y1, int x2, int y2) {
        if (board.getBlock(x1, y1) == null || board.getBlock(x2, y2) == null) {
            System.out.print("This block is out of bound.");
            return false;
        }
        if (board.getBlock(x1, y1).getOccupancy() == 0 && board.getBlock(x2, y2).getOccupancy() == 0) {
            Player player = new Player(board.getBlock(x1, y1), board.getBlock(x2, y2));
            if (player.getWorkers() == null) return false;
            players.add(player);
            return true;
        } else {
            System.out.print("This block is occupied.");
            return false;
        }
    }
    /**
     * Initiate the board.
     */
    private void initiateBoard() {
        board = new Board();
    }
    /**
     * Get the current worker index.
     * @param x
     * @param y
     * @return index of the worker
     */
    public int getWorkerIdx(int x, int y) {
        Block currBlock = board.getBlock(x, y);
        if (currBlock.getOccupancy() == OCCUPIED) {
            Player player = getCurrentPlayer();
            if (player.getWorkers().contains(currBlock)) {
                return player.getWorkers().indexOf(currBlock);
            }
        }
        System.out.println("Invalid position.");
        return -1;
    }
    /**
     * Switch the player for another round.
     */
    public void switchPlayer() {
        if (Game.currPlayer == null) {
            Game.currPlayer = players.get(0);
        } else {
            int idx = 1 - players.indexOf(Game.currPlayer);
            Game.currPlayer.setActions(0);
            Game.currPlayer = players.get(idx);
        }
    }
    /**
     * return the next player.
     * @return int
     */
    public static int getNextPlayer() {
        int idx = 1 - players.indexOf(Game.currPlayer);
        return idx;
    }
    /**
     * Check if this round is over.
     * @return boolean
     */
    public boolean isRoundOver() {
        if (Game.currPlayer.getActions() == 2) return true;
        return false;
    }
    /**
     * Get the board.
     * @return Board
     */
    public static Board getBoard() {
        return board;
    }
    /**
     * Get the current player.
     * @return Player
     */
    public static Player getCurrentPlayer() {
        return Game.currPlayer;
    }
    /**
     * Get the winner.
     * @return winner
     */
    public static Player getWinner(){
        return winner;
    }
    /**
     * Get the list of players.
     * @return List of players
     */
    public static List<Player> getPlayers() {
        return players;
    }
    /**
     * Winner setter.
     * @param winner
     */
    public static void winnerSet(Player winner) {
        Game.winner = winner;
    }
    /**
     * Set the current player.
     * @param player
     */
    public static void setCurrentPlayer(Player player) {
        Block workerBlock1 = player.getWorkers().get(0);
        Block workerBlock2 = player.getWorkers().get(1);
        int action = player.getActions();
        Game.currPlayer.setWorkers(0, workerBlock1);
        Game.currPlayer.setWorkers(1, workerBlock2);
        Game.currPlayer.setActions(action);
    }
    /**
     * Set the opponent player.
     * @param player
     */
    public static void setOppoPlayer(Player player) {
        Block workerBlock1 = player.getWorkers().get(0);
        Block workerBlock2 = player.getWorkers().get(1);
        int idx = 1 - players.indexOf(Game.currPlayer);
        Game.players.get(idx).setWorkers(0, workerBlock1);
        Game.players.get(idx).setWorkers(1, workerBlock2);
    }
}
