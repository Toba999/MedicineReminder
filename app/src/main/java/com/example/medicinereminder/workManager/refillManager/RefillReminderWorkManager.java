package com.example.medicinereminder.workManager.refillManager;

import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RefillReminderWorkManager extends Worker {
    Context context;
    public RefillReminderWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        Log.i("Reminder", "doWork: momomom");
        startMyService(data.getString("MedReminderList"));
        return Result.success();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startMyService(String data){
        Intent intent = new Intent(getApplicationContext(), RefillReminderService.class);
        intent.putExtra("refill",data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("startForegroundService", "data");
            getApplicationContext().startForegroundService(intent);
        } else {
            Log.i("startService", "data");
            getApplicationContext().startService(intent);
        }

    }
}
