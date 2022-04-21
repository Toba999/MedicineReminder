package com.example.medicinereminder.tracker_screen.presenter;

import android.content.Context;

import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.example.medicinereminder.tracker_screen.view.TrackerActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;

public class TrakerPresenter implements TrakerPresenterInterface, NetworkDelegate {

    private Context context;
    private TrackerActivity view;
    private Repository repository;
    public TrakerPresenter(Context context, TrackerActivity view) {
        this.context = context;
        this.view = view;
        repository = Repository.getInstance(this,context);

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Task<AuthResult> task) {

    }

    @Override
    public void onFailure(String errorMessage) {

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
    public void onSuccessPatient(List<PatientDTO> patientDTOS) {

    }

    @Override
    public void isUserExist(boolean existance) {
      //  view.isUserExiste(existance);
    }

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {

    }

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {

    }

    @Override
    public boolean UserExistence(String email) {
      return    repository.UserExistence(email);
    }

    @Override
    public void sendRequest(RequestDTO request) {
        repository.sendRequest(request);
    }
}
