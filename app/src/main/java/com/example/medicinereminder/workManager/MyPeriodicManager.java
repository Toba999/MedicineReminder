package com.example.medicinereminder.workManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.TimeUtility;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.example.medicinereminder.workManager.medReminderManager.MedOneTimeWorkManger;
import com.example.medicinereminder.workManager.refillManager.RefillReminderWorkManager;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MyPeriodicManager extends Worker {
    long timeNow;
    long timeNowPlusThreeHours;
    Repository repository;
    Single<List<MedicationPOJO>> medicationSingleList;
    Single<List<MedicationPOJO>> medicationSingleListForRefillReminder;
    Calendar calendar;
    long alarmTimePeriod;
    // period before running
    long periodBeforeRunning;
    List<MedicationPOJO> medicationListForMedicationReminder;
    List<MedicationPOJO> medicationListForRefillReminder;
    Context context;
    NetworkDelegate networkDelegate;

    public MyPeriodicManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = Repository.getInstance(networkDelegate, context);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        calendar = Calendar.getInstance();
        medicationSingleList = repository.getMedicationDayWorkManger(calendar.getTimeInMillis());
        medicationSingleListForRefillReminder = repository.getRefillReminderListLive(calendar.getTimeInMillis());
        subscribeOnSingleForMedicationReminder();
        subscribeOnSingleForRefillReminder();
        getTimePeriod();
        getCurrentAlarms();
        return Result.success();
    }

    // for periodic
    private void getTimePeriod() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (hour == 0) {
            hour += 12;
        }
        timeNow = ((hour * 60 + minute) * 60 * 1000)+ TimeUtility.getDateNowMilli();
        timeNowPlusThreeHours = timeNow + (3 * 60 * 60 * 1000);
    }


    //setting for period before running alarm
    private void setDurationTimes(long timeNow, long alarmPeriod) {
        periodBeforeRunning = (alarmPeriod - timeNow) / 60000;
    }

    private void subscribeOnSingleForMedicationReminder() {
        medicationSingleList.subscribe(new SingleObserver<List<MedicationPOJO>>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onSuccess(List<MedicationPOJO> medicationPOJOS) {
                for(MedicationPOJO medicationPOJO:medicationPOJOS){
                    Log.i("medicine reminder", "onSuccess: "+medicationPOJO.getMedicationName()+medicationPOJO.getLeftNumberReminder());
                }
                medicationListForMedicationReminder = medicationPOJOS;
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void subscribeOnSingleForRefillReminder() {
        medicationSingleListForRefillReminder.subscribe(new SingleObserver<List<MedicationPOJO>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<MedicationPOJO> medicationPOJOS) {
                for(MedicationPOJO medicationPOJO:medicationPOJOS){
                    Log.i("refill reminder", "onSuccess: "+medicationPOJO.getMedicationName()+medicationPOJO.getLeftNumberReminder());
                }
                medicationListForRefillReminder = medicationPOJOS;
                loopOnRefileMedicationList();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void getCurrentAlarms() {
        if (medicationListForMedicationReminder != null) {
            for (int i = 0; i < medicationListForMedicationReminder.size(); i++) {
                if (medicationListForMedicationReminder.get(i).getDateTimeAbsTaken() != null) {
                    for (Map.Entry<String, Boolean> entry : medicationListForMedicationReminder.get(i).getDateTimeAbsTaken().entrySet()) {
                        Log.i("getCurrentAlarms For", "onSuccess: "+entry.getKey());
                        if (checkPeriod(entry.getKey())) {
                            Log.i("setOnTimeWorkManger IF", "onSuccess: "+alarmTimePeriod+" "+timeNow);
                            setDurationTimes(timeNow, alarmTimePeriod);
                            setOnTimeWorkManger(periodBeforeRunning, medicationListForMedicationReminder.get(i), entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
        }
    }

    private boolean checkPeriod(String time) {
        Log.i("checkPeriod", "onSuccess: "+time);
        BigInteger sub =  new BigInteger(time);
        long t=sub.longValue();
        Log.i("checkPeriod", "onSuccess: "+t+" "+timeNow+" "+timeNowPlusThreeHours);
        if (t >= timeNow && t <= timeNowPlusThreeHours) {
            alarmTimePeriod = t;
            return true;
        }
        return false;
    }


    private void setOnTimeWorkManger(long time, MedicationPOJO medicationPOJO, String index, Boolean isTaken) {
        // pass medication POJO
        Data data = new Data.Builder()
                .putString("MED", serializeToJason(medicationPOJO))
                .putString("INDEX", index)
                .putBoolean("isTaken", isTaken)
                .build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        String tag = medicationPOJO.getId()+medicationPOJO.getMedicationName()+index;
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MedOneTimeWorkManger.class).
                setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(time, TimeUnit.MINUTES)
                .addTag(tag)
                .build();
        Log.i("setOnTimeWorkManger", "onSuccess: "+time);

        WorkManager.getInstance(context).enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE,oneTimeWorkRequest);
    }


    private String serializeToJason(MedicationPOJO pojo) {
        Gson gson = new Gson();
        return gson.toJson(pojo);
    }



    private void callOneTimeRefillReminder(MedicationPOJO medicationPOJO) {

        Data data = new Data.Builder()
                .putString("MedReminderList", serializeToJason(medicationPOJO)).build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        String tag = medicationPOJO.getMedicationName()+medicationPOJO.getId();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(RefillReminderWorkManager.class)
                .setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .addTag(tag)
                .build();
        WorkManager.getInstance(context).enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE,oneTimeWorkRequest);
    }

    private void loopOnRefileMedicationList() {
        for (MedicationPOJO medicationPOJO : medicationListForRefillReminder) {

            if (medicationPOJO.getLeftNumber() <= medicationPOJO.getLeftNumberReminder()) {
                Log.i("loopOnRefill", "onSuccess: "+medicationPOJO.getMedicationName()+medicationPOJO.getLeftNumberReminder());
                callOneTimeRefillReminder(medicationPOJO);
            }
        }
    }

}

