package org.example.lab6fx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFrame extends Stage{
    final ConfigPanel configPanel;
    final ControlPanel controlPanel;
    DrawingPanel drawingPanel;

    public MainFrame() {
        configPanel = new ConfigPanel(this);
        controlPanel = new ControlPanel(this);
        drawingPanel = new DrawingPanel(this);

        BorderPane pane = new BorderPane();
        pane.setTop(configPanel);
        pane.setCenter(drawingPanel);
        pane.setBottom(controlPanel);
        BorderPane.setMargin(configPanel, new Insets(10));
        BorderPane.setMargin(controlPanel, new Insets(10));

        this.setScene(new Scene(pane, 600, 400));
    }
}
