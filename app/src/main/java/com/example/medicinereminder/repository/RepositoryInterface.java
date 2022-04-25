package com.example.medicinereminder.repository;

import android.app.Activity;

import androidx.lifecycle.LiveData;

import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.Single;

public interface RepositoryInterface {
    LiveData<List<MedicationPOJO>> getAllMedication();

    void insertMedication(MedicationPOJO medication);

    void deleteMedication(MedicationPOJO medication);

    LiveData<List<MedicationPOJO>> getMedicationDay(long time);

    LiveData<MedicationPOJO> getMedications(int id);

    void updateMedications(MedicationPOJO medicationPOJO);

    LiveData<List<MedicationPOJO>> getActiveMedications(long time);

    LiveData<List<MedicationPOJO>> getInactiveMedications(long time);

    // fireBase
    void isSignedIn();

    void registerWithEmailAndPass(Activity activity, String email, String password, String name);

    void signInWithEmailAndPass(Activity activity, String email, String password);

    void signInUsingGoogle(String idToken);

    void isSignedWithGoogle(String email);

    FirebaseUser getCurrentUser();

    void getUserName(String email);

    void sendRequest(RequestDTO requestPojo);

    void loadHelpRequest(String email);

    void onAccept(TrackerDTO takerPOJO, PatientDTO patientPojo);

    void onReject(String key, String email);

    void loadPatients(String email);

    void loadTakers(String email);
    void setRemoteDelegate(NetworkDelegate delegate);
    //add med to fireBase
    void addMedicationListFromRoomToFirebase(List<MedicationPOJO> medicationPOJOS, String email);


    Single<List<MedicationPOJO>> getMedicationDayWorkManger(long time);

    Single<List<MedicationPOJO>> getRefillReminderList(long time,int left);

    Single<List<MedicationPOJO>> getRefillReminderListLive(long time);


    //remove med from fireBase
    void deleteInPatientMedicationList(String email, String medicationID);

    void UserExistence(String email);
    // get medication Patient List

    void loadMedicationListOFPatient(String email);

    void deleteTaker(String takerEmail,String patientEmail);

    void updateToRoomFromFirebase(List<MedicationPOJO> medications);

    void notifyMedicationChangeFromFirebase(String email);

    void updatePatientMedicationList(String email,MedicationPOJO medicationPOJO);

    void updateTakenMedicine(MedicationPOJO medicine);

    void deletePatient(String patientEmail, String trackerEmail);

}
