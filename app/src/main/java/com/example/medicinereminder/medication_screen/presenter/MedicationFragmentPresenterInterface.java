package com.example.medicinereminder.medication_screen.presenter;

import com.example.medicinereminder.services.model.MedicationPOJO;

import java.util.List;

public interface MedicationFragmentPresenterInterface {
    public List<String> getActiveInactive();
    public List<MedicationPOJO> getMedicines();
    public List<MedicationPOJO> getInactiveMedicines();
    public List<MedicationPOJO> getActiveMedicines();
    public void seperateMedicines();
}
