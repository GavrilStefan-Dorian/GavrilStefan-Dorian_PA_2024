package org.example;

//import org.graph4j.Graph;
//import org.graph4j.GraphBuilder;
//import org.jgrapht.Graph;

import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.alg.tour.PalmerHamiltonianCycle;
import org.jgrapht.graph.SimpleGraph;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private List<Tile> tiles = new ArrayList<>();
    Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    private int score;
    private boolean isSmart;

    public Player(String name, boolean isSmart) {
        this.name = name;
        this.isSmart = isSmart;

    }

    public void run() {
        for (int i = 0; i < game.getBag().getSize(); i++)
            graph.addVertex(i);

        Thread bgThread = isSmart ? new Thread(this::crissCross) : new Thread(this::checkSequence);
        bgThread.start();

        while (game.isRunning()) {
            synchronized (game.getBag()) {
                try {
                    if (game.getFoundWinner() == null && game.isRunning()) {
                        System.out.println(this.name + " waiting for next turn...");
                        game.getBag().wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                List<Tile> pickedTiles = game.getBag().extractTile(1);
                tiles.addAll(pickedTiles);
                graph.addEdge(pickedTiles.get(0).getFirst(), pickedTiles.get(0).getSecond());
                this.score = tiles.size();

                if (game.getBag().getTiles().isEmpty() || game.getFoundWinner() != null) {
                    game.setRunning(false);
                    game.getBag().notifyAll();
                    break;
                }

                System.out.println(name + " picked tile: ["
                        + pickedTiles.get(0).getFirst() + ", " + pickedTiles.get(0).getSecond() + "] Score: " + score);

            }
        }
        try {
            bgThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkSequence() {
        List<Integer> numbers = new ArrayList<>();
        int n = game.getBag().getSize();
        int tileCount = 0;

        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        while (game.isRunning()) {
            List<Tile> currentTiles;
            synchronized (tiles) {
                currentTiles = new ArrayList<>(tiles);
            }

            if (tileCount != currentTiles.size()) {
                tileCount = currentTiles.size();

                for (Tile tile : currentTiles) {
                    numbers.remove((Object) tile.getFirst());
                    numbers.remove((Object) tile.getSecond());
                }
                if (numbers.isEmpty() && game.getFoundWinner() == null) {
                    game.setRunning(false);
                    game.setFoundWinner(this);
                }
            }
        }
    }

    private void crissCross() {
//        var g = GraphBuilder.numVertices(tiles.size()).buildGraph();

        while (game.isRunning()) {
            if (GraphTests.hasOreProperty(graph)){
                game.setRunning(false);
                game.setFoundWinner(this);
                System.out.println("Sequence is: " + new PalmerHamiltonianCycle<Integer, DefaultEdge>().getTour(graph));
            }
        }
//        for (int i = 0; i < n; i++) {
//            for (int j = i + 1; j < n; j++) {
//                if (graph.containsEdge(i, j) && graph.containsEdge(j, i)) { // Non-adjacent vertices
//                    int degreeSum = graph.degreeOf(i) + graph.degreeOf(j);
//                    if (degreeSum < n) {
//                    }
//                }
//            }
//        }
    }
    public Game getGame() {
        return game;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
