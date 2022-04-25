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

    public void updateMedStatus(MedicationPOJO med, String time, String date){
        med.getTimeSimpleTaken().put(time,true);
        Long absTime = simpleTimeToAbs(time);
        med.getDateTimeAbsTaken().put(absTime.toString(),true);
        med.getDateTimeSimpleTaken().put(date,true);
        repository.updateTakenMedicine(med);
    }

    private Long simpleTimeToAbs(String time) {
        String[] times = time.split(":");
        String part1 = times[0]; // 004
        String[] part2 = times[1].split(" ");
        Long hours = Long.parseLong(part1);
        Long mins = Long.parseLong(part2[0]);
        Long res = hours * 60 + mins;
        if (part2[1].equals("PM")) {
            res += (12 * 60);
        }
        return res;

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
