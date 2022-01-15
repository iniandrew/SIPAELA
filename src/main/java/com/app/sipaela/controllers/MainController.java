package com.app.sipaela.controllers;

import com.app.sipaela.MainApplication;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.text.DateFormat;
import java.util.*;

/**
 * Created by Chrisdion Andrew on 13/01/22 12.18
 */
public class MainController implements Initializable {
    @FXML
    private Button btnDataParkir;

    @FXML
    private Button btnDataPegawai;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPengguna;

    @FXML
    private Button btnTipeKendaraan;

    @FXML
    private Label lblAppName;

    @FXML
    private Label lblFooter;

    @FXML
    public BorderPane mainLayout;

    @FXML
    private Text tvCurrentTimestamp;

    @FXML
    private Text tvUserName;

    // String userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentDate();

        try {
            loadView("view/admin/data-parkir-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnDataParkir.setOnAction(actionEvent -> {
            try {
                loadView("view/admin/data-parkir-view.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnPengguna.setOnAction(actionEvent -> {
            try {
                loadView("view/admin/users/show.fxml");
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

    private void getCurrentDate() {
        final DateFormat format = DateFormat.getInstance();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        final Calendar calendar = Calendar.getInstance();
                        tvCurrentTimestamp.setText(format.format(calendar.getTime()));
                    }
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

            }
        }, 0, 2000);
    }

//    protected void setupUserName(String name) {
//        this.userName = name;
//    }

    // Fungsi untuk memanggil tampilan
    public void loadView(String filePath) throws IOException {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(filePath)));
            mainLayout.setCenter(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}