package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class ConfigPanel extends JPanel implements Serializable {
    final MainFrame frame;
    JLabel label;
    JSpinner spinnerRows, spinnerCols;

    JButton createBtn = new JButton("Create");
    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        label = new JLabel("Grid size:");
        spinnerRows = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        spinnerCols = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));

        add(label);
        add(spinnerRows);
        add(spinnerCols);

        createBtn.addActionListener((this::createBoard));
        add(createBtn);
    }

    public int getRows() {
        return (int) this.spinnerRows.getValue();
    }

    public int getCols() {
        return (int) this.spinnerCols.getValue();
    }

    public void createBoard(ActionEvent event) {
        //frame.canvas.stones.clear();
        frame.canvas.generateSticks = true;
        frame.canvas.lastPlacedStone = -1;
        frame.canvas.playerOneTurn = true;
        frame.canvas.update(frame.canvas.offscreen);
    }
}
