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
    public LiveData<List<MedicationPOJO>> getActiveMedications(long time) {
        return dao.getActiveMedications(time);
    }

    @Override
    public LiveData<List<MedicationPOJO>> getInactiveMedications(long time) {
        return dao.getInactiveMedications(time);
    }

    @Override
    public LiveData<List<MedicationPOJO>> getMedicationDay(long time) {
        return dao.getMedicationDay(time);
    }

    //@Override
//    public Single<List<MedicationPOJO>> getMedicationDayWorkManger(long time) {
//        return dao.getMedicationDayWorkManger(time);
//    }
//
//    @Override
//    public Single<List<MedicationPOJO>> getRefillReminderList(long time) {
//        return  dao.getRefillReminderList(time);
//    }
}
