package com.example.medicinereminder.AddEditMedicine.view;

import com.example.medicinereminder.services.model.MedicationPOJO;

public interface AddAndEditMedicationInterface {
    void updateMedication(MedicationPOJO medication);

    void addMedication(MedicationPOJO medication);
}
