package com.example.medicinereminder.signup.presenter;

import android.app.Activity;
import android.content.Context;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.example.medicinereminder.signup.view.SignUpActivityInterface;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import java.util.List;

public class SignUpPresenter implements SignupPresenterInterface, NetworkDelegate {

    private Context context;
    private SignUpActivityInterface view;
    private Repository repository;
     public SignUpPresenter(Context context, SignUpActivityInterface view) {
        this.context = context;
        this.view = view;
        repository = Repository.getInstance(this,context);

    }

    @Override
    public void registerWithEmailAndPass(Activity activity,String email, String password, String name) {
         repository.registerWithEmailAndPass(activity,email,password,name);
    }
    @Override
    public void onSuccess() {
        view.setSuccessfulResponse();
    }

 @Override
 public void onFailure(Task<AuthResult> task) {
        view.setFailureResponse(task);

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

    }

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {

    }

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {

    }


}
