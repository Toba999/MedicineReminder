package com.example.medicinereminder.medication_screen.presenter;

import com.example.medicinereminder.model.MedicationPOJO;

import java.util.List;

public interface MedicationFragmentPresenterInterface {
    public List<MedicationPOJO> getMedicines(int position);
}
