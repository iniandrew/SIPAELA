module com.app.sipaela {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.app.sipaela to javafx.fxml;
    exports com.app.sipaela;
    exports com.app.sipaela.controllers;
    opens com.app.sipaela.controllers to javafx.fxml;
}