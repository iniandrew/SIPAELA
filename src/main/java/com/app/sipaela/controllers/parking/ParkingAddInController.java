package com.app.sipaela.controllers.parking;

import com.app.sipaela.helpers.Connection;
import com.app.sipaela.helpers.Helpers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Chrisdion Andrew on 18/01/22 04.14
 */
public class ParkingAddInController implements Initializable {
    @FXML
    private Button btnSubmit;

    @FXML
    private TextField fieldBiaya;

    @FXML
    private ComboBox<String> fieldKategori;

    @FXML
    private TextField fieldNomorPolisiKendaraan;

    @FXML
    private RadioButton fieldOptionMobil;

    @FXML
    private RadioButton fieldOptionMotor;

    private Helpers helpers = new Helpers();
    private ToggleGroup toggleGroup = new ToggleGroup();
    private ObservableList<String> categories = FXCollections.observableArrayList("Pegawai", "Mahasiswa", "Umum");
    private String category, type;
    private int biaya = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCategoryDropdown();
        setupTypeOptions();
        fieldBiaya.setDisable(true);
        btnSubmit.setOnAction(actionEvent -> {
            validation();
            try {
                if (getParkingCount() >= helpers.getParkingQuota()) {
                    helpers.showAlert(Alert.AlertType.ERROR, "Error!", "Kouta Parkir sudah penuh!");
                } else {
                    addParkingIn();
                    clearField();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupCategoryDropdown() {
        fieldKategori.setItems(categories);
        fieldKategori.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                category = observableValue.getValue();
            }
        });
    }

    private void setupTypeOptions() {
        fieldOptionMotor.setToggleGroup(toggleGroup);
        fieldOptionMobil.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton selectedValue = (RadioButton)t1.getToggleGroup().getSelectedToggle();
                type = selectedValue.getText();
                setupBiayaParkir();
            }
        });
    }

    private void setupBiayaParkir() {
        if (category.equals("Umum")) {
            if (type.equals("Motor")) {
                biaya = 3000;
            } else {
                biaya = 5000;
            }
            fieldBiaya.setText("Rp " + biaya);
        } else {
            fieldBiaya.setText("Rp " + 0);
        }
    }

    private void clearField() {
        fieldNomorPolisiKendaraan.clear();
        fieldBiaya.clear();
    }

    private void validation() {
        if (fieldNomorPolisiKendaraan.getText().isEmpty() || category.isEmpty() || type.isEmpty()) {
            helpers.showAlert(Alert.AlertType.ERROR, "Error!", "Tidak boleh ada data yang kosong!");
        }
    }

    private void addParkingIn() throws SQLException {
        String query = "INSERT INTO parking (nopol, category, type, biaya) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setString(1, fieldNomorPolisiKendaraan.getText().toUpperCase(Locale.ROOT));
        statement.setString(2, category);
        statement.setString(3, type);
        statement.setInt(4, biaya);
        if (statement.executeUpdate() == 1) {
            helpers.showAlert(Alert.AlertType.INFORMATION, "Berhasil!", "Data Parkir Berhasil di Tambahkan");
        } else {
            helpers.showAlert(Alert.AlertType.ERROR, "Gagal!", "Data Parkir Gagal di Tambahkan");
        }
        statement.close();
    }

    private int getParkingCount() throws SQLException {
        String query = "SELECT COUNT(id) FROM parking WHERE status = (?) AND waktu_masuk LIKE " + "'%" + helpers.getCurrentDate() + "%'";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setString(1, "IN");
        ResultSet result = statement.executeQuery();
        int count = 0;
        while (result.next()) {
            count = result.getInt(1);
        }
        return count;
    }
}
