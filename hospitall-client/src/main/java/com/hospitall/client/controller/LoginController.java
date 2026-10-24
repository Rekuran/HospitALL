package com.hospitall.client.controller;

import java.io.IOException;

import com.hospitall.client.HospitalClientApplication;
import com.hospitall.client.service.AuthService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final AuthService authService = HospitalClientApplication.getAuthService();

    @FXML
    private void handleRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospitall/client/fxml/register.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("HospitALL - Register");
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            messageLabel.setText("Please enter both username and password.");
            return;
        }

        if (authService.login(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospitall/client/fxml/dashboard.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(loader.load(), 800, 600));
                stage.setTitle("HospitALL - Dashboard");
            } catch (IOException e) {
                messageLabel.setText("Error loading dashboard.");
            }
        } else {
            messageLabel.setText("Invalid username or password");
        }
    }
}
