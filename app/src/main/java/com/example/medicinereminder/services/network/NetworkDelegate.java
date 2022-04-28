package com.example.medicinereminder.services.network;

import android.util.Log;

import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.model.MedicationPOJO;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;

public interface NetworkDelegate {

    default void onSuccess(){
        Log.i("onSuccess","onSuccess");
    }
    default void onFailure( Task<AuthResult> task){

    }
    default void onFailure(String errorMessage){

    }
    // for response
    default void onSuccessReturn(String userName){

    }
    default void onSuccessRequest(List<RequestDTO> requestDTOS){

    }
    default void onSuccessTracker(List<TrackerDTO> trackerDTOS){

    }
    default void onSuccess(boolean response){

    }
    default void onSuccessPatient(List<PatientDTO> patientDTOS){

    }
    default void isUserExist(boolean existance){

    }
    default void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications){

    }

    default void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {

    }

}
