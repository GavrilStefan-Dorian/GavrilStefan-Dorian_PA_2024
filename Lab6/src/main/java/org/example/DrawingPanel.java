package org.example;
import org.graph4j.Edge;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class DrawingPanel extends JPanel implements Serializable {
    final MainFrame frame;
    boolean playerOneTurn = true;
    boolean generateSticks = true;
    int canvasWidth = 400, canvasHeight = 400;
    int rows, cols;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    int stoneSize = 20;
    // un array -> tine cont de sticks, stone color cu -1 0 1 2
    int[][] grid;

    transient Graph subgraph;
    List<Color> colors;
    java.util.List<Line2D> sticks;
//    Point[][] nodes;
//    java.util.List<Point> connectedNodes;
//    Map<Point, Color> stones;
    int lastPlacedStone = -1;
    transient BufferedImage image;
    transient Graphics2D offscreen;
    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
//        stones = new HashMap<>();
        colors = new ArrayList<>();
        colors.addAll(List.of(new Color[]{Color.LIGHT_GRAY, Color.BLUE, Color.RED}));
        init(frame.configPanel.getRows(), frame.configPanel.getCols());
    }
    final void init(int rows, int cols) {
        createOffscreenImage();

//        nodes = new Point[rows][cols];
        grid = new int[rows][cols];
        subgraph = GraphBuilder.numVertices(rows * cols).buildGraph();
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
                drawStone(e.getX(), e.getY());
                repaint();
            }
        });
    }
    private void createOffscreenImage() {
        image = new BufferedImage(
                canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        offscreen = image.createGraphics();
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
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

                // store center of node
                Point point = new Point(x, y);
                grid[row][col] = 0;
//                // Graph holds elements indexed 0..rows*cols so need to add as congruent indexes
//                subgraph.addEdge(row * cols + row, col);
//                stones.putIfAbsent(point, Color.LIGHT_GRAY);
//                nodes[row][col] = point;
                // DRAW ALL STONES EMPTY
                offscreen.setColor(Color.LIGHT_GRAY);
                offscreen.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
            }
        }

        if(generateSticks)
            generateSticks();

//        for(Line2D stick : sticks) {
//            offscreen.setColor(Color.BLACK);
//            offscreen.setStroke(new BasicStroke(3));
//            offscreen.drawLine((int) stick.getX1(), (int) stick.getY1(), (int) stick.getX2(), (int) stick.getY2());
//        }

        // draw the sticks after generation/loading
        offscreen.setColor(Color.BLACK);
        offscreen.setStroke(new BasicStroke(3));

        System.out.println(subgraph);

        for(var edge : subgraph.edges()) {
            int x1 = padX + (edge.source() % cols) * cellWidth;
            int y1 = padY + (edge.source() / cols) * cellHeight;
            int x2 = padX + (edge.target() % cols) * cellWidth;
            int y2 = padY + (edge.target() / cols) * cellHeight;
            offscreen.drawLine(x1, y1, x2, y2);
        }
        offscreen.setStroke(new BasicStroke(1));

        // stored placed stones
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;

                // store center of node
//                Point point = new Point(x, y);
                if(!colors.get(grid[row][col]).equals(Color.LIGHT_GRAY)) {
                    offscreen.setColor(colors.get(grid[row][col]));
                    offscreen.fillOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
                }
            }
        }
    }
    @Override
    public void update(Graphics g) {
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
        init(frame.configPanel.getRows(), frame.configPanel.getCols());
        repaint();
    }

    //Draw the offscreen image, using the original graphics
    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, 0, this);
    }
    private void drawStone(int x, int y) {
        if(playerOneTurn)
            offscreen.setColor(Color.BLUE);
        else
            offscreen.setColor(Color.RED);

        Point selectedPoint = new Point(x, y);
        outerLoop:
        for(int row = 0; row < rows; row++)
            for(int col = 0; col < cols; col++) {
                int nodeX = padX + col * cellHeight;
                int nodeY = padY + row * cellWidth;
                Point point = new Point(nodeX, nodeY);

                // player made a valid move
                if(point.distance(selectedPoint) <= (double) stoneSize / 2 && grid[row][col] == 0) {
                        //&& connectedNodes.contains(point)
                        //&& stones.get(point).equals(Color.LIGHT_GRAY)
                //if(lastPlacedStone == -1 || subgraph.containsEdge(lastPlacedStone, row * cols + row)
//                            || (lastPlacedStone.distance(point) <= cellWidth
//                                    && containsStick(new Line2D.Double(point, lastPlacedStone)))
                //) {
                    offscreen.fillOval(nodeX - stoneSize / 2, nodeY - stoneSize / 2, stoneSize, stoneSize);
//                        stones.put(point, offscreen.getColor());
                    grid[row][col] = playerOneTurn ? 1 : 2;
                    lastPlacedStone = row * cols + row;
                    playerOneTurn = !playerOneTurn;

                    // check if winning move
                    // if a neighbour is still not picked, game continues
                    if(row - 1 > 0)
//                            if(stones.get(nodes[row - 1][col]).equals(Color.LIGHT_GRAY)
//                                    && containsStick(new Line2D.Double(lastPlacedStone, nodes[row - 1][col])))
                        if(subgraph.containsEdge(row * cols + row, (row - 1) * cols + row))
                            break outerLoop;
                    if(row + 1 < rows)
//                            if(stones.get(nodes[row + 1][col]).equals(Color.LIGHT_GRAY)
//                                    && containsStick(new Line2D.Double(lastPlacedStone, nodes[row + 1][col])))
                        if(subgraph.containsEdge(row * cols + row, (row + 1) * cols + row))
                            break outerLoop;
                    if(col - 1 > 0)
//                            if(stones.get(nodes[row][col - 1]).equals(Color.LIGHT_GRAY)
//                                    && containsStick(new Line2D.Double(lastPlacedStone, nodes[row][col - 1])))
                        if(subgraph.containsEdge(row * cols + row, row * cols + row - 1))
                            break outerLoop;
                    if(col + 1 < cols)
//                            if(stones.get(nodes[row][col + 1]).equals(Color.LIGHT_GRAY)
//                                    && containsStick(new Line2D.Double(lastPlacedStone, nodes[row][col + 1])))
                        if(subgraph.containsEdge(row * cols + row, row * cols + row + 1))
                            break outerLoop;
                    // getting here means no available next move
                    System.out.println(playerOneTurn ? "Player 2 won!" : "Player 1 won!");
                    break;
                   }
                //}
            }
    }

    private void generateSticks() {
        Random random = new Random();

        int stickCount = rows * (cols - 1) + cols * (rows - 1);
        stickCount = random.nextInt((int) (0.5 * stickCount), stickCount);
        int startPointRow = random.nextInt(0, rows);
        int startPointCol = random.nextInt(0, cols);
//        Point startPoint = nodes[startPointRow][startPointCol];

        int startPoint = startPointRow * cols + startPointCol;

        java.util.List<Integer> dx = new java.util.ArrayList<>();
        java.util.List<Integer> dy = new java.util.ArrayList<>();

        // left and right nodes
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
        // lista de indexes
        // aleg ranodm unul
        // aleg random un vecin de-al lui
        // adaug in subgraph
        // repeat
        // index 28 -> 28 % 10 = 8 ( col ) , 28 / 20 = 2 ( row )

        int randomIndex = random.nextInt(0, dx.size());
//        Point endPoint = nodes[startPointRow + dx.get(randomIndex)][startPointCol + dy.get(randomIndex)];
        int endPoint = startPoint + dx.get(randomIndex) + dy.get(randomIndex) * cols;
        sticks = new ArrayList<>();

        // List to randomly pick from to continue building subgraph
        List<Integer> connectedNodes = new ArrayList<>();
        connectedNodes.add(startPoint);
        connectedNodes.add(endPoint);
//        Line2D stick = new Line2D.Double(startPoint, endPoint);
//        sticks.add(stick);
//        connectedNodes.add(startPoint);
//        connectedNodes.add(endPoint);

//        Map<Integer, Point> nodesPositions = new HashMap<>();
//        nodesPositions.put(startPoint, new Point(startPointRow, startPointCol));
//        nodesPositions.put(endPoint, new Point(startPointRow + dx.get(randomIndex), startPointCol + dy.get(randomIndex)));

//        while(sticks.size() < stickCount) {
        while(stickCount > 0) {
            // pick randomly from already connected nodes
//            startPoint = connectedNodes.get(random.nextInt(0, connectedNodes.size()));
//            startPointRow = nodesPositions.get(startPoint).x;
//            startPointCol = nodesPositions.get(startPoint).y;

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
//            endPoint = nodes[startPointRow + dx.get(randomIndex)][startPointCol + dy.get(randomIndex)];

//            stick = new Line2D.Double(startPoint, endPoint);

            // .equals() works based on reference, so need to compare values
//            if(containsStick(stick))
//                continue whileLoop;

//            sticks.add(stick);

//            for(Point existingNode : connectedNodes)
//                if(existingNode.x == endPoint.x && existingNode.y == endPoint.y)
//                    continue whileLoop;
//            System.out.println(startPointRow + " " + startPointCol);
//            System.out.println(startPoint + " " + endPoint);
            if(subgraph.containsEdge(startPoint, endPoint))
                continue;

            subgraph.addEdge(startPoint, endPoint);
            stickCount -= 1;

            if(!connectedNodes.contains(endPoint))
                connectedNodes.add(endPoint);
//            nodesPositions.put(endPoint, new Point(startPointRow + dx.get(randomIndex), startPointCol + dy.get(randomIndex)));
        }
    }

//    public boolean containsStick(Line2D stick) {
//        for(Line2D existingStick : sticks)
//            if(existingStick.getP1().equals(stick.getP1()) && existingStick.getP2().equals(stick.getP2())
//                    || existingStick.getP1().equals(stick.getP2()) && existingStick.getP2().equals(stick.getP1())
//            )
//                return true;
//        return false;
//    }
}


//
//
//package org.example;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.geom.Line2D;
//import java.awt.image.BufferedImage;
//import java.io.Serializable;
//import java.util.*;
//
//public class DrawingPanel extends JPanel implements Serializable {
//    final MainFrame frame;
//    boolean playerOneTurn = true;
//    boolean generateSticks = true;
//    int canvasWidth = 400, canvasHeight = 400;
//    int rows, cols;
//    int boardWidth, boardHeight;
//    int cellWidth, cellHeight;
//    int padX, padY;
//    int stoneSize = 20;
//
//    Point lastPlacedStone = null;
//    Map<Point, Color> stones;
//    Map<Point, Set<Point>> connections; // Store connections between nodes
//    transient BufferedImage image;
//    transient Graphics2D offscreen;
//
//    public DrawingPanel(MainFrame frame) {
//        this.frame = frame;
//        stones = new HashMap<>();
//        connections = new HashMap<>(); // Initialize connections map
//        init(frame.configPanel.getRows(), frame.configPanel.getCols());
//    }
//
//    final void init(int rows, int cols) {
//        createOffscreenImage();
//
//        this.rows = rows;
//        this.cols = cols;
//        this.padX = stoneSize + 10;
//        this.padY = stoneSize + 10;
//        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
//        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
//        this.boardWidth = (cols - 1) * cellWidth;
//        this.boardHeight = (rows - 1) * cellHeight;
//        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
//
//        this.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                drawStone(e.getX(), e.getY());
//                repaint();
//            }
//        });
//
//        paintGrid();
//        paintComponent(offscreen);
//    }
//
//    private void createOffscreenImage() {
//        image = new BufferedImage(
//                canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
//        offscreen = image.createGraphics();
//        offscreen.setColor(Color.WHITE);
//        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
//    }
//
//    private void paintGrid() {
//        offscreen.setColor(Color.DARK_GRAY);
//        // Draw horizontal lines
//        for (int row = 0; row < rows; row++) {
//            int x1 = padX;
//            int y1 = padY + row * cellHeight;
//            int x2 = padX + boardWidth;
//            int y2 = y1;
//            offscreen.drawLine(x1, y1, x2, y2);
//        }
//        // Draw vertical lines
//        for (int col = 0; col < cols; col++) { // Fixed the loop condition here
//            int x1 = padX + col * cellWidth;
//            int y1 = padY;
//            int x2 = x1;
//            int y2 = padY + boardHeight;
//            offscreen.drawLine(x1, y1, x2, y2);
//        }
//        // Draw intersections (nodes)
//        for (int row = 0; row < rows; row++) {
//            for (int col = 0; col < cols; col++) {
//                int x = padX + col * cellWidth;
//                int y = padY + row * cellHeight;
//
//                // Store center of node
//                Point point = new Point(x, y);
//                stones.putIfAbsent(point, Color.LIGHT_GRAY);
//
//                // Draw node
//                offscreen.setColor(stones.get(point));
//                offscreen.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
//
//                // Initialize empty set of connections for each node
//                connections.put(point, new HashSet<>());
//            }
//        }
//
//        if (generateSticks) {
////             Generate sticks (connections between nodes)
//            generateSticks();
//        }
//
//        // Draw sticks
//        for (Map.Entry<Point, Set<Point>> entry : connections.entrySet()) {
//            Point startPoint = entry.getKey();
//            for (Point endPoint : entry.getValue()) {
//                offscreen.setColor(Color.BLACK);
//                offscreen.setStroke(new BasicStroke(3));
//                offscreen.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
//            }
//        }
//        offscreen.setStroke(new BasicStroke(1));
//
//        // Draw placed stones
//        for (Map.Entry<Point, Color> entry : stones.entrySet()) {
//            Point point = entry.getKey();
//            Color color = entry.getValue();
//            offscreen.setColor(color);
//            if (!color.equals(Color.LIGHT_GRAY)) {
//                offscreen.fillOval(point.x - stoneSize / 2, point.y - stoneSize / 2, stoneSize, stoneSize);
//            }
//        }
//    }
//
//    @Override
//    public void update(Graphics g) {
//        offscreen.setColor(Color.WHITE);
//        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
//        init(frame.configPanel.getRows(), frame.configPanel.getCols());
//        repaint();
//    }
//
//    // Draw the offscreen image, using the original graphics
//    @Override
//    protected void paintComponent(Graphics graphics) {
//        graphics.drawImage(image, 0, 0, this);
//    }
//
//    private void drawStone(int x, int y) {
//        if (playerOneTurn) {
//            offscreen.setColor(Color.BLUE);
//        } else {
//            offscreen.setColor(Color.RED);
//        }
//
//        Point selectedPoint = new Point(x, y);
//        for (Map.Entry<Point, Color> entry : stones.entrySet()) {
//            Point point = entry.getKey();
//            Color color = entry.getValue();
//
//            // Player made a valid move
//            if (point.distance(selectedPoint) <= (double) stoneSize / 2
//                    && color.equals(Color.LIGHT_GRAY)
//                    && connections.get(point).contains(lastPlacedStone)
//                    && (lastPlacedStone == null || lastPlacedStone.distance(point) <= cellWidth)) {
//                offscreen.fillOval(point.x - stoneSize / 2, point.y - stoneSize / 2, stoneSize, stoneSize);
//                stones.put(point, offscreen.getColor());
//                lastPlacedStone = point;
//                playerOneTurn = !playerOneTurn;
//                // Check if winning move
//                // If a neighbor is still not picked, game continues
//                // (Implementation of winning logic needs to be added)
//                break;
//            }
//        }
//    }
//
//    private void generateSticks() {
//        Random random = new Random();
//
//        // Iterate through all nodes
//        for (Map.Entry<Point, Color> entry : stones.entrySet()) {
//            Point startPoint = entry.getKey();
//
//            // Randomly determine the number of sticks for this node
//            int stickCount = random.nextInt(4); // Maximum of 4 connections per node
//
//            // Generate sticks until the desired count is reached
//            while (connections.get(startPoint).size() < stickCount) {
//                // Find a random neighboring node
//                int dx = random.nextInt(-1, 2); // Random offset in x direction (-1, 0, or 1)
//                int dy = dx; // Random offset in y direction (-1, 0, or 1)
//
//                // Skip the current node and out-of-bounds nodes
//                if ((dx == 0 && dy == 0) ||
//                        startPoint.x + dx < 0 || startPoint.x + dx >= cols ||
//                        startPoint.y + dy < 0 || startPoint.y + dy >= rows) {
//                    continue;
//                }
//
//                // Calculate the neighboring node
//                Point endPoint = new Point(startPoint.x + dx, startPoint.y + dy);
//
//                // Add the connection between the nodes
//                connections.get(startPoint).add(endPoint);
//                connections.get(endPoint).add(startPoint); // Also add the connection in the opposite direction
//            }
//        }
//    }
//}
//
