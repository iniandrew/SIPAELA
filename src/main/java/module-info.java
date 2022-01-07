module com.app.sipaela {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.app.sipaela to javafx.fxml;
    exports com.app.sipaela;
    exports com.app.sipaela.controllers;
    opens com.app.sipaela.controllers to javafx.fxml;
}