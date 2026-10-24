package com.hospitall.client.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;

public class Patient {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty age;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty createdAt;
    private final SimpleStringProperty updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Patient(String id, String name, String age, String gender, String contact, String createdAt, String updatedAt) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
        this.gender = new SimpleStringProperty(gender);
        this.contact = new SimpleStringProperty(contact);
        this.createdAt = new SimpleStringProperty(formatDate(createdAt));
        this.updatedAt = new SimpleStringProperty(formatDate(updatedAt));
    }

    private String formatDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return "";
        try {
            // Attempt to handle date string. Replacing space with T handles standard ISO format with space.
            return LocalDateTime.parse(dateStr.replace(" ", "T")).format(FORMATTER);
        } catch (Exception e) {
            return dateStr;
        }
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getAge() { return age.get(); }
    public String getGender() { return gender.get(); }
    public String getContact() { return contact.get(); }
    public String getCreatedAt() { return createdAt.get(); }
    public String getUpdatedAt() { return updatedAt.get(); }

    public void setName(String name) { this.name.set(name); }
    public void setAge(String age) { this.age.set(age); }
    public void setGender(String gender) { this.gender.set(gender); }
    public void setContact(String contact) { this.contact.set(contact); }
}
