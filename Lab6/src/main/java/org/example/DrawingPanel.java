package org.example;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class DrawingPanel extends JPanel implements Serializable {
    final MainFrame frame;
    boolean playerOneTurn = true;
    boolean gameOver = false;
    boolean generateSticks = true;
    int canvasWidth = 400, canvasHeight = 400;
    int rows, cols;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    int stoneSize = 20;
    int[][] grid;// 0 -> unpicked/available, 1 -> picked by player1, 2 -> picked by player2
    Graph<Integer, DefaultEdge> subgraph;// for ease of use in the AI strategy
    List<Color> colors;// gray -> unpicked, blue -> player 1, red -> player2
    int lastPlacedStone = -1;
    transient BufferedImage image;
    transient Graphics2D offscreen;

    AIPlayer aiPlayer;
    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        colors = new ArrayList<>();

        int rows = frame.configPanel.getRows();
        int cols = frame.configPanel.getCols();
        grid = new int[rows][cols];
        subgraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        for(int i = 0; i < rows * cols; i++)
            subgraph.addVertex(i);

        for(int[] row : grid)
            Arrays.fill(row, -1);

        colors.addAll(List.of(new Color[]{Color.LIGHT_GRAY, Color.BLUE, Color.RED}));
        init(rows, cols);

        aiPlayer = new AIPlayer(this);
        aiPlayer.startPlaying();
    }
    //Draw the offscreen image, using the original graphics
    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, 0, this);
    }
    @Override
    public void update(Graphics g) {
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
        init(frame.configPanel.getRows(), frame.configPanel.getCols());
        repaint();
    }
    private void createOffscreenImage() {
        image = new BufferedImage(
                canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        offscreen = image.createGraphics();
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
    }
    final void init(int rows, int cols) {
        createOffscreenImage();

        this.rows = rows;
        this.cols = cols;
        this.padX = stoneSize + 10;
        this.padY = stoneSize + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        paintGrid();
        paintComponent(offscreen);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                playerDrawStone(e.getX(), e.getY());
                repaint();
            }
        });
    }
    private void paintGrid() {
        offscreen.setColor(Color.DARK_GRAY);
        //horizontal lines
        for (int row = 0; row < rows; row++) {
            int x1 = padX;
            int y1 = padY + row * cellHeight;
            int x2 = padX + boardWidth;
            int y2 = y1;
            offscreen.drawLine(x1, y1, x2, y2);
        }
        //vertical lines
        for (int col = 0; col < rows; col++) {
            int x1 = padX + col * cellWidth;
            int y1 = padY;
            int x2 = x1;
            int y2 = padY + boardHeight;
            offscreen.drawLine(x1, y1, x2, y2);
        }
        //intersections
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;

                // DRAW ALL STONES EMPTY
                offscreen.setColor(Color.LIGHT_GRAY);
                offscreen.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
            }
        }

        if(generateSticks)
            generateSticks();

        // draw the sticks after generation/loading
        offscreen.setColor(Color.BLACK);
        offscreen.setStroke(new BasicStroke(3));

        for(var edge : subgraph.edgeSet()) {
            int x1 = padX + (subgraph.getEdgeSource(edge) % cols) * cellWidth;
            int y1 = padY + (subgraph.getEdgeSource(edge) / cols) * cellHeight;
            int x2 = padX + (subgraph.getEdgeTarget(edge) % cols) * cellWidth;
            int y2 = padY + (subgraph.getEdgeTarget(edge) / cols) * cellHeight;
            offscreen.drawLine(x1, y1, x2, y2);
        }
        offscreen.setStroke(new BasicStroke(1));

        // stored placed stones
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;

                if(grid[row][col] > 0) {
                    offscreen.setColor(colors.get(grid[row][col]));
                    offscreen.fillOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
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

        if(startPointRow - 1 > -1) {
            dx.add(0);
            dy.add(-1);
        }
        if(startPointRow + 1 < rows) {
            dx.add(0);
            dy.add(1);
        }
        if(startPointCol - 1 > -1) {
            dx.add(-1);
            dy.add(0);
        }
        if(startPointCol + 1 < cols) {
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

        while(stickCount > 0) {
            startPoint = connectedNodes.get(random.nextInt(0,connectedNodes.size()));
            startPointRow = startPoint / cols;
            startPointCol = startPoint % cols;

            dx.clear();
            dy.clear();
            if(startPointRow - 1 > -1) {
                dx.add(0);
                dy.add(-1);
            }
            if(startPointRow + 1 < rows) {
                dx.add(0);
                dy.add(1);
            }
            // above and below nodes
            if(startPointCol - 1 > -1) {
                dx.add(-1);
                dy.add(0);
            }
            if(startPointCol + 1 < cols) {
                dx.add(1);
                dy.add(0);
            }

            randomIndex = random.nextInt(0, dx.size());
            endPoint = startPoint + dx.get(randomIndex) + dy.get(randomIndex) * cols;

            if(subgraph.containsEdge(startPoint, endPoint))
                continue;

            subgraph.addEdge(startPoint, endPoint);
            grid[endPoint / cols][endPoint % cols] = 0;

            stickCount -= 1;

            if(!connectedNodes.contains(endPoint))
                connectedNodes.add(endPoint);
        }
    }

    public void playerDrawStone(int x, int y) {
        drawStone(x, y);
        // after a human's action, queue the AI's action
        AIDrawStone();
    }

    private void AIDrawStone() {
        aiPlayer.drawStone();
    }
    public void drawStone(int x, int y) {
        if(playerOneTurn)
            offscreen.setColor(Color.BLUE);
        else
            offscreen.setColor(Color.RED);
        validateMove(x, y);

    }
    private void validateMove(int x, int y) {
        Point selectedPoint = new Point(x, y);
        outerLoop:
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++) {
                int nodeX = padX + col * cellWidth;
                int nodeY = padY + row * cellHeight;
                Point point = new Point(nodeX, nodeY);

                // player made a valid move
                if ((point.distance(selectedPoint) <= (double) stoneSize / 2) // fits in the circle's radius
                        && grid[row][col] == 0
                        && (lastPlacedStone == -1 || subgraph.containsEdge(lastPlacedStone, row * cols + col))) {
                    offscreen.fillOval(nodeX - stoneSize / 2, nodeY - stoneSize / 2, stoneSize, stoneSize);
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
        if(row - 1 > 0)
            if(grid[row - 1][col] == 0 && subgraph.containsEdge(row * cols + col, (row - 1) * cols + col))
                return;
        if(row + 1 < rows)
            if(grid[row + 1][col] == 0 && subgraph.containsEdge(row * cols + col, (row + 1) * cols + col))
                return;
        if(col - 1 > 0)
            if(grid[row][col - 1] == 0 && subgraph.containsEdge(row * cols + col, row * cols + col - 1))
                return;
        if(col + 1 < cols)
            if(grid[row][col + 1] == 0 && subgraph.containsEdge(row * cols + col, row * cols + col + 1))
                return;
        // getting here means no available next move
        System.out.println(playerOneTurn ? "Player 2 won!" : "Player 1 won!");
        gameOver = true;
    }
}