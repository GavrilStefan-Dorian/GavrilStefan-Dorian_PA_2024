package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");
    JButton exitBtn = new JButton("Exit");
    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        setLayout(new GridLayout(1, 4));
        //add all buttons ...TODO
        //configure listeners for all buttons
        //loadBtn.addActionListener(this::loadGame);
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

    }

}




