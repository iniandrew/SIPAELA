package com.app.sipaela.helpers;

import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String getCurrentDatetime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public int getParkingQuota() throws SQLException {
        String query = "SELECT kouta_parkir FROM master_settings";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        int quota = 0;

        while (result.next()) {
            quota = Integer.parseInt(result.getString(1));
        }

        return quota;
    }

    public String getAppName() throws SQLException {
        String query = "SELECT nama_aplikasi FROM master_settings";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        String appname = "";

        while (result.next()) {
            appname = result.getString(1);
        }
        return appname;
    }
}
