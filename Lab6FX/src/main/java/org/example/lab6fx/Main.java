package org.example.lab6fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainFrame mf = new MainFrame();
        BorderPane root = new BorderPane();
        root.setCenter(mf.getScene().getRoot());
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.setTitle("My Drawing Application");
        primaryStage.show();
    }
}
