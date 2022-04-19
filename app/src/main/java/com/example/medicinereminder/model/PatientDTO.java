package com.example.medicinereminder.model;

public class PatientDTO {

    private String email;
    private String patientEmail;
    private String name;
    private int img;
    private int ID;

    public int getProfileImg() {
        return img;
    }

    public void setProfileImg(int profileImg) {
        this.img = profileImg;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public PatientDTO(String email, String patientEmail, String name, int img) {
        this.email = email;
        this.patientEmail = patientEmail;
        this.name = name;
        this.img = img;
    }

    public PatientDTO() {
    }

    public PatientDTO(String email, String patientEmail, String password, String name) {
        this.email = email;
        this.patientEmail = patientEmail;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
