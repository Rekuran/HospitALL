package com.hospitall.client.model;

import javafx.beans.property.SimpleStringProperty;

public class Patient {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty age;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty contact;

    public Patient(String id, String name, String age, String gender, String contact) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
        this.gender = new SimpleStringProperty(gender);
        this.contact = new SimpleStringProperty(contact);
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getAge() { return age.get(); }
    public String getGender() { return gender.get(); }
    public String getContact() { return contact.get(); }

    public void setName(String name) { this.name.set(name); }
    public void setAge(String age) { this.age.set(age); }
    public void setGender(String gender) { this.gender.set(gender); }
    public void setContact(String contact) { this.contact.set(contact); }
}
