package org.example;

import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.DenseEdmondsMaximumCardinalityMatching;
import org.jgrapht.graph.DefaultEdge;

import java.io.Serializable;

public class AIPlayer implements Serializable {
    DrawingPanel canvas;

    MatchingAlgorithm.Matching<Integer, DefaultEdge> matching;

    public AIPlayer(DrawingPanel canvas) {
        this.canvas = canvas;
    }

    public void startPlaying() {
        // check for perfect matching
        var matcher = new DenseEdmondsMaximumCardinalityMatching<>(canvas.subgraph);
        matching = matcher.getMatching();
        int startPoint = 0;

        if(!matching.isPerfect()) {
            for (int node : canvas.subgraph.vertexSet())
                if (!matching.isMatched(node) && canvas.grid[node / canvas.cols][node % canvas.cols] == 0) {
                    startPoint = node;
                    break;
                }
        }
        else {
            for(int node : canvas.subgraph.vertexSet())
                if(canvas.grid[startPoint / canvas.cols][startPoint % canvas.cols] == 0)
                    startPoint = node;
            }

        int row = startPoint / canvas.cols;
        int col = startPoint % canvas.cols;
        for(int i = 0;i < canvas.rows; i ++)
        {
            for(int j = 0; j< canvas.cols; j++)
                System.out.print(canvas.grid[i][j]);
            System.out.println();
        }
        System.out.println(startPoint);
        canvas.drawStone(canvas.padX + col * canvas.cellWidth, canvas.padY + row * canvas.cellHeight);
    }

    public void drawStone() {
        int startPoint = 0;

        if (!canvas.gameOver && canvas.playerOneTurn) {
            for (DefaultEdge edge : canvas.subgraph.edgesOf(canvas.lastPlacedStone)) {
                if (matching.getEdges().contains(edge)) {
                    int source = canvas.subgraph.getEdgeSource(edge);
                    int target = canvas.subgraph.getEdgeTarget(edge);

                    if (source == canvas.lastPlacedStone) {
                        startPoint = target;
                    } else if (target == canvas.lastPlacedStone) {
                        startPoint = source;
                    }
                    break;
                }
            }

            int row = startPoint / canvas.cols;
            int col = startPoint % canvas.cols;
            canvas.drawStone(canvas.padX + col * canvas.cellWidth, canvas.padY + row * canvas.cellHeight);
        }
    }

}
