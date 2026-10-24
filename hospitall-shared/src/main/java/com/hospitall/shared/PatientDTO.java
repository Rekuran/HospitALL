package com.hospitall.shared;

import java.io.Serializable;

public class PatientDTO implements Serializable {
    private String id;
    private String name;
    private String age;
    private String gender;
    private String contact;
    private String createdAt;
    private String updatedAt;

    public PatientDTO() {
    }

    public PatientDTO(String id, String name, String age, String gender, String contact, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
