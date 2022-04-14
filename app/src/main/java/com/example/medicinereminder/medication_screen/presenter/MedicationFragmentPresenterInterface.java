package com.example.medicinereminder.medication_screen.presenter;

import com.example.medicinereminder.services.model.Medicine;

import java.util.List;

public interface MedicationFragmentPresenterInterface {
    public List<String> getActiveInactive();
    public List<Medicine> getMedicines();
    public List<Medicine> getInactiveMedicines();
    public List<Medicine> getActiveMedicines();
    public void seperateMedicines();
}
