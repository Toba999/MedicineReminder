package com.example.medicinereminder.medication_for_patient.presenter;

import com.example.medicinereminder.model.MedicationPOJO;

public interface MedicationForPatientPresenterInterface {
    public void getMedicines(String email);
    public void updateMedStatus(String email, MedicationPOJO med);
    public void updateMedObject(Boolean checked, MedicationPOJO medicine, int position);
}
