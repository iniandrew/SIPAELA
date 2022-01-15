package com.app.sipaela.helpers;

import javafx.scene.control.Alert;

/**
 * Created by Chrisdion Andrew on 16/01/22 02.28
 */
public class Helpers {
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
