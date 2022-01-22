package com.app.sipaela.controllers.admin.users;

import com.app.sipaela.helpers.Connection;
import com.app.sipaela.helpers.Helpers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Chrisdion Andrew on 16/01/22 00.06
 */
public class UserAddController implements Initializable {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @FXML
    private TextField fieldName;

    @FXML
    private RadioButton fieldOptionActive;

    @FXML
    private RadioButton fieldOptionInActive;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField fieldUsername;

    private Helpers helpers = new Helpers();
    private String status;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupActionButton();
        setupToggleGroup();
    }

    private void clearAllField() {
        fieldName.clear();
        fieldUsername.clear();
        fieldPassword.clear();
    }

    private void setupToggleGroup() {
        ToggleGroup toggleGroup = new ToggleGroup();
        fieldOptionActive.setToggleGroup(toggleGroup);
        fieldOptionInActive.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton selectedValue = (RadioButton)t1.getToggleGroup().getSelectedToggle();
                status = selectedValue.getText();
            }
        });
    }

    private void setupActionButton() {
        btnSubmit.setOnAction(actionEvent -> {
            try {
                if (fieldName.getText().isEmpty() || fieldUsername.getText().isEmpty() || fieldPassword.getText().isEmpty()) {
                    helpers.showAlert(Alert.AlertType.ERROR, "Error!", "Tidak boleh ada data yang kosong!");
                } else {
                    addUser();
                    clearAllField();
                    ((Stage) btnSubmit.getScene().getWindow()).close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnCancel.setOnAction(actionEvent -> {
            ((Stage) btnCancel.getScene().getWindow()).close();
        });
    }

    private void addUser() throws SQLException {
        String query = "INSERT INTO users (nama, username, password, jabatan,status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setString(1, fieldName.getText());
        statement.setString(2, fieldUsername.getText());
        statement.setString(3, fieldPassword.getText());
        statement.setString(4, "PEGAWAI");
        statement.setBoolean(5, status.equals("Aktif"));

        if (statement.executeUpdate() == 1) {
            helpers.showAlert(Alert.AlertType.INFORMATION, "Berhasil!", "Pengguna Baru Berhasil di Tambahkan");
        } else {
            helpers.showAlert(Alert.AlertType.ERROR, "Gagal!", "Pengguna Baru Gagal di Tambahkan");
        }
        statement.close();
    }
}
