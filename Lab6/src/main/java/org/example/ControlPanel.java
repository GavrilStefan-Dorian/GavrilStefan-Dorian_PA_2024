package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Arrays;

public class ControlPanel extends JPanel implements Serializable{
    final MainFrame frame;
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");
    JButton exitBtn = new JButton("Exit");
    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init(){
        setLayout(new GridLayout(1, 4));

        //configure listeners for all buttons
        loadBtn.addActionListener(this::loadGame);
        saveBtn.addActionListener(this::saveGame);
        exitBtn.addActionListener(this::exitGame);
        add(loadBtn);
        add(saveBtn);
        add(exitBtn);
// ...TODO
    }
    private void exitGame(ActionEvent event) {
        frame.dispose();
    }
    private void loadGame(ActionEvent event) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("saved_game.txt"))) {
            DrawingPanel savedCanvas = (DrawingPanel) in.readObject();
            frame.canvas.playerOneTurn = savedCanvas.playerOneTurn;
            //keep sticks from saved canvas, don't generate new ones
            frame.canvas.generateSticks = false;
            // last move
            frame.canvas.lastPlacedStone = savedCanvas.lastPlacedStone;
            //rows cols
            frame.canvas.subgraph = savedCanvas.subgraph;

            frame.canvas.grid = Arrays.stream(savedCanvas.grid)
                    .map(int[]::clone)
                    .toArray(int[][]::new);

            frame.configPanel.spinnerRows.setValue(savedCanvas.rows);
            frame.configPanel.spinnerCols.setValue(savedCanvas.cols);

            frame.canvas.update(frame.canvas.offscreen);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveGame(ActionEvent event) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("saved_game.txt"))) {
            out.writeObject(frame.canvas);
            ImageIO.write(frame.canvas.image, "PNG", new File("saved_game.png"));
            System.out.println("Saved current state of game");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}




