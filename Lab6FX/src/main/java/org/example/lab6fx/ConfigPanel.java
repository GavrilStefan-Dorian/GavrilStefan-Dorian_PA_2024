package org.example.lab6fx;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.io.Serializable;
import java.util.Arrays;

public class ConfigPanel extends HBox {
    final MainFrame frame;
    Label label;
    Spinner<Integer> spinnerRows, spinnerCols;

    Button createBtn = new Button("Create");

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setPadding(new Insets(0, 0, 0, 100));
        label = new Label("Grid size:");
        spinnerRows = new Spinner<>(2, 100, 10, 1);
        spinnerCols = new Spinner<>(2, 100, 10, 1);

        getChildren().addAll(label, spinnerRows, spinnerCols, createBtn);

        createBtn.setOnAction(this::createBoard);
    }

    public int getRows() {
        return spinnerRows.getValue();
    }

    public int getCols() {
        return spinnerCols.getValue();
    }

    public void createBoard(ActionEvent event) {
//        frame.drawingPanel.gc.clearRect(0, 0, frame.drawingPanel.canvasWidth, frame.drawingPanel.canvasHeight);

        // Initialize the drawing panel with the new grid siz_e
        frame.drawingPanel.init(getRows(), getCols());
    }

}
