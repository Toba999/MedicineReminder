package com.example.medicinereminder.AddEditMedicine.view;

import com.example.medicinereminder.model.MedicationPOJO;

import java.util.List;
import java.util.Map;

public interface AddAndEditMedicationInterface {
    void onClick(MedicationPOJO medication);
    void updateMedication(MedicationPOJO medication);
    void handleEditScreen(MedicationPOJO medication);
    void addMedication(MedicationPOJO medication);
    void onSuccess();
    void onFailure();
    String getEtName();
    String getEtNumDose();
    String getMedEmail();
    String getEtStartDate();
    String getEtEndDate();
    String getEtLeftNumber();
    String getEtLeftNumberReminder();
    String getEtReason();
    String getEtMedSize();
    String getEtMedStrength();
    String getSpinnerMedType();
    String getSpinnerStrengthType();
    String getSpinnerTimePerDay();
    String getSpinnerTimePerWeek();
    String getSpinnerInstruction();
    Map<String, Boolean> getDateTimeAbsISTaken();
    Map<String, Boolean> getDateSimpleISTaken();
    Map<String, Boolean> getTimeSimpleIsTaken();
    List<String>setDateTimeAbsolute();






}
