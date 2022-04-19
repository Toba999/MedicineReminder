package com.example.medicinereminder.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class RequestDTO implements Serializable {

    private String name;
    private  String email;
    private String myEmail;
    private int acceptance;
    private String requestID;


    public RequestDTO() {
    }

    public RequestDTO(String name, String email, String myEmail, int acceptance) {
        this.name = name;
        this.email = email;
        this.myEmail = myEmail;
        this.acceptance = acceptance;
    }

    public RequestDTO(String name, String email, String myEmail, int acceptance, String id) {
        this.name = name;
        this.email = email;
        this.myEmail = myEmail;
        this.acceptance = acceptance;
        this.requestID = id;
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

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public int getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(int acceptance) {
        this.acceptance = acceptance;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }


}
