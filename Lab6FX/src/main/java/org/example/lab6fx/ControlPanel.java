package org.example.lab6fx;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;

import javax.imageio.ImageIO;
import javax.swing.text.Position;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class ControlPanel extends GridPane {
    final MainFrame frame;
    Button loadBtn = new Button("Load");
    Button saveBtn = new Button("Save");
    Button exitBtn = new Button("Exit");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setHgap(10);
        setPadding(new Insets(0, 0, 0, 200));

        // configure listeners for all buttons
        loadBtn.setOnAction(this::loadGame);
        saveBtn.setOnAction(this::saveGame);
        exitBtn.setOnAction(this::exitGame);

        add(loadBtn, 0, 0);
        add(saveBtn, 1, 0);
        add(exitBtn, 2, 0);
    }

    private void exitGame(ActionEvent event) {
        frame.close();
    }
    private void loadGame(ActionEvent event) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("saved_game.txt"))) {
            DrawingPanel savedDrawingPanel = (DrawingPanel) in.readObject();
            frame.drawingPanel.playerOneTurn = savedDrawingPanel.playerOneTurn;
            // keep sticks from saved drawingPanel, don't generate new ones
            frame.drawingPanel.generateSticks = false;
            // last move
            frame.drawingPanel.lastPlacedStone = savedDrawingPanel.lastPlacedStone;
            // rows cols
            frame.drawingPanel.subgraph = savedDrawingPanel.subgraph;
            frame.drawingPanel.aiPlayer = savedDrawingPanel.aiPlayer;

            frame.drawingPanel.grid = Arrays.stream(savedDrawingPanel.grid)
                    .map(int[]::clone)
                    .toArray(int[][]::new);

            frame.configPanel.spinnerRows.getValueFactory().setValue(savedDrawingPanel.rows);
            frame.configPanel.spinnerCols.getValueFactory().setValue(savedDrawingPanel.cols);

            // Redraw the grid and stones
            frame.drawingPanel.paintGrid();
//            frame.drawingPanel.redrawStones();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveGame(ActionEvent event) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("saved_game.txt"))) {
            out.writeObject(frame.drawingPanel);

            // Take a snapshot of the JavaFX scene
            WritableImage image = frame.drawingPanel.snapshot(new SnapshotParameters(), null);

            // Convert WritableImage to BufferedImage manually
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            PixelReader pixelReader = image.getPixelReader();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    bufferedImage.setRGB(x, y, ((PixelReader) pixelReader).getArgb(x, y));
                }
            }

            // Save the BufferedImage to file
            File outputFile = new File("saved_game.png");
            ImageIO.write(bufferedImage, "png", outputFile);

            System.out.println("Saved current state of the game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
