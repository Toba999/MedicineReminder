package com.example.medicinereminder.medication_screen.presenter;

import com.example.medicinereminder.services.model.MedicineStore;

import java.util.List;

public interface MedicationFragmentPresenterInterface {
    public List<String> getActiveInactive();
    public List<MedicineStore> getMedicines();
    public List<MedicineStore> getInactiveMedicines();
    public List<MedicineStore> getActiveMedicines();
    public void seperateMedicines();
}
