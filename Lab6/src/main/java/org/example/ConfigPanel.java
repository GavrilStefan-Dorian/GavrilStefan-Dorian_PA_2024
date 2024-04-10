package org.example;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Arrays;

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
        frame.canvas.generateSticks = true;
        frame.canvas.lastPlacedStone = -1;
        frame.canvas.playerOneTurn = true;

        frame.canvas.subgraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        for(int i = 0; i < getRows() * getCols(); i++)
            frame.canvas.subgraph.addVertex(i);

        frame.canvas.grid = new int[getRows()][getCols()];
        for(int[] row : frame.canvas.grid)
            Arrays.fill(row, -1);

        frame.canvas.update(frame.canvas.offscreen);
    }
}
