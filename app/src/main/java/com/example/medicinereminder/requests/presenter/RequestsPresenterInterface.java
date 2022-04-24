package com.example.medicinereminder.requests.presenter;

import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.google.firebase.auth.FirebaseUser;

public interface RequestsPresenterInterface {
    void loadRequests(String email);
    FirebaseUser currentUser();
    void onAccept(TrackerDTO trackerDTO, PatientDTO patientDTO);
    void getUserFromRealDB(String email);
    void onReject(String key, String email);

}
