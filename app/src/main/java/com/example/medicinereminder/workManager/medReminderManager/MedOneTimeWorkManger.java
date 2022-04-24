package com.example.medicinereminder.workManager.medReminderManager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class MedOneTimeWorkManger extends Worker {
    String key;
    boolean isTaken;
    Data data;
    public final static String KEY_TAG = "INDEX";
    public final static String VALUE_TAG = "isTaken";
    public final static String MEDICINE_TAG = "MED";


    public MedOneTimeWorkManger(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        getData();
        startMyService();
        return Result.success();
    }

    // getting DataFirst
    private void getData() {
        data = getInputData();
        key = data.getString(KEY_TAG);
        isTaken = data.getBoolean(VALUE_TAG, false);
        Log.i("Toooba", "setOnTimeWorkManger: "+key);
    }


    private void startMyService() {
        Intent intent = new Intent(getApplicationContext(), MedReminderService.class);
        intent.putExtra(MEDICINE_TAG, data.getString(MEDICINE_TAG));
        intent.putExtra(KEY_TAG, key);
        intent.putExtra(VALUE_TAG, isTaken);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getApplicationContext().startForegroundService(intent);
        } else {
            getApplicationContext().startService(intent);
        }
    }
}

