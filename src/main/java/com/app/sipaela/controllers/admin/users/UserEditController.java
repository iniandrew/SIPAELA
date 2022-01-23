package com.app.sipaela.controllers.admin.users;

import com.app.sipaela.helpers.Connection;
import com.app.sipaela.helpers.Helpers;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Chrisdion Andrew on 17/01/22 13.36
 */
public class UserEditController implements Initializable {
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

    private int userId;
    private String status;
    private Helpers helpers = new Helpers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // setup data
        Platform.runLater(() -> {
            try {
                loadDataUser();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        setupActionButton();
        setupToggleGroup();
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
                validation();
                updateExistingUser();
                ((Stage) btnSubmit.getScene().getWindow()).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnCancel.setOnAction(actionEvent -> {
            ((Stage) btnCancel.getScene().getWindow()).close();
        });
    }

    private void validation() {
        if (fieldName.getText().isEmpty() || fieldUsername.getText().isEmpty() || fieldPassword.getText().isEmpty()) {
            helpers.showAlert(Alert.AlertType.ERROR, "Error!", "Tidak boleh ada data yang kosong!");
        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private void loadDataUser() throws SQLException {
        String query = "SELECT * FROM users WHERE id = (?)";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet result =  statement.executeQuery();

        while (result.next()) {
            fieldName.setText(result.getString(2));
            fieldUsername.setText(result.getString(3));
            fieldPassword.setText(result.getString(4));

            if (result.getBoolean(6)) {
                fieldOptionActive.setSelected(true);
            } else {
                fieldOptionInActive.setSelected(true);
            }

        }
        statement.close();
    }

    private void updateExistingUser() throws SQLException {
        String query = "UPDATE users SET nama=(?), username=(?), password=(?), status = (?) WHERE id = (?)";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setString(1, fieldName.getText());
        statement.setString(2, fieldUsername.getText());
        statement.setString(3, fieldPassword.getText());
        statement.setBoolean(4, status.equals("Aktif"));
        statement.setInt(5, userId);

        if (statement.executeUpdate() == 1) {
            helpers.showAlert(Alert.AlertType.INFORMATION, "Berhasil!", "Pengguna Berhasil di perbarui");
        } else {
            helpers.showAlert(Alert.AlertType.ERROR, "Gagal!", "Pengguna Baru Gagal di Tambahkan");
        }
        statement.close();
    }
}
