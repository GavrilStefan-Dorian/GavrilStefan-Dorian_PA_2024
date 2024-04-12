package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Bag {
    private final Queue<Token> tiles;

    public Bag(Queue<Token> tiles) {
        this.tiles = tiles;
    }

    public synchronized List<Token> extractTokens(int howMany) {
        List<Token> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            Token token = tiles.poll();
            if (token != null) {
                extracted.add(token);
            }
        }
        return extracted;
    }
}