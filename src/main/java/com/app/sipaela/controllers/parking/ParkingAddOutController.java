package com.app.sipaela.controllers.parking;

import com.app.sipaela.helpers.Connection;
import com.app.sipaela.helpers.Helpers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Chrisdion Andrew on 18/01/22 04.32
 */
public class ParkingAddOutController implements Initializable {
    @FXML
    private Button btnSearch;

    @FXML
    private Button btnSubmit;

    @FXML
    private TextField fieldBiaya;

    @FXML
    private TextField fieldKategori;

    @FXML
    private TextField fieldNomorPolisiKendaraan;

    @FXML
    private TextField fieldTipeKendaraan;

    @FXML
    private TextField fieldWaktuMasuk;

    private Helpers helpers = new Helpers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableField();
        btnSearch.setOnAction(actionEvent -> {
            try {
                searchDataParkir();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnSubmit.setOnAction(actionEvent -> {
            try {
                submitParkingOut();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void disableField() {
        fieldKategori.setDisable(true);
        fieldTipeKendaraan.setDisable(true);
        fieldBiaya.setDisable(true);
        fieldWaktuMasuk.setDisable(true);
    }

    private void searchDataParkir() throws SQLException {
        String query = "SELECT * FROM parking WHERE nopol LIKE" + "'%" + fieldNomorPolisiKendaraan.getText() + "%'" + "AND waktu_masuk LIKE " + "'%" + helpers.getCurrentDate() + "%'" + "AND status = 'IN'";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            fieldKategori.setText(result.getString(3));
            fieldTipeKendaraan.setText(result.getString(4));
            fieldBiaya.setText(result.getString(5));
            fieldWaktuMasuk.setText(result.getString(6));
        } else {
            helpers.showAlert(Alert.AlertType.ERROR, "Error!", "Data Parkir tidak ditemukan");
        }
        statement.close();
    }

    private void submitParkingOut() throws SQLException {
        String query = "UPDATE parking SET waktu_keluar = (?), status = (?) WHERE nopol LIKE" + "'%" + fieldNomorPolisiKendaraan.getText() + "%'" + "AND waktu_masuk LIKE " + "'%" + helpers.getCurrentDate() + "%'";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setString(1, helpers.getCurrentDatetime());
        statement.setString(2, "OUT");
        if (statement.executeUpdate() == 1) {
            helpers.showAlert(Alert.AlertType.INFORMATION, "Berhasil!", "Data Parkir Keluar Berhasil di Tambahkan");
        } else {
            helpers.showAlert(Alert.AlertType.ERROR, "Gagal!", "Data Parkir Gagal di Tambahkan");
        }
        statement.close();
    }
}
