package com.example.medicinereminder.HomeScreen.presenter;

import androidx.lifecycle.LiveData;

import com.example.medicinereminder.model.MedicationPOJO;

import java.util.List;

public interface HomePresenterInterface {
     void getAllMedicine();
     List<MedicationPOJO> filterMedicinesOfCurrentDate(List<MedicationPOJO> allData,String currentData);
     void filterMedicinesOfSameDate(List<MedicationPOJO> medicinesOfToday);
     List<MedicationPOJO> getMorningMed();
     List<MedicationPOJO> getAfternoonMed();
     List<MedicationPOJO> getEveningMed();
}
