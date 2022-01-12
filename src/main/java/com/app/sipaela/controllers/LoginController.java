package com.app.sipaela.controllers;

import com.app.sipaela.MainApplication;
import com.app.sipaela.helpers.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Chrisdion Andrew on 07/01/22 14.33
 */
public class LoginController implements Initializable {
    // Master Setting
    @FXML
    private Label lblAppname, lblAppDescription;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnLogin.setOnAction(actionEvent -> {
            try {
                login();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void onButtonLoginClicked(ActionEvent actionEvent) {

    }

    private void login() throws SQLException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("") || password.equals("")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Isian Username / Password harus diisi!");
        }

        String query = "SELECT * FROM users WHERE username = (?) AND password = (?)";
        PreparedStatement statement = Connection.doConnect().prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            if (result.getString(5).equals("admin")) {// get column jabatan
                System.out.println("Hello admin!");
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                Parent root = FXMLLoader.load(MainApplication.class.getResource("view/admin/dashboard-view.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        statement.close();
    }

    // untuk menampilkan alert
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
