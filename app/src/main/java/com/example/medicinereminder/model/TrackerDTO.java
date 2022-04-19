package com.example.medicinereminder.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TrackerDTO implements Serializable {
    private String patientEmail;
    private String name;
    private String email;
    private int img;
    private String requestID;

    public TrackerDTO() {
    }

    public TrackerDTO(String patientEmail, String name, String email, int img, String requestID) {
        this.patientEmail = patientEmail;
        this.name = name;
        this.email = email;
        this.img = img;
        this.requestID = requestID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }


}
