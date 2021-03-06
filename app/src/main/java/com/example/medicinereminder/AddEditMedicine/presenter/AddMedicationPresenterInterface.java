package com.example.medicinereminder.AddEditMedicine.presenter;

import com.example.medicinereminder.model.MedicationPOJO;

public interface AddMedicationPresenterInterface {
    void updateToDatabase(MedicationPOJO medication,String email);
    void addToDatabase(MedicationPOJO medication,String email);
    void setPresenterModel(MedicationPOJO medication);
    void setWorkTimer();
    void cancelEditScreen();
    void buildMedObject(MedicationPOJO medicationPOJO);

}
