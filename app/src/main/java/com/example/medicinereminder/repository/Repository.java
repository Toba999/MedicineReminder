package com.example.medicinereminder.repository;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.medicinereminder.localdatabase.LocalSource;
import com.example.medicinereminder.localdatabase.LocalSourceInterface;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.services.network.FirebaseNetwork;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.example.medicinereminder.services.network.NetworkInterface;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.Single;

public class Repository implements RepositoryInterface{

    private Context context;
    LocalSourceInterface localSource;
    private static Repository repo = null;
    NetworkInterface myRemote;
    private NetworkDelegate myDelegation;


    private Repository(NetworkDelegate myDelegation, Context context) {
        this.context = context;
        this.myDelegation=myDelegation;
        myRemote = FirebaseNetwork.getInstance(myDelegation);
        localSource = LocalSource.getConcreteLocalClassInstance(context);
    }


    public static Repository getInstance(NetworkDelegate myDelegation, Context context) {
        if (repo == null) {
            repo = new Repository(myDelegation, context);
       }
        return repo;
    }


    @Override
    public LiveData<List<MedicationPOJO>> getAllMedication() {

        return localSource.getAllMedication();
    }

    @Override
    public void insertMedication(MedicationPOJO medication) {
        localSource.insertMedication(medication);
        Log.i("TAG", "insertMedication: ");
    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {
        localSource.deleteMedication(medication);
    }

    @Override
    public LiveData<MedicationPOJO> getMedications(int id) {
        return localSource.getMedications(id);
    }

    @Override
    public void updateMedications(MedicationPOJO medicationPOJO) {
        localSource.updateMedications(medicationPOJO);
    }

    @Override
    public LiveData<List<MedicationPOJO>> getActiveMedications(long time) {
        return localSource.getActiveMedications(time);
    }

    @Override
    public LiveData<List<MedicationPOJO>> getInactiveMedications(long time) {
        return localSource.getInactiveMedications(time);
    }

    @Override
    public LiveData<List<MedicationPOJO>> getMedicationDay(long time) {
        return localSource.getMedicationDay(time);
    }

    @Override
    public Single<List<MedicationPOJO>> getMedicationDayWorkManger(long time) {
        return localSource.getMedicationDayWorkManger(time);
    }

    @Override
    public Single<List<MedicationPOJO>> getRefillReminderList(long time,int left) {
        return localSource.getRefillReminderList(time,left);
    }

    @Override
    public Single<List<MedicationPOJO>> getRefillReminderListLive(long time) {
        return localSource.getRefillReminderListLive(time);
    }

    // for firebase
    @Override
    public void isSignedIn() {
        myRemote.isSignedIn();
    }


    @Override
    public void registerWithEmailAndPass(Activity activity, String email, String password, String name) {
        myRemote.registerWithEmailAndPass(activity, email, password, name);

    }

    @Override
    public void signInWithEmailAndPass(Activity activity, String email, String password) {
        myRemote.signInWithEmailAndPass(activity, email, password);
    }


    @Override
    public void isSignedWithGoogle(String email) {
        myRemote.tryLoginGoogle(email);
    }

    @Override
    public void signInUsingGoogle(String idToken) {
        myRemote.signInUsingGoogle(idToken);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return myRemote.getCurrentUser();
    }

    @Override
    public void getUserName(String email) {
        myRemote.getUserFromRealDB(email);

    }



    @Override
    public void UserExistence(String email) {
        myRemote.UserExistence(email);
    }

    @Override
    public void deleteTaker(String takerEmail, String patientEmail) {
        myRemote.deleteTracker(takerEmail, patientEmail);
    }

    @Override
    public void updateToRoomFromFirebase(List<MedicationPOJO> medications) {
        for (MedicationPOJO medication : medications) {
            localSource.updateMedications(medication);
            Log.i("ahmed", "updateToRoomFromFirebase: "+medication.getStrength());
        }
    }

    @Override
    public void notifyMedicationChangeFromFirebase(String email) {
        myRemote.updateMedicationToRoomFromFirebase(email);
    }


    @Override
    public void sendRequest(RequestDTO requestPojo) {
        myRemote.sendRequest(requestPojo);
    }

    @Override
    public void loadHelpRequest(String email) {
        myRemote.loadHelpRequest(email);
    }

    @Override
    public void onAccept(TrackerDTO takerPOJO, PatientDTO patientPojo) {
        myRemote.onAccept(takerPOJO, patientPojo);
    }

    @Override
    public void onReject(String key, String email) {
        myRemote.onReject(key, email);
    }

    @Override
    public void loadPatients(String email) {
        myRemote.loadPatients(email);
    }

    @Override
    public void loadTakers(String email) {

        myRemote.loadTrackers(email);
    }

    @Override
    public void setRemoteDelegate(NetworkDelegate delegate) {
        myRemote.setNetworkDelegate(delegate);
    }

    //add med to firebase
    @Override
    public void addMedicationListFromRoomToFirebase(List<MedicationPOJO> medicationPOJOS, String email) {
   // try one
        myRemote.addMedicationListFromRoomToFirebase(medicationPOJOS, email);
    }

    @Override
    public void deleteInPatientMedicationList(String email, String medicationID) {
        myRemote.deleteInPatientMedicationList(email, medicationID);
    }

    // get medication Patient List
    @Override
    public void loadMedicationListOFPatient(String email) {
        myRemote.loadPatientMedicationList(email);

    }

    @Override
    public void updatePatientMedicationList(String email, MedicationPOJO medicationPOJO) {
        myRemote.updatePatientMedication(email, medicationPOJO);
    }

    @Override
    public void updateTakenMedicine(MedicationPOJO medicine) {
        localSource.updateTakenMedicines(medicine);
    }


}
