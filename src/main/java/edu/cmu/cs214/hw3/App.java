package edu.cmu.cs214.hw3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;
    private final List<Integer> initialX = new ArrayList<>();
    private final List<Integer> initialY = new ArrayList<>();
    private static List<God> godList = new ArrayList<God>();
    // private static Stack<GameState> undoStack = new Stack<>();
    // private static Stack<Player> currPlayerStack = new Stack<>();
    // private static Stack<Player> oppoPlayerStack = new Stack<>();
    // private static Stack<Integer> workerStack = new Stack<>();
    private int workerIndex;
    private int moveWorkerIdx;
    public static final int PORT = 8080;

    public App() throws IOException {
        super(PORT);

        this.game = new GodGame();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/newgame")) {
            this.game = new GodGame();
            godList = new ArrayList<God>();
            // undoStack = new Stack<>();
            // currPlayerStack = new Stack<>();
            // oppoPlayerStack = new Stack<>();
            // workerStack = new Stack<>();
        } else if (uri.equals("/play")) {
            // e.g., /play?x=1&y=1
            int x = Integer.parseInt(params.get("x"));
            int y = Integer.parseInt(params.get("y"));
            // initialize players
            if (Game.getPlayers().size() < 2) {
                initialize(params);
            // choose the operating worker
            } else if (Game.getBoard().getBlock(x, y).getOccupancy() == Block.OCCUPIED
                    && Game.getCurrentPlayer().getWorkers().contains(Game.getBoard().getBlock(x, y))){
                workerIndex = Game.getCurrentPlayer().getWorkerIndex(Game.getBoard().getBlock(x, y));
                System.out.println("Player: "+ Game.getPlayers().indexOf(Game.getCurrentPlayer()) + " workerIndex: " + workerIndex);
            } else if (Game.getCurrentPlayer().getActions() == 0) {
                game.move(x, y, workerIndex);
                moveWorkerIdx = workerIndex;
                System.out.println("Worker " + workerIndex + " moved to " + moveWorkerIdx);
            } else if (Game.getCurrentPlayer().getActions() == 1) {
                if (Game.getBoard().getBlock(x, y).getLevel() < Block.COMPLETE_TOWER) {
                    game.buildTower(x, y, moveWorkerIdx);
                } else if (Game.getBoard().getBlock(x, y).getLevel() == Block.COMPLETE_TOWER) {
                    game.buildDome(x, y, moveWorkerIdx);
                }
            }
        } else if (uri.equals("/demeter")) {
            System.out.println("Demeter selected");
            godList.add(new Demeter());
        } else if (uri.equals("/pan")) {
            System.out.println("Pan selected");
            godList.add(new Pan());
        } else if (uri.equals("/minotaur")) {
            System.out.println("Minotaur selected");
            godList.add(new Minotaur());
        } else if (uri.equals("/nogod")) {
            System.out.println("No God card selected");
            godList.add(new NoGod());
        } else if (uri.equals("/skip")) {
            System.out.println("Skip the optional action.");
            game.switchPlayer();
        } 
        // else if (uri.equals("/undo")) {
        //     System.out.println("Undo the last action.");
        //     if (undoStack.size() > 1) {
        //         // Set the game state to the previous state.
        //         undoStack.pop();
        //         GameState lastState = undoStack.peek();
        //         // System.out.println("undoStack size is " + undoStack.size());
        //         // System.out.println(lastState.toString());
        //         // Set the players to the previous players with proper actions.
        //         currPlayerStack.pop();
        //         Game.setCurrentPlayer(currPlayerStack.peek());
        //         System.out.println(currPlayerStack.peek().toString());
        //         oppoPlayerStack.pop();
        //         Game.setOppoPlayer(oppoPlayerStack.peek());
        //         // Set the current moveWorkerIdx to the previous moveWorkerIdx.
        //         workerStack.pop();
        //         moveWorkerIdx = workerStack.peek();
        //         System.out.println("undoStack size is " + undoStack.size() + " currPlayerStack size is " + 
        //                         currPlayerStack.size() + " oppoPlayerStack size is " + oppoPlayerStack.size() +
        //                         " workerStack size is " + workerStack.size());
        //         return newFixedLengthResponse(lastState.toString());
        //     }
        // }
        // System.out.println("Continue Playing!");
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game);
        if (uri.equals("/favicon.ico")) {
            return newFixedLengthResponse(gameplay.toString());
        }
        // if (Game.getPlayers().size() == 2) {
        //     undoStack.push(GameState.forGame(this.game));
        //     currPlayerStack.push(Game.getCurrentPlayer());
        //     System.out.println(Game.getCurrentPlayer().toString());
        //     Player oppoPlayer = Game.getPlayers().get(1 - Game.getPlayers().indexOf(Game.getCurrentPlayer()));
        //     oppoPlayerStack.push(oppoPlayer);
        //     workerStack.push(moveWorkerIdx);
        //     System.out.println("undoStack size is " + undoStack.size() + " currPlayerStack size is " + 
        //                     currPlayerStack.size() + " oppoPlayerStack size is " + oppoPlayerStack.size() + 
        //                     " workerStack size is " + workerStack.size());
        //     for (int i = 0; i < undoStack.size(); i++) {
        //         System.out.println(currPlayerStack.get(i).toString());
        //     }
        // }
        // System.out.println("undoStack is : " + undoStack);
        // System.out.println(gameplay.toString());
        return newFixedLengthResponse(gameplay.toString());
    }

    private void initialize(Map<String, String> params) {
        if (initialX.size() == 0) {
            initialX.add(Integer.parseInt(params.get("x")));
            initialY.add(Integer.parseInt(params.get("y")));
        } else if (initialX.size() == 1) {
            initialX.add(Integer.parseInt(params.get("x")));
            initialY.add(Integer.parseInt(params.get("y")));
            boolean init = this.game.initiatePlayer(initialX.get(0), initialY.get(0), initialX.get(1), initialY.get(1));
            // System.out.println("Current player: " + Game.getPlayers().indexOf(Game.getCurrentPlayer()));
            if (init) this.game.switchPlayer();
            if (Game.getPlayers().size() == 2) {
                for (int i = 0; i < Game.getPlayers().size(); i++) {
                    ((GodGame) this.game).selectGod(Game.getPlayers().get(i),godList.get(i));
                }
                System.out.println("Player 1: " + godList.get(0).getName() + " Player 2: " + godList.get(1).getName());
                this.game.switchPlayer();
            }
            // System.out.println("Next player: " + Game.getPlayers().indexOf(Game.getCurrentPlayer()));
            initialX.clear();
            initialY.clear();
        }
    }
}
