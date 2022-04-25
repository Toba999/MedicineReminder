package com.example.medicinereminder.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.NetworkValidation;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.repository.RepositoryInterface;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class NetworkStateListener extends BroadcastReceiver implements NetworkDelegate {

    RepositoryInterface repo;
    public static boolean isConnected = false;
    Single<List<MedicationPOJO>> medicationSingleList;
    List<MedicationPOJO> medList = new ArrayList<>();
    @Override
    public void onReceive(final Context context, final Intent intent) {
        repo= Repository.getInstance(this, context);
        repo.setRemoteDelegate(this);
        boolean status = NetworkValidation.isOnline(context);
        Log.e("network receiver", "network");
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (!status) {
                Log.e("network receiver", "no network");
                isConnected =false;
            } else {
                isConnected = true;
                sync();
                Log.e("network receiver", "network back");
            }
        }
    }
    public void sync(){
        Log.e("network receiver", "sync: " );
        new Thread(new Runnable() {
            @Override
            public void run() {
                medicationSingleList = repo.getAllMedicationSync();
                medicationSingleList.subscribe(new SingleObserver<List<MedicationPOJO>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<MedicationPOJO> medicationPojos) {
                        medList = medicationPojos;
                        Log.e("Local Fetch", "onSuccess:" +medicationPojos.size());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Local Fetch", "onError:" +e.toString());
                    }
                });
                if(!medList.isEmpty()){
                    Log.e("Remote update", "onSuccess: "+medList.get(0).getEmail() );
                    repo.addMedicationListFromRoomToFirebase(medList,medList.get(0).getEmail());
                }
            }
        }).start();
    }

    @Override
    public void onSuccess() {
        Log.e("Remote update", "onSuccess" );
    }

    @Override
    public void onFailure(Task<AuthResult> task) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onSuccessReturn(String userName) {

    }

    @Override
    public void onSuccessRequest(List<RequestDTO> requestDTOS) {

    }

    @Override
    public void onSuccessTracker(List<TrackerDTO> trackerDTOS) {

    }

    @Override
    public void onSuccess(boolean response) {

    }

    @Override
    public void onSuccessPatient(List<PatientDTO> patientDTOS) {

    }

    @Override
    public void isUserExist(boolean existance) {

    }

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {

    }

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {

    }
}
