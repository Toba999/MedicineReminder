package com.example.medicinereminder.tracker_screen.presenter;

import com.example.medicinereminder.model.RequestDTO;

public interface TrakerPresenterInterface {
    boolean UserExistence(String email);
    void sendRequest(RequestDTO request);
}
