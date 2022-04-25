package com.example.medicinereminder.patient_screen.presenter;

public interface PatientPresenterInterface {
    public void getPatients();
    public void deletePatient(String patientEmail, String trackerEmail);
}
