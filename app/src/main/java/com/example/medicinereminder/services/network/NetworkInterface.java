package com.example.medicinereminder.services.network;

import android.app.Activity;

import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.model.UserDTO;
import com.example.medicinereminder.model.MedicationPOJO;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public interface NetworkInterface {

    void setNetworkDelegate(NetworkDelegate networkDelegate);

    void isSignedIn();

    void registerWithEmailAndPass(Activity activity, String email, String password, String name);

    void signInWithEmailAndPass(Activity activity, String email, String password);

    void tryLoginGoogle(String email);

    void signInUsingGoogle( String idToken);

    FirebaseUser getCurrentUser();

    void addUserInDB(UserDTO user);

    void getUserFromRealDB(String email);

    void sendRequest(RequestDTO requestPojo);

    void loadHelpRequest(String email);

    void onAccept(TrackerDTO takerPOJO, PatientDTO patientPojo);

    void onReject(String key,String email);

    void loadPatients(String email);

    void loadTrackers(String email);
    void loadPatientMedicationList(String email);

    abstract void addMedicationListViewNetwork(List<MedicationPOJO> medicationPOJOS, String email);
    void UserExistance(String email);
    void deleteTracker(String takerEmail,String patientEmail);

    void deleteInPatientMedicationList(String email, String medicationID);
    void updateMedicationToRoomFromFirebase(String email);

    void updatePatientMedicationList(String email,MedicationPOJO medicationPOJO);
}
