package com.app.sipaela.helpers;

import javafx.scene.control.Alert;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Chrisdion Andrew on 11/01/22 13.51
 */
public class Connection {
    public static java.sql.Connection doConnect() {
        String dbName = "sipaela";
        String url = "jdbc:mysql://localhost:3306/" + dbName;
        String username = "praktikum";
        String password = "password";
        java.sql.Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Koneksi Ke DATABASE Gagal! Karena " + e.getMessage());
            alert.showAndWait();
        }
        return connection;
    }
}
