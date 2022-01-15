package com.app.sipaela.controllers.admin;

import com.app.sipaela.helpers.Connection;
import com.app.sipaela.helpers.Helpers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
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
    private ComboBox<String> fieldPosition;

    @FXML
    private TextField fieldUsername;

    private ObservableList<String> data = FXCollections.observableArrayList("Administrator", "Pegawai");

    Helpers helpers = new Helpers();
    String status, jabatan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldPosition.setItems(data);

        ToggleGroup toggleGroup = new ToggleGroup();
        fieldOptionActive.setToggleGroup(toggleGroup);
        fieldOptionInActive.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                status = String.valueOf(observableValue.getValue());
            }
        });

        fieldPosition.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                jabatan = (observableValue.getValue().equals("Administrator") ? "admin" : "pegawai");
            }
        });

        btnSubmit.setOnAction(actionEvent -> {
            try {
                addUser();
                helpers.showAlert(Alert.AlertType.INFORMATION, "Berhasil!", "Pengguna Baru Berhasil di Tambahkan");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnCancel.setOnAction(actionEvent -> {
            ((Stage) btnCancel.getScene().getWindow()).close();
        });
    }

    private void addUser() throws SQLException {
        String query = "INSERT INTO users (nama, username, password, jabatan, status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setString(1, fieldName.getText());
        statement.setString(2, fieldUsername.getText());
        statement.setString(3, fieldPassword.getText());
        statement.setString(4, jabatan);
        statement.setBoolean(5, Objects.equals(status, "Aktif"));

        if (statement.executeUpdate() == 1) {
            Helpers helpers = new Helpers();
            helpers.showAlert(Alert.AlertType.INFORMATION, "Berhasil!", "Pengguna Baru Berhasil di Tambahkan");
        }
        statement.close();
    }


}
