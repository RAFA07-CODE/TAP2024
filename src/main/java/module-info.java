module com.example.tap2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.tap2024 to javafx.fxml;
    opens com.example.tap2024.modelos;
    opens com.example.tap2024.vistas to javafx.base;
    exports com.example.tap2024;
    requires org.kordamp.bootstrapfx.core;
}