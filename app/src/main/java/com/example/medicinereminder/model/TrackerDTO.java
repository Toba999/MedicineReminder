package com.example.medicinereminder.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class TrackerDTO implements Parcelable {
    private String patientEmail;
    private String name;
    private String email;
    private String requestID;

    public TrackerDTO() {
    }

    public TrackerDTO(String patientEmail, String name, String email, String requestID) {
        this.patientEmail = patientEmail;
        this.name = name;
        this.email = email;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
