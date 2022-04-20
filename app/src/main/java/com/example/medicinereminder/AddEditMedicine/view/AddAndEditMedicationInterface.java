package com.example.medicinereminder.AddEditMedicine.view;

import com.example.medicinereminder.model.MedicationPOJO;

public interface AddAndEditMedicationInterface {
    void updateMedication(MedicationPOJO medication);

    void addMedication(MedicationPOJO medication);

    void onSuccess();
    void onFailure();
}
