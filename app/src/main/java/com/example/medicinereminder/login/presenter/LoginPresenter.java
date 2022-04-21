package com.example.medicinereminder.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.medicinereminder.login.view.LoginActivityInterface;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import java.util.List;

public class LoginPresenter implements NetworkDelegate ,LoginPresenterInterface{
    private Context context;
    private LoginActivityInterface view;
    private Repository repository;

    public LoginPresenter(Context context, LoginActivityInterface view) {
        this.context = context;
        this.view = view;
        repository = Repository.getInstance(this,context);

    }

    @Override
    public void signInWithEmailAndPass(Activity activity, String email, String password) {
        repository.signInWithEmailAndPass(activity,email,password);
    }

    @Override
    public void signInUsingGoogle(String idToken) {
        repository.signInUsingGoogle(idToken);
    }

    @Override
    public void onSuccess() {
        view.setSuccessfulResponse();
    }

    @Override
    public void onFailure(Task<AuthResult> task) {

    }

    @Override
    public void onFailure(String errorMessage) {
        view.setFailureResponse(errorMessage);
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
        Log.i("presenter","login presenter user exist");
    }

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {

    }

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {

    }


}
