package com.hospitall.client.controller;

import java.util.List;

import com.hospitall.client.model.Patient;
import com.hospitall.client.service.PatientService;
import com.hospitall.shared.PatientDTO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientsController {
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> idCol, nameCol, ageCol, genderCol, contactCol;
    @FXML private TextField idField, nameField, ageField, genderField, contactField, searchField;
    @FXML private Label statusLabel;

    private final ObservableList<Patient> masterData = FXCollections.observableArrayList();
    private final FilteredList<Patient> filteredData = new FilteredList<>(masterData, p -> true);
    private final PatientService patientService = new PatientService();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        patientTable.setItems(filteredData);
        loadPatients();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());

        patientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                idField.setText(newSel.getId()); idField.setDisable(true);
                nameField.setText(newSel.getName());
                ageField.setText(newSel.getAge());
                genderField.setText(newSel.getGender());
                contactField.setText(newSel.getContact());
            }
        });
    }

    private void loadPatients() {
        try {
            List<PatientDTO> list = patientService.getAllPatients();
            masterData.setAll(list.stream().map(d -> new Patient(d.getId(), d.getName(), d.getAge(), d.getGender(), d.getContact())).toList());
            handleSearch(); // Re-apply search filter after reloading data
        } catch (Exception e) {
            statusLabel.setText("Load error: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch() {
        String filter = searchField.getText().toLowerCase().trim();
        filteredData.setPredicate(p -> {
            if (filter.isEmpty()) return true;
            
            boolean matchId = p.getId() != null && p.getId().trim().toLowerCase().equals(filter);
            boolean matchAge = p.getAge() != null && p.getAge().trim().toLowerCase().equals(filter);
            
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

            return matchId || matchName || matchAge || matchContact;
        });
    }

    @FXML
    private void handleAddPatient() {
        if (idField.getText().isEmpty() || nameField.getText().isEmpty()) {
            statusLabel.setText("ID and Name are required.");
            return;
        }
        PatientDTO dto = new PatientDTO(idField.getText(), nameField.getText(), ageField.getText(), genderField.getText(), contactField.getText());
        try {
            patientService.addPatient(dto);
            loadPatients();
            clearFields();
        } catch (Exception e) {
            statusLabel.setText("Add error: " + e.getMessage());
        }
    }

    @FXML
    private void handleEdit() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected == null) { statusLabel.setText("Select a patient."); return; }
        PatientDTO dto = new PatientDTO(selected.getId(), nameField.getText(), ageField.getText(), genderField.getText(), contactField.getText());
        try {
            patientService.updatePatient(dto);
            loadPatients();
            statusLabel.setText("Updated.");
        } catch (Exception e) {
            statusLabel.setText("Update error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected == null) { statusLabel.setText("Select a patient."); return; }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this patient?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                patientService.deletePatient(selected.getId());
                loadPatients();
                clearFields();
            } catch (Exception e) {
                statusLabel.setText("Delete error: " + e.getMessage());
            }
        }
    }

    private void clearFields() {
        idField.clear(); idField.setDisable(false); nameField.clear(); ageField.clear(); genderField.clear(); contactField.clear();
    }
}
