package edu.cmu.cs214.hw3;

import java.util.Arrays;
import java.util.List;

import static edu.cmu.cs214.hw3.Block.OCCUPIED;
import static edu.cmu.cs214.hw3.Block.DOME;

public final class GameState {
    private final Cell[] cells;
    private static Player winner;
    private final int turn;
    public static final int CELL_SIZE = 25;
    public static final int BOUND = 5;
    public static final int COEFFICIENT = 5;

    private GameState(Cell[] cells, Player winner, int turn) {
        this.cells = cells;
        this.turn = turn;
        GameState.winner = winner;
    }

    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game);
        Player winner = getWinner(game);
        int turn = getTurn();
        return new GameState(cells, winner, turn);
    }

    @Override
    public String toString() {
        if (GameState.winner == null) {
            return """
                    {
                        "cells": %s,
                        "turn": %s
                    }
                    """.formatted(Arrays.toString(this.cells), String.valueOf(this.turn));
        }
        int rtnWinner = Game.getPlayers().indexOf(GameState.winner);
        return """
                {
                    "cells": %s,
                    "turn": %s,
                    "winner": %s
                }
                """.formatted(Arrays.toString(this.cells), String.valueOf(this.turn), String.valueOf(rtnWinner));
    }

    private static Cell[] getCells(Game godGame) {
        Cell[] cells = new Cell[CELL_SIZE];
        Board board = Game.getBoard();
        for (int x = 0; x < BOUND; x++) {
            for (int y = 0; y < BOUND; y++) {
                String textPlayer = "";
                String textLevel = "";
                String link = "/play?x=" + x + "&y=" + y;
                String godCard = "";
                List<Player> players = Game.getPlayers();
                if (board.getBlock(x, y).getOccupancy() == OCCUPIED) {
                    if (players.get(0).getWorkers().contains(board.getBlock(x, y))) {
                        textPlayer = "X";
                        if (((edu.cmu.cs214.hw3.GodGame) godGame).getGodCards().containsKey(players.get(0))) {
                            godCard = ((edu.cmu.cs214.hw3.GodGame) godGame).getGodCards().get(players.get(0)).getName();
                        }
                    }
                    else if (players.get(1).getWorkers().contains(board.getBlock(x, y))) {
                        textPlayer = "O";
                        if (((edu.cmu.cs214.hw3.GodGame) godGame).getGodCards().containsKey(players.get(1))) {
                            godCard = ((edu.cmu.cs214.hw3.GodGame) godGame).getGodCards().get(players.get(1)).getName();
                        }
                    }
                }
                if (board.getBlock(x, y).getLevel() != 0) {
                    if (board.getBlock(x, y).getLevel() == DOME) textLevel = "Dome";
                    else textLevel = "Level"+String.valueOf(board.getBlock(x, y).getLevel());
                }
                cells[COEFFICIENT * y + x] = new Cell(textPlayer, textLevel, link, godCard);
            }
        }
        return cells;
    }

    private static Player getWinner(Game game) {
        return Game.getWinner();
    }

    private static int getTurn() {
        if (Game.getPlayers().indexOf(Game.getCurrentPlayer()) == -1) return 0;
        if (Game.getPlayers().size() == 1) return 1;
        return Game.getPlayers().indexOf(Game.getCurrentPlayer());
    }
}
class Cell {
    private final String textPlayer;
    private final String textLevel;
    private final String link;
    private final String godCard;

    Cell(String textPlayer, String textLevel, String link, String godCard) {
        this.textPlayer = textPlayer;
        this.textLevel = textLevel;
        this.link = link;
        this.godCard = godCard;
    }

    public String getTextPlayer() {
        return this.textPlayer;
    }

    public String getTextLevel() {
        return this.textLevel;
    }

    public String getLink() {
        return this.link;
    }

    public String getGodCard() {
        return this.godCard;
    }

    @Override
    public String toString() {
        return """
                {
                    "textPlayer": "%s",
                    "textLevel": "%s",
                    "link": "%s",
                    "godCard": "%s"
                }
                """.formatted(this.textPlayer, this.textLevel, this.link, this.godCard);
    }
}