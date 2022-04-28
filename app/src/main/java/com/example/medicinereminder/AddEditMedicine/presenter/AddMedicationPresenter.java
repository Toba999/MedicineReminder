package com.example.medicinereminder.AddEditMedicine.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.medicinereminder.AddEditMedicine.view.AddAndEditMedicationInterface;
import com.example.medicinereminder.AddEditMedicine.view.AddEditMedActivity;
import com.example.medicinereminder.DisplayMedicine.DisplayMedActivity;
import com.example.medicinereminder.broadcast.NetworkStateListener;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.NetworkValidation;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TimeUtility;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.example.medicinereminder.workManager.MyPeriodicManager;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AddMedicationPresenter implements AddMedicationPresenterInterface, NetworkDelegate {
    private Repository repository;
    private AddAndEditMedicationInterface view;
    private Context context;
    private MedicationPOJO medication;

    public AddMedicationPresenter(AddAndEditMedicationInterface view, Context context) {
        this.repository = Repository.getInstance(this,context);
        this.view = view;
        this.context = context;
        this.repository.setRemoteDelegate(this);
    }

    @Override
    public void updateToDatabase(MedicationPOJO myMedication,String email) {
        repository.updateMedications(myMedication);
        checkUpdateToFirebase(myMedication,email);
        Intent intent = new Intent((Context)view , DisplayMedActivity.class);
        intent.putExtra("med", (Serializable) myMedication);
        context.startActivity(intent);
    }

    @Override
    public void addToDatabase(MedicationPOJO myMedication,String email) {
       repository.insertMedication(myMedication);
       checkUpdateToFirebase(myMedication,email);
    }

    @Override
    public void setPresenterModel(MedicationPOJO myMedication) {
        this.medication=myMedication;
        view.handleEditScreen(myMedication);
    }

    private void checkUpdateToFirebase(MedicationPOJO myMedication,String email){
        Log.i("add presenter",email);
        if(!email.equals("null")){
            repository.updatePatientMedicationList(email,myMedication);
        }
    }

    @Override
    public void setWorkTimer() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicManager.class,
                3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance((Context) view).enqueueUniquePeriodicWork("Counter", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);

    }

    @Override
    public void cancelEditScreen() {
        Intent intent = new Intent((Context)view,DisplayMedActivity.class);
        intent.putExtra("med", (Serializable) medication);
        context.startActivity(intent);
    }

    @Override
    public void buildMedObject(MedicationPOJO medicationPOJO) {

        MedicationPOJO myMedication=new MedicationPOJO();
        if (AddEditMedActivity.isAdd){
            myMedication.setId(Calendar.getInstance().getTimeInMillis()+view.getEtName()+TimeUtility.getDateMillis(view.getEtEndDate()));
        }else{
            myMedication.setId(medicationPOJO.getId());
        }
        myMedication.setMedicationName(view.getEtName());
        if (!view.getEtMedStrength().isEmpty())
            myMedication.setStrength(Integer.parseInt(view.getEtMedStrength()));
        if (!view.getEtLeftNumber().isEmpty())
            myMedication.setLeftNumber(Integer.parseInt(view.getEtLeftNumber()));
        if (!view.getEtLeftNumberReminder().isEmpty())
            myMedication.setLeftNumberReminder(Integer.parseInt(view.getEtLeftNumberReminder()));
        if (!view.getEtStartDate().equals("Selected Start Date"))
            myMedication.setStartDate(TimeUtility.getDateMillis(view.getEtStartDate()));
        if (!view.getEtEndDate().equals("Selected End Date"))
            myMedication.setEndDate(TimeUtility.getDateMillis(view.getEtEndDate()));
        if (!view.getEtMedSize().isEmpty())
            myMedication.setMedicineSize(Integer.parseInt(view.getEtMedSize()));
        if (!view.getEtNumDose().isEmpty())
            myMedication.setDoseNum(view.getEtNumDose());

        myMedication.setMedicationReason(view.getEtReason());
        myMedication.setEmail(view.getMedEmail());
        myMedication.setActive(true);
        myMedication.setMedicationType(view.getSpinnerMedType());
        myMedication.setStrengthType(view.getSpinnerStrengthType());
        myMedication.setTakeTimePerDay(view.getSpinnerTimePerDay());
        myMedication.setInstruction(view.getSpinnerInstruction());
        myMedication.setTakeTimePerWeek(view.getSpinnerTimePerWeek());

        myMedication.setDateTimeAbsTaken(view.getDateTimeAbsISTaken());
        myMedication.setDateTimeSimpleTaken(view.getDateSimpleISTaken());
        myMedication.setTimeSimpleTaken(view.getTimeSimpleIsTaken());
        myMedication.setDateTimeAbs(view.setDateTimeAbsolute());

        view.onClick(myMedication);


    }

    @Override
    public void onSuccess() {
        view.onSuccess();
        Log.i("add presenter","here****************");
    }

    @Override
    public void onFailure(String errorMessage) {
        view.onFailure();
    }


}
