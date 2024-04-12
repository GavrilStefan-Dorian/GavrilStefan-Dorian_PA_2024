package org.example;

import java.util.ArrayList;
import java.util.List;

public  class Player implements Runnable {
    private final String name;
    private final Game game;
    private final List<Token> tokens = new ArrayList<>();

    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    @Override
    public void run() {
        while (!game.isGameOver()) {
            synchronized (game) {
                if (!game.isPlayerTurn(this)) {
                    try {
                        game.wait(); // Wait for player's turn
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                List<Token> extractedTokens = game.getBag().extractTokens(2); // Extract two tokens
                tokens.addAll(extractedTokens);
                System.out.println(name + " extracted tokens: " + extractedTokens);
                // Logic for sequence creation
                game.endTurn();
                game.notifyAll(); // Notify other players
            }
        }
    }

    // Other methods
}
