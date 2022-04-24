package com.example.medicinereminder.patient_screen.presenter;

import android.content.Context;
import android.util.Log;

import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.patient_screen.view.PatientActivityInterface;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class PatientPresenter implements PatientPresenterInterface, NetworkDelegate {
    Context context;
    Repository repository;
    PatientActivityInterface patientActivity;

    public PatientPresenter(Context context, PatientActivityInterface patientActivity){
        this.context = context;
        this.patientActivity = patientActivity;
        repository = Repository.getInstance(this, context);
        repository.setRemoteDelegate(this);

    }

    public FirebaseUser getCurrentUser() {
        return repository.getCurrentUser();
    }

    @Override
    public void getPatients() {
        String myEmail = getCurrentUser().getEmail().toString();
        repository.loadPatients(myEmail);
    }

    @Override
    public void onSuccessPatient(List<PatientDTO> patient) {
        patientActivity.showPatient(patient);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Task<AuthResult> task) {

    }

    @Override
    public void onFailure(String errorMessage) {
        Log.i("FFFF", errorMessage);
    }

    @Override
    public void onSuccessReturn(String userName) {

    }

    @Override
    public void onSuccessRequest(List<RequestDTO> requestDTOS) {

    }

    @Override
    public void onSuccessTracker(List<TrackerDTO> trackerDTOS) {

    }

    @Override
    public void onSuccess(boolean response) {

    }

    @Override
    public void isUserExist(boolean existance) {

    }

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {

    }

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {

    }
}
