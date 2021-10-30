module com.example.checkersgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens checkers to javafx.fxml;
    exports checkers;
}