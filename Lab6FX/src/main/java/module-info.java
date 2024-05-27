module org.example.lab6fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;
    requires java.desktop;


    opens org.example.lab6fx to javafx.fxml;
    exports org.example.lab6fx;
}