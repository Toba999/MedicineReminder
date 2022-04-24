package com.example.medicinereminder.requests.view;

import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;

import java.util.List;

public interface RequestsActivityInterface {
    void setonSuccessRequest(List<RequestDTO> requestPojos);
    void setonSuccessReturn(String userName);
    void getUserFromRealDB(String email);
    void confirem(TrackerDTO tracker, PatientDTO patient);
    void delete(String key,String email);

}
