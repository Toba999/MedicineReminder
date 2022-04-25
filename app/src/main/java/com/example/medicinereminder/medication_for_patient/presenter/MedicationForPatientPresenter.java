package com.example.medicinereminder.medication_for_patient.presenter;

import android.content.Context;

import com.example.medicinereminder.medication_for_patient.view.MedicationForPatient;
import com.example.medicinereminder.medication_for_patient.view.MedicationForPatientInterface;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;

public class MedicationForPatientPresenter implements MedicationForPatientPresenterInterface, NetworkDelegate {

    Context context;
    MedicationForPatientInterface view;
    Repository repository;

    public MedicationForPatientPresenter(Context context, MedicationForPatientInterface view){
        this.context = context;
        this.view = view;
        repository = Repository.getInstance(this, context);
        repository.setRemoteDelegate(this);
    }
    @Override
    public void getMedicines(String email) {
        repository.loadMedicationListOFPatient(email);
    }

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {
        view.showMedications(medicationPOJOList);
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

    }

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {

    }
}
