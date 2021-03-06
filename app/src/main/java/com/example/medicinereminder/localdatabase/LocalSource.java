package com.example.medicinereminder.localdatabase;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.medicinereminder.model.MedicationPOJO;

import java.util.List;

import io.reactivex.Single;

public class LocalSource implements LocalSourceInterface{

    private  DAO dao;
    private  LiveData<List<MedicationPOJO>> storedMedications;
    private static LocalSource localSource = null;

    private LocalSource(Context context) {

        MyDataBase db = MyDataBase.getInstance(context);
        dao = db.getDao();
        storedMedications = dao.getAllMedication();
    }

    public static LocalSource getConcreteLocalClassInstance(Context context) {
        if (localSource == null) {
            localSource = new LocalSource(context);
        }
        return localSource;
    }

    @Override
    public LiveData<List<MedicationPOJO>> getAllMedication() {
        return storedMedications;
    }

    @Override
    public Single<List<MedicationPOJO>> getAllMedicationSync() {
        return  dao.getAllMedicationSync();
    }

    @Override
    public void insertMedication(MedicationPOJO medication) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                dao.insertMedication(medication);
            }
        }.start();
    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                dao.deleteMedication(medication);
            }
        }.start();
    }

    @Override
    public LiveData<MedicationPOJO> getMedications(int id) {
        return dao.getMedications(id);
    }

    @Override
    public void updateMedications(MedicationPOJO medicationPOJO) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                dao.updateMedications(medicationPOJO);
            }
        }.start();
    }

    @Override
    public LiveData<List<MedicationPOJO>> getActiveMedications() {
        return dao.getActiveMedications();
    }

    @Override
    public LiveData<List<MedicationPOJO>> getInactiveMedications() {
        return dao.getInactiveMedications();
    }

    @Override
    public LiveData<List<MedicationPOJO>> getMedicationDay(long time) {
        return dao.getMedicationDay(time);
    }

    @Override
    public Single<List<MedicationPOJO>> getMedicationDayWorkManger(long time) {
        return dao.getMedicationDayWorkManger(time);
    }

    @Override
    public Single<List<MedicationPOJO>> getRefillReminderList(long time,int left) {
        return  dao.getRefillReminderList(time,left);
    }
    @Override
    public Single<List<MedicationPOJO>> getRefillReminderListLive(long time) {
        return  dao.getRefillReminderListLive(time);
    }

    @Override
    public void clearAllDataWhenUserOut() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                dao.clearAllDataWhenUserOut();
            }
        }.start();
    }

    @Override
    public void updateTakenMedicines(MedicationPOJO medicine) {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                dao.updateTakenMedince(medicine);
            }
        }.start();
    }



}
