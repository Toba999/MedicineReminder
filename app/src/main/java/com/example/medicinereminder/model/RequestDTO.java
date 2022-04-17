package com.example.medicinereminder.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class RequestDTO implements Parcelable {

    private String name;
    private  String email;
    private String myEmail;
    private int acceptance;
    private String requestID;


    public RequestDTO() {
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

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
