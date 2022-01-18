module com.app.sipaela {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    exports com.app.sipaela;

    exports com.app.sipaela.controllers;
    opens com.app.sipaela.controllers to javafx.fxml;

    exports com.app.sipaela.controllers.admin;
    exports com.app.sipaela.controllers.admin.users;
    exports com.app.sipaela.controllers.employee;
    exports com.app.sipaela.controllers.parking;


    opens com.app.sipaela.controllers.admin to javafx.fxml;
    opens com.app.sipaela.controllers.admin.users to javafx.fxml;
    opens com.app.sipaela.controllers.employee to javafx.fxml;
    opens com.app.sipaela.controllers.parking to javafx.fxml;
}