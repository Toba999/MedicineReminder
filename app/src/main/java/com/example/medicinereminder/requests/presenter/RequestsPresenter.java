package com.example.medicinereminder.requests.presenter;

import android.content.Context;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.requests.view.RequestsActivityInterface;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

public class RequestsPresenter implements RequestsPresenterInterface, NetworkDelegate {

    private Context context;
    private RequestsActivityInterface view;
    private Repository repository;

    public RequestsPresenter(Context context, RequestsActivityInterface view) {
        this.context = context;
        this.view = view;
        repository = Repository.getInstance(this,context);
        repository.setRemoteDelegate(this);

    }
    @Override
    public void loadRequests(String email) {
        repository.loadHelpRequest(email);
    }

    @Override
    public FirebaseUser currentUser() {
        return   repository.getCurrentUser();
    }

    @Override
    public void onAccept(TrackerDTO trackerDTO, PatientDTO patientDTO) {
        repository.onAccept(trackerDTO,patientDTO);
    }

    @Override
    public void getUserFromRealDB(String email) {
        repository.getUserName(email);
    }

    @Override
    public void onReject(String key, String email) {
        repository.onReject(key,email);
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
        view.setonSuccessReturn(userName);
    }

    @Override
    public void onSuccessRequest(List<RequestDTO> requestDTOS) {
        view.setonSuccessRequest(requestDTOS);
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
