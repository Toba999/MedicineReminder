package com.example.medicinereminder.AddEditMedicine.presenter;

import com.example.medicinereminder.services.model.MedicationPOJO;

public interface AddMedicationPresenterInterface {
    void updateToDatabase(MedicationPOJO medication);
    void addToDatabase(MedicationPOJO medication);
}
