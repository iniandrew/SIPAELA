package com.app.sipaela.controllers.parking;

import com.app.sipaela.helpers.Connection;
import com.app.sipaela.helpers.Helpers;
import com.app.sipaela.models.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by Chrisdion Andrew on 18/01/22 04.14
 */
public class ParkingShowController implements Initializable {
    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Parking, Integer> columnBiaya;

    @FXML
    private TableColumn<Parking, Integer> columnId;

    @FXML
    private TableColumn<Parking, String> columnKategori, columnNomorPolisi, columnTipeKendaraan, columnWaktuKeluar, columnWaktuMasuk, columnStatus;

    @FXML
    private TextField fieldSearch;

    @FXML
    private TableView<Parking> tableParking;

    @FXML
    private Text tvTotalKendaraan;

    @FXML
    private Text tvTotalPendapatan;

    private int id, biaya;
    private String nopol, category, type, waktuKeluar, waktuMasuk, status;
    private Helpers helpers = new Helpers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getTotalParking();
            getTotalPendapatan();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setupTable();
        btnSearch.setOnAction(actionEvent -> {
            setupTable();
        });
    }

    private void setupTable() {
        columnId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        columnNomorPolisi.setCellValueFactory(data -> data.getValue().nopolProperty());
        columnKategori.setCellValueFactory(data -> data.getValue().categoryProperty());
        columnTipeKendaraan.setCellValueFactory(data -> data.getValue().typeProperty());
        columnBiaya.setCellValueFactory(data -> data.getValue().biayaProperty().asObject());
        columnWaktuMasuk.setCellValueFactory(data -> data.getValue().waktu_masukProperty());
        columnWaktuKeluar.setCellValueFactory(data -> data.getValue().waktu_keluarProperty());
        columnStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        try {
            if (fieldSearch.getText().isEmpty()) {
                tableParking.setItems(loadData());
            } else {
                tableParking.setItems(loadDataFromUserInput());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getTotalParking() throws SQLException {
        String totalParkingQuery = "SELECT COUNT(id) FROM parking WHERE waktu_masuk LIKE " + "'%" + helpers.getCurrentDate() + "%'";
        PreparedStatement statement = Connection.doConnect().prepareStatement(totalParkingQuery);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            tvTotalKendaraan.setText((result.getString(1) == null ? "0" : result.getString(1)) + " Kendaraan");
        }
        statement.close();
    }

    private void getTotalPendapatan() throws SQLException {
        String totalPendapatanQuery = "SELECT SUM(biaya) FROM parking WHERE waktu_masuk LIKE " + "'%" + helpers.getCurrentDate() + "%'";
        PreparedStatement statement = Connection.doConnect().prepareStatement(totalPendapatanQuery);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            tvTotalPendapatan.setText("Rp " + (result.getString(1) == null ? "0" : result.getString(1)) );
        }
        statement.close();
    }

    private ObservableList<Parking> loadData() throws SQLException {
        String query = "SELECT * FROM parking WHERE waktu_masuk LIKE " + "'%" + helpers.getCurrentDate() + "%'";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        ObservableList<Parking> parkings = FXCollections.observableArrayList();

        while (result.next()) {
            id = result.getInt(1);
            nopol = result.getString(2);
            category = result.getString(3);
            type = result.getString(4);
            biaya = result.getInt(5);
            waktuMasuk = result.getString(6);
            waktuKeluar = result.getString(7) == null ? "-" : result.getString(7);
            status = result.getString(8).equals("IN") ? "Masuk" : "Keluar";
            parkings.add(new Parking(id, nopol, category, type, biaya, waktuMasuk, waktuKeluar, status));
        }
        statement.close();
        return parkings;
    }

    private ObservableList<Parking> loadDataFromUserInput() throws SQLException {
        String query = "SELECT * FROM parking WHERE nopol LIKE" + "'%" + fieldSearch.getText() + "%'" + "AND waktu_masuk LIKE " + "'%" + helpers.getCurrentDate() + "%'";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        ObservableList<Parking> parkings = FXCollections.observableArrayList();

        while (result.next()) {
            id = result.getInt(1);
            nopol = result.getString(2);
            category = result.getString(3);
            type = result.getString(4);
            biaya = result.getInt(5);
            waktuMasuk = result.getString(6);
            waktuKeluar = result.getString(7) == null ? "-" : result.getString(7);
            status = result.getString(8).equals("IN") ? "Masuk" : "Keluar";
            parkings.add(new Parking(id, nopol, category, type, biaya, waktuMasuk, waktuKeluar, status));
        }
        statement.close();
        return parkings;
    }
}
