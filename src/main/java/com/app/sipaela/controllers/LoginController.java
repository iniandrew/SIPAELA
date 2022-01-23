package com.app.sipaela.controllers;

import com.app.sipaela.MainApplication;
import com.app.sipaela.controllers.admin.AdminController;
import com.app.sipaela.controllers.employee.EmployeeController;
import com.app.sipaela.helpers.Connection;
import com.app.sipaela.helpers.Helpers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    private Helpers helpers = new Helpers();

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

    private void login() throws SQLException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // validasi form
        if (username.isEmpty() || password.isEmpty()) {
            helpers.showAlert(Alert.AlertType.ERROR, "Error", "Isian Username / Password harus diisi!");
        } else {
            String query = "SELECT * FROM users WHERE username = (?) AND password = (?)";
            PreparedStatement statement = Connection.doConnect().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String name = result.getString(2);
                String jabatan = result.getString(5);
                boolean isActive = result.getBoolean(6);

                if (isActive) {
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    FXMLLoader loader;
                    if (jabatan.equals("ADMIN")) {
                        loader = new FXMLLoader(MainApplication.class.getResource("view/admin/main-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        AdminController adminController = loader.getController();
                        adminController.setupUserName(name);
                        stage.setTitle("SIP AE LA - Admin");
                        stage.setScene(scene);
                    } else {
                        loader = new FXMLLoader(MainApplication.class.getResource("view/employee/main-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        EmployeeController employeeController = loader.getController();
                        employeeController.setupUserName(name);
                        stage.setTitle("SIP AE LA - Pegawai");
                        stage.setScene(scene);
                    }
                    stage.show();
                } else {
                    helpers.showAlert(Alert.AlertType.ERROR, "Login Gagal", "Akun " + name + " tidak aktif!");
                }
            } else {
                helpers.showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username / Password Salah!");
            }
            statement.close();
        }
    }
}
