package com.app.sipaela.controllers.admin;

import com.app.sipaela.MainApplication;
import com.app.sipaela.controllers.MainController;
import com.app.sipaela.helpers.Connection;
import com.app.sipaela.helpers.Helpers;
import com.app.sipaela.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Chrisdion Andrew on 15/01/22 20.36
 */
public class UserShowController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> columnIdD;

    @FXML
    private TableColumn<User, String> columnName, columnUsername, columnPassword, columnJabatan, columnStatus, columnCreatedAt;

    @FXML
    private TextField fieldSearch;

    @FXML
    private Text tvCountUsersActive;

    @FXML
    private Text tvCountUsersInactive;

    private int id;
    private String name, username, password, jabatan, status, createdAt;
    private Helpers helpers = new Helpers();
    private User user;
    private int selectedId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        setupActionButton();

        try {
            getUserStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // untuk mendapatkan id user yang sedang di click oleh pengguna
    public void onTableUsersClicked(MouseEvent mouseEvent) {
        user = tableUsers.getSelectionModel().getSelectedItem();
        selectedId = user.idProperty().getValue();
    }

    private void setupActionButton() {
        btnAdd.setOnAction(actionEvent -> {
            showModalView("view/admin/users/add.fxml", actionEvent, "Tambah Pengguna Baru");
            tableUsers.refresh();
            setupTable();
        });

        btnDelete.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Hapus Data");
            alert.setContentText("Apakah anda yakin menghapus data tersebut ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    removeExistingUser();
                    tableUsers.refresh();
                    tableUsers.getSelectionModel().clearSelection();
                    setupTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                helpers.showAlert(Alert.AlertType.INFORMATION, "Info", "Dibatalkan");
            }
        });
    }

    private void showModalView(String viewPath, ActionEvent actionEvent, String title) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(MainApplication.class.getResource(viewPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    private void setupTable() {
        columnIdD.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        columnName.setCellValueFactory(data -> data.getValue().nameProperty());
        columnUsername.setCellValueFactory(data -> data.getValue().usernameProperty());
        columnPassword.setCellValueFactory(data -> data.getValue().passwordProperty());
        columnJabatan.setCellValueFactory(data -> data.getValue().jabatanProperty());
        columnStatus.setCellValueFactory(data -> data.getValue().statusProperty());
        columnCreatedAt.setCellValueFactory(data -> data.getValue().created_atProperty());

        try {
            tableUsers.setItems(loadData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserStatus() throws SQLException {
        // Untuk mengetahui berapa pengguna yang masih berstatus aktif
        String userActiveQuery = "SELECT COUNT(id) FROM users WHERE status = 1";
        PreparedStatement statementUserActive = Connection.doConnect().prepareStatement(userActiveQuery);
        ResultSet resultUserActive = statementUserActive.executeQuery();
        while (resultUserActive.next()) {
            tvCountUsersActive.setText(resultUserActive.getString(1) + " Pengguna");
        }
        statementUserActive.close();

        // Untuk mengetahui berapa pengguna yang masih berstatus tidak aktif
        String userInActiveQuery = "SELECT COUNT(id) FROM users WHERE status = 0";
        PreparedStatement statementUserInActive = Connection.doConnect().prepareStatement(userInActiveQuery);
        ResultSet resultUserInActive = statementUserInActive.executeQuery();
        while (resultUserInActive.next()) {
            tvCountUsersInactive.setText(resultUserInActive.getString(1) + " Pengguna");
        }
        statementUserInActive.close();
    }

    private void removeExistingUser() throws SQLException {
        String query = "DELETE FROM users WHERE id = (?)";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setInt(1, selectedId);
        if (statement.executeUpdate() == 1) {
            helpers.showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data Berhasil di Hapus!");
        } else {
            helpers.showAlert(Alert.AlertType.INFORMATION, "Gagal", "Data gagal di Hapus!");
        }
    }

    public ObservableList<User> loadData() throws Exception {
        String query = "SELECT * FROM users";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        ObservableList<User> users = FXCollections.observableArrayList();

        while (result.next()) {
            id = result.getInt(1);
            name = result.getString(2);
            username = result.getString(3);
            password = result.getString(4);
            jabatan = result.getString(5);
            status = result.getBoolean(6) ? "Aktif" : "Tidak Aktif";
            createdAt = result.getString(7);
            users.add(new User(id, name, username, password, jabatan, status, createdAt));
        }
        statement.close();
        return users;
    }
}
