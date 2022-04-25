package com.example.medicinereminder.tracker_screen.view;

import com.example.medicinereminder.model.TrackerDTO;

import java.util.List;

public interface TrakerActivityInterface {
    void setUserExiste(boolean respons);
    void setTrakerExiste(boolean respons);
    void setonSuccessReturn(String userName);
    void setonSuccessTracker(List<TrackerDTO> trackerDTOS);
    void deleteTracker(String takerEmail, String patientEmail);
}
