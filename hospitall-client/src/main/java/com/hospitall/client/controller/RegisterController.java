package com.hospitall.client.controller;

import com.hospitall.client.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill all fields");
            return;
        }

        if (authService.register(username, password)) {
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Registration successful!");
        } else {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Registration failed!");
        }
    }

    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/hospitall/client/fxml/login.fxml"));
        stage.setScene(new Scene(root));
    }
}
