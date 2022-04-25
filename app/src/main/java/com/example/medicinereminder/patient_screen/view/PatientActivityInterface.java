package com.example.medicinereminder.patient_screen.view;

import com.example.medicinereminder.model.PatientDTO;

import java.util.List;

public interface PatientActivityInterface {
    public void showPatient(List<PatientDTO> patients);
    public void deletePatient(String patientEmail, String trackerEmail);
}
