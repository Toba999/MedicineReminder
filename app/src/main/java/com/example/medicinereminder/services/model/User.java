package com.example.medicinereminder.services.model;

import java.util.List;


public class User {
    String fullName;
    String email;
    String password;
    List<String> patients;
    List<String> healthTrackers;

    public User() {
    }

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public User(String fullName, String email, String password, List<String> patient, List<String> healthTracker) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.patients = patient;
        this.healthTrackers = healthTracker;
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

    public void setPatient(List<String> patients) {
        this.patients = patients;
    }
    public void addPatient(String patient) {
        patients.add(patient);
    }
    public void addHealthTracker(String healthTracker) {
        healthTrackers.add(healthTracker);
    }

    public List<String> getPatients() {
        return patients;
    }

    public List<String> getHealthTrackers() {
        return healthTrackers;
    }

    public void setHealthTracker(List<String> healthTrackers) {
        this.healthTrackers = healthTrackers;
    }
}
