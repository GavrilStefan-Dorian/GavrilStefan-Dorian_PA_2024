package org.example.lab6fx;

import javafx.geometry.Point2D;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingPanel extends Pane implements Serializable {
    transient final MainFrame frame;
    boolean playerOneTurn = true;
    boolean gameOver = false;
    boolean generateSticks = true;
    int canvasWidth = 500, canvasHeight = 500;
    int rows, cols;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    int stoneSize = 20;
    int[][] grid;// 0 -> unpicked/available, 1 -> picked by player1, 2 -> picked by player2
    Graph<Integer, DefaultEdge> subgraph;// for ease of use in the AI strategy

    transient List<Color> colors;// gray -> unpicked, blue -> player 1, red -> player2
    int lastPlacedStone = -1;
    transient Canvas canvas;
    transient GraphicsContext gc;

    AIPlayer aiPlayer;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        init(frame.configPanel.getRows(), frame.configPanel.getCols());
    }


    void init(int rows, int cols) {
        lastPlacedStone = -1;
        playerOneTurn = true;
        gameOver = false;
        generateSticks = true;

        this.rows = rows;
        this.cols = cols;
        this.padX = stoneSize + 10;
        this.padY = stoneSize + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;

        colors = new ArrayList<>();
        colors.addAll(List.of(Color.LIGHTGRAY, Color.BLUE, Color.RED));

        setOnMouseClicked(this::mouseClicked);

        this.canvas = new Canvas(canvasWidth, canvasHeight);
        this.gc = canvas.getGraphicsContext2D();

//        getChildren().remove(canvas);
        getChildren().add(canvas);

        grid = new int[rows][cols];
        for (int[] row : grid)
            java.util.Arrays.fill(row, -1);

        subgraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        for(int i = 0; i < rows * cols; i++)
            subgraph.addVertex(i);

        paintGrid();

        aiPlayer = new AIPlayer(this);
        aiPlayer.startPlaying();
    }

    void paintGrid() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight); // Clear the canvas

        gc.setStroke(Color.DARKGRAY);
        //horizontal lines
        for (int row = 0; row < rows; row++) {
            int y = padY + row * cellHeight;
            gc.strokeLine(padX, y, padX + boardWidth, y);
        }
        //vertical lines
        for (int col = 0; col < cols; col++) {
            int x = padX + col * cellWidth;
            gc.strokeLine(x, padY, x, padY + boardHeight);
        }
        //intersections
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;

                // DRAW ALL STONES EMPTY
                gc.setStroke(Color.LIGHTGRAY);
                gc.strokeOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
            }
        }

        if (generateSticks)
            generateSticks();

        // draw the sticks after generation/loading
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);

        for (var edge : subgraph.edgeSet()) {
            int x1 = padX + (subgraph.getEdgeSource(edge) % cols) * cellWidth;
            int y1 = padY + (subgraph.getEdgeSource(edge) / cols) * cellHeight;
            int x2 = padX + (subgraph.getEdgeTarget(edge) % cols) * cellWidth;
            int y2 = padY + (subgraph.getEdgeTarget(edge) / cols) * cellHeight;
            gc.strokeLine(x1, y1, x2, y2);
        }
        gc.setLineWidth(1);

        // stored placed stones
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;

                if (grid[row][col] > 0) {
                    gc.setFill(colors.get(grid[row][col]));
                    gc.fillOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
                }
            }
        }
    }


    private void generateSticks() {
        Random random = new Random();

        int stickCount = rows * (cols - 1) + cols * (rows - 1);
        stickCount = random.nextInt((int) (0.85 * stickCount), stickCount);
        int startPointRow = random.nextInt(0, rows);
        int startPointCol = random.nextInt(0, cols);

        grid[startPointRow][startPointCol] = 0;
        int startPoint = startPointRow * cols + startPointCol;

        java.util.List<Integer> dx = new java.util.ArrayList<>();
        java.util.List<Integer> dy = new java.util.ArrayList<>();

        if (startPointRow - 1 > -1) {
            dx.add(0);
            dy.add(-1);
        }
        if (startPointRow + 1 < rows) {
            dx.add(0);
            dy.add(1);
        }
        if (startPointCol - 1 > -1) {
            dx.add(-1);
            dy.add(0);
        }
        if (startPointCol + 1 < cols) {
            dx.add(1);
            dy.add(0);
        }

        int randomIndex = random.nextInt(0, dx.size());
        int endPoint = startPoint + dx.get(randomIndex) + dy.get(randomIndex) * cols;
        grid[endPoint / cols][endPoint % cols] = 0;

        // List to randomly pick from to continue building subgraph
        List<Integer> connectedNodes = new ArrayList<>();
        connectedNodes.add(startPoint);
        connectedNodes.add(endPoint);

        while (stickCount > 0) {
            startPoint = connectedNodes.get(random.nextInt(0, connectedNodes.size()));
            startPointRow = startPoint / cols;
            startPointCol = startPoint % cols;

            dx.clear();
            dy.clear();
            if (startPointRow - 1 > -1) {
                dx.add(0);
                dy.add(-1);
            }
            if (startPointRow + 1 < rows) {
                dx.add(0);
                dy.add(1);
            }
            // above and below nodes
            if (startPointCol - 1 > -1) {
                dx.add(-1);
                dy.add(0);
            }
            if (startPointCol + 1 < cols) {
                dx.add(1);
                dy.add(0);
            }

            randomIndex = random.nextInt(0, dx.size());
            endPoint = startPoint + dx.get(randomIndex) + dy.get(randomIndex) * cols;

            if (subgraph.containsEdge(startPoint, endPoint))
                continue;

            subgraph.addEdge(startPoint, endPoint);
            grid[endPoint / cols][endPoint % cols] = 0;

            stickCount -= 1;

            if (!connectedNodes.contains(endPoint))
                connectedNodes.add(endPoint);
        }
    }

    private void mouseClicked(MouseEvent event) {
        if (!gameOver) {
            double x = event.getX();
            double y = event.getY();
            playerDrawStone(x, y);
        }
    }

    public void playerDrawStone(double x, double y) {
        if (!gameOver) {
            drawStone(x, y);
//             after a human's action, queue the AI's action
            AIDrawStone();
        }
    }

    private void AIDrawStone() {
        aiPlayer.drawStone();
    }

    public void drawStone(double x, double y) {
        if (playerOneTurn)
            gc.setFill(Color.BLUE);
        else
            gc.setFill(Color.RED);
        validateMove(x, y);
    }

    private void validateMove(double x, double y) {
        Point2D selectedPoint = new Point2D(x, y);
        outerLoop:
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++) {
                int nodeX = padX + col * cellWidth;
                int nodeY = padY + row * cellHeight;
                Point2D point = new Point2D(nodeX, nodeY);

                // player made a valid move
                if ((point.distance(selectedPoint) <= (double) stoneSize / 2) // fits in the circle's radius
                        && grid[row][col] == 0
                        && (lastPlacedStone == -1 || subgraph.containsEdge(lastPlacedStone, row * cols + col))) {
                    gc.fillOval(nodeX - stoneSize / 2, nodeY - stoneSize / 2, stoneSize, stoneSize);
                    grid[row][col] = playerOneTurn ? 1 : 2;
                    playerOneTurn = !playerOneTurn;
                    lastPlacedStone = row * cols + col;

                    // check if winning move
                    // if a neighbour is still not picked, game continues
                    checkForWinningMove(row, col);
                    break outerLoop;
                }
            }
    }

    private void checkForWinningMove(int row, int col) {
        if (row - 1 > 0)
            if (grid[row - 1][col] == 0 && subgraph.containsEdge(row * cols + col, (row - 1) * cols + col))
                return;
        if (row + 1 < rows)
            if (grid[row + 1][col] == 0 && subgraph.containsEdge(row * cols + col, (row + 1) * cols + col))
                return;
        if (col - 1 > 0)
            if (grid[row][col - 1] == 0 && subgraph.containsEdge(row * cols + col, row * cols + col - 1))
                return;
        if (col + 1 < cols)
            if (grid[row][col + 1] == 0 && subgraph.containsEdge(row * cols + col, row * cols + col + 1))
                return;
        // getting here means no available next move
        System.out.println(playerOneTurn ? "Player 2 won!" : "Player 1 won!");
        gameOver = true;
    }
}
