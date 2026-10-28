package com.hospitall.client.controller;

import com.hospitall.client.HospitalClientApplication;
import com.hospitall.client.service.AuthService;
import com.hospitall.client.util.NavigationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class DashboardController {

    @FXML private BorderPane mainView;
    private final AuthService authService = HospitalClientApplication.getAuthService();

    private void loadView(String viewName) {
        try {
            Pane view = FXMLLoader.load(getClass().getResource("/com/hospitall/client/fxml/" + viewName + ".fxml"));
            mainView.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleLogout() {
        authService.logout();
        NavigationUtils.navigateToLogin(mainView);
    }

    @FXML private void initialize() {
        showAdminOverview();
    }

    @FXML private void showPatients() { loadView("patients_view"); }
    @FXML private void showBilling() { loadView("billing_view"); }
    @FXML private void showAdminOverview() { loadView("admin_overview"); }
}
