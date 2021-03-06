package com.example.medicinereminder.HomeScreen.view;

import androidx.lifecycle.LiveData;

import com.example.medicinereminder.model.MedicationPOJO;

import java.util.Calendar;
import java.util.List;

public interface HomeFragmentViewInterface {
    void updateMedStatus(MedicationPOJO medicine, String interval, String timeStr);

    void showMedicineDialog(MedicationPOJO medicine,  String interval,String timeStr);
}
