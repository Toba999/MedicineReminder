package com.example.medicinereminder.tracker_screen.presenter;

import com.example.medicinereminder.model.RequestDTO;
import com.google.firebase.auth.FirebaseUser;

public interface TrakerPresenterInterface {
    void UserExistence(String email);
    void sendRequest(RequestDTO request);
    FirebaseUser currentUser();
}
