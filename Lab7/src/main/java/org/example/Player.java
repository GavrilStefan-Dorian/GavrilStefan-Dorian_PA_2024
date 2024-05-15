package org.example;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.util.Cycle;
import org.graph4j.util.IntArrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private List<Tile> tiles = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    Graph graph;
    private int score;
    private final boolean isSmart;

    public Player(String name, boolean isSmart) {
        this.name = name;
        this.isSmart = isSmart;
        graph = GraphBuilder.empty().buildGraph();

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
                if(!pickedTiles.isEmpty()) {
                    tiles.addAll(pickedTiles);
                    graph.addEdge(pickedTiles.get(0).getFirst(), pickedTiles.get(0).getSecond());
                }
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
        int n = game.getBag().getSize();
        int tileCount = 0;
        var newGraph = GraphBuilder.empty().buildGraph();

        while (game.isRunning()) {
            List<Tile> currentTiles = new ArrayList<>(tiles);

            if (tileCount != currentTiles.size()) {
                tileCount = currentTiles.size();
                newGraph = GraphBuilder.empty().buildGraph();
                int[] nodes = new int[n];
                int nodeCount = 0;

                Tile firstTile = currentTiles.get(0);
                newGraph.addVertex(firstTile.getFirst());
                newGraph.addVertex(firstTile.getSecond());
                newGraph.addEdge(firstTile.getFirst(), firstTile.getSecond());
                nodes[nodeCount++] = firstTile.getFirst();
                nodes[nodeCount++] = firstTile.getSecond();

                Tile prevTile = firstTile;

                boolean done = false;
                while(!done) {
                    done = true;
                    forLoop:
                    for (Tile tile : currentTiles) {
                        if (tile.getFirst() == prevTile.getSecond()
                                && !newGraph.containsVertex(tile.getSecond())) {
                            newGraph.addVertex(tile.getSecond());
                            newGraph.addEdge(tile.getFirst(), tile.getSecond());
                            nodes[nodeCount++] = tile.getSecond();
                            prevTile = tile;
                            done = false;
                            break forLoop;
                        }
                    }
                }

//                for(Tile tile : currentTiles) {
//                    if(tile.getFirst() == prevTile.getSecond() && tile.getSecond() == firstTile.getFirst()) {
//                        newGraph.addEdge(tile.getFirst(), tile.getSecond());
//                        break;
//                    }
//                }
                if (newGraph.numVertices() == n && game.getFoundWinner() == null) {
                    game.setRunning(false);
                    game.setFoundWinner(this);
                    System.out.println("Sequence is: " + new Cycle(newGraph, nodes));
                }
            }
        }
    }
    public boolean hasOreProperty(Graph graph) {
        int n = graph.numVertices();
        if (n < 3) {
            return false;
        }
        int[] nodes = graph.vertices();
        int[] deg = graph.degrees();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                try {
                    graph.containsEdge(nodes[i], nodes[j]);
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Checking for edge: [" + nodes[i] + ", " + nodes[j] + "]");
                    System.out.println("Graph is: " + graph);
                }
                if (deg[i] + deg[j] < n && !graph.containsEdge(nodes[i], nodes[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private void crissCross() {
        long m = 0;
        while (game.isRunning()) {
            lock.lock();
                if(graph.numEdges() != m) {
                    m = graph.numEdges();
                    if (hasOreProperty(graph)) {
                        game.setRunning(false);
                        game.setFoundWinner(this);
                        System.out.println("Sequence is: " + PalmerAlgorithmCycle(graph));
                    }
                }
            lock.unlock();
            }
        }

    private Cycle PalmerAlgorithmCycle(Graph graph) {
        int n = graph.numVertices();
        int[] nodes = IntArrays.copyOf(graph.vertices());
        boolean foundGap = true;

        while(foundGap){
            foundGap = false;
            for (int i = 0; i < n; i++) {
                // Check for a gap between nodes i and i+1 (clockwise direction)
                if (!graph.containsEdge(nodes[i], nodes[(i + 1) % n])) {
                    foundGap = true;
                    // Find last node of gap
                    int lastNode = (i + 1) % n;
                    for (int j = i + 2; j < i + n; j++) {
                        if (graph.containsEdge(nodes[i], nodes[j % n])
                                && graph.containsEdge(nodes[(i + 1) % n], nodes[(j + 1) % n])) {
                            lastNode = j % n;
                            break;
                        }
                    }

                    // Swap chords and fil two gaps
                    int firstNode = i + 1;
                    if (firstNode >= lastNode) {
                        firstNode = (lastNode + 1) % n;
                        lastNode = i;
                    }
                    for (int offset = 0; offset < (lastNode - firstNode + 1) / 2; offset++) {
                        int temp = nodes[firstNode + offset];
                        nodes[firstNode + offset] = nodes[lastNode - offset];
                        nodes[lastNode - offset] = temp;
                    }
                    break;
                }
            }
        }

        var newHamiltonianGraph = GraphBuilder.empty().buildGraph();
        for (int i = 0; i < n; i++)
            newHamiltonianGraph.addVertex(nodes[i]);
        for (int i = 0; i < n; i++)
            newHamiltonianGraph.addEdge(nodes[i], nodes[(i + 1) % n]);

        return new Cycle(newHamiltonianGraph, nodes);
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
