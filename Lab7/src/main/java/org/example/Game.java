package org.example;

import java.util.ArrayList;
import java.util.List;

class Game {
    private final Bag bag;
    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean gameOver = false;

    public Game(Bag bag) {
        this.bag = bag;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        for (Player player : players) {
            new Thread(player).start();
        }
    }

    public synchronized boolean isPlayerTurn(Player player) {
        return players.indexOf(player) == currentPlayerIndex;
    }

    public synchronized Bag getBag() {
        return bag;
    }

    public synchronized void endTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public synchronized boolean isGameOver() {
        return gameOver;
    }

    // Other methods
}
