package com.example.medicinereminder.medication_screen.presenter;

import com.example.medicinereminder.model.MedicationPOJO;

import java.util.List;

public interface MedicationFragmentPresenterInterface {
    public List<MedicationPOJO> getMedicines(int position);
    public void deleteMed(MedicationPOJO medication, String email);
    public void updateMed(MedicationPOJO medication, String email);
}
