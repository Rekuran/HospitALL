package com.hospitall.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminOverviewController {
    @FXML private Label patientCountLabel;
    @FXML private Label doctorCountLabel;
    @FXML private Label appointmentCountLabel;

    public void initialize() {
        loadStatistics();
    }

    private void loadStatistics() {
        // TODO: Integrate with actual REST API. 
        // Currently, no aggregate endpoints for statistics exist on the backend.
        patientCountLabel.setText("N/A");
        doctorCountLabel.setText("N/A");
        appointmentCountLabel.setText("N/A");
    }
}
