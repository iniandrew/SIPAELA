package com.app.sipaela.controllers.admin;

import com.app.sipaela.helpers.Connection;
import com.app.sipaela.helpers.Helpers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Chrisdion Andrew on 22/01/22 09.57
 */
public class SettingController implements Initializable  {
    @FXML
    private Button btnSubmit;

    @FXML
    private TextField fieldAppDescription;

    @FXML
    private TextField fieldAppName;

    @FXML
    private TextField fieldQuota;

    private Helpers helpers = new Helpers();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getApplicationInformation();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnSubmit.setOnAction(actionEvent -> {
            try {
                updateApplicationInformation();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void getApplicationInformation() throws SQLException {
        String query = "SELECT * FROM master_settings";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            fieldAppName.setText(result.getString(2));
            fieldAppDescription.setText(result.getString(3));
            fieldQuota.setText(String.valueOf(result.getInt(4)));
        }
    }

    private void updateApplicationInformation() throws SQLException {
        String query = "UPDATE master_settings SET nama_aplikasi = (?), deskripsi_aplikasi = (?), kouta_parkir = (?)";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setString(1, fieldAppName.getText());
        statement.setString(2, fieldAppDescription.getText());
        statement.setInt(3, Integer.parseInt(fieldQuota.getText()));

        if (statement.executeUpdate() == 1) {
            helpers.showAlert(Alert.AlertType.INFORMATION, "Berhasil", "Pengaturan Aplikasi berhasil diperbarui!");
        } else {
            helpers.showAlert(Alert.AlertType.ERROR, "Gagal", "Pengaturan Aplikasi gagal diperbarui!");
        }

    }
}
