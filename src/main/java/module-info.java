module com.example.miniproyecto_sudoku_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;

    opens controller to javafx.fxml;
    exports controller;

    opens model to javafx.base;
    exports model;

    opens View to javafx.fxml;
    exports View;

    opens application to javafx.fxml, javafx.graphics;
    exports application;
}