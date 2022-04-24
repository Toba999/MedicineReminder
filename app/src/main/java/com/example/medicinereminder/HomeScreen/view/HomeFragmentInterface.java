package com.example.medicinereminder.HomeScreen.view;

import androidx.lifecycle.LiveData;

import com.example.medicinereminder.model.MedicationPOJO;

import java.util.List;

public interface HomeFragmentInterface {
    String getCurrentData();
    void getData(LiveData<List<MedicationPOJO>> data);
}
