package com.example.medicinereminder.services.network;

import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.model.MedicationPOJO;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;

public interface NetworkDelegate {

    void onSuccess();
    void onFailure(String errorMessage, Task<AuthResult> task);
    void onFailure(String errorMessage);

    // for response
    void onSuccessReturn(String userName);
    void onSuccessRequest(List<RequestDTO> requestDTOS);
    void onSuccessTracker(List<TrackerDTO> trackerDTOS);
    void onSuccess(boolean response);
    void onSuccessPatient(List<PatientDTO> patientDTOS);
    void isUserExist(boolean existance);
    void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications);

    void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList); //Medication

}
