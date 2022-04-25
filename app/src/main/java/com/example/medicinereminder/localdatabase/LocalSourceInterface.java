package com.example.medicinereminder.localdatabase;



import androidx.lifecycle.LiveData;

import com.example.medicinereminder.model.MedicationPOJO;

import java.util.List;

import io.reactivex.Single;

public interface LocalSourceInterface {
    LiveData<List<MedicationPOJO>> getAllMedication();
    Single<List<MedicationPOJO>> getAllMedicationSync();
    void insertMedication(MedicationPOJO medication);

    void deleteMedication(MedicationPOJO medication);
    LiveData<MedicationPOJO> getMedications(int id);
    void updateMedications(MedicationPOJO medicationPOJO);
    LiveData<List<MedicationPOJO>> getActiveMedications(long time);
    LiveData<List<MedicationPOJO>> getInactiveMedications(long time);

    LiveData<List<MedicationPOJO>> getMedicationDay(long time);

    Single<List<MedicationPOJO>> getMedicationDayWorkManger(long time);
    Single<List<MedicationPOJO>> getRefillReminderList(long time,int left);


    void updateTakenMedicines(MedicationPOJO medicine);

    Single<List<MedicationPOJO>> getRefillReminderListLive(long time);

}
