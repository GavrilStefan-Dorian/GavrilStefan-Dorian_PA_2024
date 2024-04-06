package org.example;

import javax.swing.*;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel label;
    JSpinner spinnerRows, spinnerCols;
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
    }

    public int getRows() {
        return (int) this.spinnerRows.getValue();
    }

    public int getCols() {
        return (int) this.spinnerCols.getValue();
    }
}