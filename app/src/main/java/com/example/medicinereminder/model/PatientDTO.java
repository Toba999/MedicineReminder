package com.example.medicinereminder.model;

import java.io.Serializable;

public class PatientDTO implements Serializable {

    private String email;
    private String name;
    private String trakerEmail;
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

    public PatientDTO( String trakerEmail,String email, String name, int img) {
        this.email = email;
        this.trakerEmail = trakerEmail;
        this.name = name;
        this.img = img;
    }

    public PatientDTO() {
    }

    public PatientDTO(String trakerEmail,String email,  String password, String name) {
        this.email = email;
        this.trakerEmail = trakerEmail;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String gettrakerEmail() {
        return trakerEmail;
    }

    public void settrakerEmail(String trakerEmail) {
        this.trakerEmail = trakerEmail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
