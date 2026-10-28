package com.hospitall.client.controller;

import java.util.List;

import com.hospitall.client.HospitalClientApplication;
import com.hospitall.client.model.Patient;
import com.hospitall.client.service.PatientService;
import com.hospitall.client.util.NavigationUtils;
import com.hospitall.shared.PatientDTO;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientsController {
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> idCol, nameCol, ageCol, genderCol, contactCol, createdAtCol, updatedAtCol;
    @FXML private TextField searchField;
    @FXML private Label statusLabel;

    private final ObservableList<Patient> masterData = FXCollections.observableArrayList();
    private final FilteredList<Patient> filteredData = new FilteredList<>(masterData, p -> true);
    private final PatientService patientService = new PatientService(HospitalClientApplication.getAuthService());

    private void handleException(Exception e, String context) {
        if ("UNAUTHORIZED".equals(e.getMessage())) {
            NavigationUtils.navigateToLogin(patientTable);
        } else if ("HTTP 403".equals(e.getMessage())) {
            Platform.runLater(() -> NavigationUtils.showAccessDenied("You do not have permission to perform this action."));
        } else {
            Platform.runLater(() -> statusLabel.setText(context + " error: " + e.getMessage()));
        }
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        updatedAtCol.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        patientTable.setItems(filteredData);
        loadPatients();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
    }

    private void loadPatients() {
        try {
            List<PatientDTO> list = patientService.getAllPatients();
            masterData.setAll(list.stream().map(d -> new Patient(d.getId(), d.getName(), d.getAge(), d.getGender(), d.getContact(), d.getCreatedAt(), d.getUpdatedAt())).toList());
            handleSearch(); // Re-apply search filter after reloading data
        } catch (Exception e) {
            handleException(e, "Load");
        }
    }

    @FXML
    private void handleSearch() {
        String filter = searchField.getText().toLowerCase().trim();
        filteredData.setPredicate(p -> {
            if (filter.isEmpty()) return true;
            
            boolean matchId = p.getId() != null && p.getId().trim().toLowerCase().equals(filter);
            boolean matchAge = p.getAge() != null && p.getAge().trim().toLowerCase().equals(filter);
            boolean matchGender = p.getGender() != null && p.getGender().toLowerCase().equals(filter);
            
            // For name and contact, use partial search only if filter is not purely numeric (or always partial)
            // But to avoid name/contact matching single digit IDs like '2', let's check if filter is numeric
            boolean isNumeric = filter.matches("\\d+");
            
            boolean matchName = false;
            boolean matchContact = false;
            
            if (!isNumeric) {
                matchName = p.getName() != null && p.getName().toLowerCase().contains(filter);
                matchContact = p.getContact() != null && p.getContact().toLowerCase().contains(filter);
            } else {
                // If searching by number (like ID or Age), require exact match on ID/Age,
                // and only check name/contact if they contain the exact word/number with word boundaries or exact match
                matchName = p.getName() != null && java.util.regex.Pattern.compile("\\b" + filter + "\\b").matcher(p.getName().toLowerCase()).find();
                matchContact = p.getContact() != null && p.getContact().toLowerCase().contains(filter) && p.getContact().trim().toLowerCase().equals(filter);
            }

            return matchId || matchName || matchAge || matchGender || matchContact;
        });
    }

    @FXML
    private void handleAddPatient() {
        // Admin Dashboard is read-only for Patients
        statusLabel.setText("Modification not allowed in Admin Dashboard.");
    }

    @FXML
    private void handleEdit() {
        // Admin Dashboard is read-only for Patients
        statusLabel.setText("Modification not allowed in Admin Dashboard.");
    }

    @FXML
    private void handleDelete() {
        // Admin Dashboard is read-only for Patients
        statusLabel.setText("Modification not allowed in Admin Dashboard.");
    }

    private void clearFields() {
   }
}
