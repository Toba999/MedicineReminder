package com.example.medicinereminder.services.model;

import java.util.List;

enum UserType { PATIENT, HEALTHTRACKER}

public class User {
    String fullName;
    String email;
    String password;
    String type;
    List<User> PatientOrHealthTracker;

    public User() {
    }

    public User(String fullName, String email, String password, String type, List<User> patientOrHealthTracker) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.type = type;
        PatientOrHealthTracker = patientOrHealthTracker;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<User> getPatientOrHealthTracker() {
        return PatientOrHealthTracker;
    }

    public void setPatientOrHealthTracker(List<User> patientOrHealthTracker) {
        PatientOrHealthTracker = patientOrHealthTracker;
    }
}
