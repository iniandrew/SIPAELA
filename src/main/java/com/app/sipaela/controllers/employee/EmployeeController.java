package com.app.sipaela.controllers.employee;

import com.app.sipaela.MainApplication;
import com.app.sipaela.helpers.Helpers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Chrisdion Andrew on 18/01/22 00.14
 */
public class EmployeeController implements Initializable {
    @FXML
    private Button btnDataParkir;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnParkirKeluar;

    @FXML
    private Button btnParkirMasuk;

    @FXML
    private Label lblAppName;

    @FXML
    private Label lblFooter;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Text tvCurrentTimestamp;

    @FXML
    private Text tvUserName;

    private String name;
    private Helpers helpers = new Helpers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupActionButton();
        setupCurrentDatetime();

        Platform.runLater(() -> {
            tvUserName.setText("Halo, " + name);
        });

        try {
            loadView("view/parking/show.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupActionButton() {
        btnDataParkir.setOnAction(actionEvent -> {
            try {
                loadView("view/parking/show.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnParkirMasuk.setOnAction(actionEvent -> {
            try {
                loadView("view/parking/add-in.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnParkirKeluar.setOnAction(actionEvent -> {
            try {
                loadView("view/parking/add-out.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnLogout.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Logout");
            alert.setContentText("Apakah anda yakin untuk logout?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("view/login-view.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    // untuk mendapatkan tanggal dan waktu hari ini
    private void setupCurrentDatetime() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        tvCurrentTimestamp.setText(helpers.getCurrentDatetime());
                    }
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
        }, 0, 2000);
    }

    public void setupUserName(String name) {
        this.name = name;
    }

    // Fungsi untuk memanggil tampilan
    private void loadView(String filePath) throws IOException {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(filePath)));
            mainLayout.setCenter(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
