package com.example.medicinereminder.workManager.medReminderManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.medicinereminder.services.network.NetworkDelegate;
import com.example.medicinereminder.workManager.MyPeriodicManager;
import com.google.gson.Gson;

import com.example.medicinereminder.R;
import com.example.medicinereminder.databinding.MedicineReminderDialogBinding;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.NetworkValidation;
import com.example.medicinereminder.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MedWindowManager {
    private Context context;
    private MedicationPOJO myMedicine;
    MedicineReminderDialogBinding binding;
    private WindowManager windowManager;
    private View customNotificationDialogView;
    NetworkDelegate networkDelegate;
    String description;
    Repository repository;
    boolean isTaken;
    String key;
    String email;
    public MedWindowManager(Context context, MedicationPOJO medication, String key, boolean isTaken) {
        this.context = context;
        this.myMedicine = medication;
        this.key = key;
        this.isTaken = isTaken;
        repository = Repository.getInstance(networkDelegate,context);

    }

    public void setMyWindowManger() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customNotificationDialogView = inflater.inflate(R.layout.medicine_reminder_dialog, null);
        binding = MedicineReminderDialogBinding.bind(customNotificationDialogView);
        bindView();
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                android.R.attr.showWhenLocked | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
                PixelFormat.TRANSLUCENT);
        windowManager.addView(customNotificationDialogView, params);
    }

    @SuppressLint("SetTextI18n")
    private void bindView() {
        binding.dialMedName.setText(myMedicine.getMedicationName());
        binding.txtTime.setText(key);
        binding.txtMedTimeNotification.setText("Schedule for " + key + ", today");
        description = "take " +myMedicine.getDoseNum() + " " + myMedicine.getMedicationType() + "(s), " +
                myMedicine.getStrength() + myMedicine.getStrengthType() + myMedicine.getInstruction();
        binding.txtMedDoseNotification.setText(description);
        handleButtons();
    }

    private void handleButtons() {
        binding.imgClose.setOnClickListener(v -> {
            stopMyService();
            close();
        });
        binding.imgAccept.setOnClickListener(v -> {
            Map<String,Boolean> map = myMedicine.getDateTimeAbsTaken();
            map.put(key,true);
            int n = myMedicine.getLeftNumber() - Integer.parseInt(myMedicine.getDoseNum());
            if (n < 0)
                n = 0;
            myMedicine.setLeftNumber(n);
            myMedicine.setDateTimeAbsTaken(map);
            updateMedication(myMedicine);
            email = NetworkValidation.checkShared(context);
            if (!email.equals("null")){
                repository.insertMedication(myMedicine);
            }
            setPeriodicWorkManger();
            stopMyService();
            close();
        });

        binding.imgSkip.setOnClickListener(v -> {
            context.stopService(new Intent(context, MedReminderService.class));
            stopMyService();
            close();
        });

        binding.imgSnooze.setOnClickListener(v -> {
            setOnTimeWorkManger(myMedicine, key, isTaken);
            Toast.makeText(context, "Snoozed for 1 hour", Toast.LENGTH_SHORT).show();
            stopMyService();
            close();
        });
    }


    private void updateMedication(MedicationPOJO medication) {
        repository.updateMedications(medication);
    }

    private void setOnTimeWorkManger(MedicationPOJO medicationPOJO, String index, boolean isTakenPill) {
        Data data = new Data.Builder()
                .putString("MED", serializeToJason(medicationPOJO))
                .putString("INDEX", index)
                .putBoolean("isTaken",isTakenPill)
                .build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        String tag = medicationPOJO.getId() + medicationPOJO.getMedicationName() + index;
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MedOneTimeWorkManger.class).
                setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.HOURS)
                .addTag(tag)
                .build();
        WorkManager.getInstance(context).enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest);
    }

    private void setPeriodicWorkManger(){
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicManager.class,
                3, TimeUnit.HOURS)
                .setInitialDelay(2,TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("Counter", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
    }

    private String serializeToJason(MedicationPOJO pojo) {
        Gson gson = new Gson();
        return gson.toJson(pojo);
    }

    private void close() {
        try {
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).removeView(customNotificationDialogView);
            customNotificationDialogView.invalidate();
            ((ViewGroup) customNotificationDialogView.getParent()).removeAllViews();
        } catch (Exception e) {
            Log.d("Error2", e.toString());
        }
    }

    private void stopMyService() {
        context.stopService(new Intent(context, MedReminderService.class));
    }
}


