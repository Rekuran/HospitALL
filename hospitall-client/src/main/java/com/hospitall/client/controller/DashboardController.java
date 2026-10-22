package com.hospitall.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class DashboardController {

    @FXML private BorderPane mainView;

    private void loadView(String viewName) {
        try {
            // Note: For simplicity in this demo stage, we assume a corresponding FXML exists.
            // If you want to keep it simple, you could just update the center of the mainView with a Label.
            // Here we'll just switch the content.
            Pane view = FXMLLoader.load(getClass().getResource("/com/hospitall/client/fxml/" + viewName + ".fxml"));
            mainView.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void showDashboard() { loadView("dashboard_content"); }
    @FXML private void showPatients() { loadView("patients_view"); }
    @FXML private void showDoctors() { loadView("doctors_view"); }
    @FXML private void showAppointments() { loadView("appointments_view"); }
    @FXML private void showBilling() { loadView("billing_view"); }
}
