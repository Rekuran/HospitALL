package com.hospitall.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class HospitalClientApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/hospitall/client/fxml/login.fxml")));
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/hospitall/client/css/style.css")).toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("HospitALL - Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
