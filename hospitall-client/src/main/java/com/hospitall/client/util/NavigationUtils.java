package com.hospitall.client.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class NavigationUtils {

    public static void navigateToLogin(javafx.scene.Node node) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(NavigationUtils.class.getResource("/com/hospitall/client/fxml/login.fxml"));
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(new Scene(loader.load(), 400, 300));
                stage.setTitle("HospitALL - Login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void showAccessDenied(String message) {
        Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Access Denied");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
