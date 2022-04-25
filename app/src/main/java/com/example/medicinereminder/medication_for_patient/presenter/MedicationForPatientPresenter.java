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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Date date=new Date(Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        String dateText = df2.format(date);

        filterMedicinesOfCurrentDate(medicationPOJOList, dateText);
        view.showMedications(medicationPOJOList);
    }

    public void updateMedStatus(String email, MedicationPOJO med){
        repository.updatePatientMedicationList(email, med);
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

    private List<MedicationPOJO> filterMedicinesOfCurrentDate(List<MedicationPOJO> allData,String currentData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<MedicationPOJO> todayMed = new ArrayList<>();

        for(MedicationPOJO med : allData)
        {
            for(Map.Entry<String,Boolean> opp : med.getDateTimeSimpleTaken().entrySet())
            {

                LocalDate date = LocalDate.parse(opp.getKey(),formatter);
                LocalDate currentLocal = LocalDate.parse(currentData,formatter);
                if(date.isEqual(currentLocal))
                {
                    todayMed.add(med);
                }
            }
        }
        return todayMed;
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
