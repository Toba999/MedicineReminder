package com.example.medicinereminder.workManager.refillManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.medicinereminder.R;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.splash.MainActivity;
import com.example.medicinereminder.workManager.medReminderManager.MedOneTimeWorkManger;
import com.example.medicinereminder.workManager.medReminderManager.MedWindowManager;
import com.google.gson.Gson;

public class RefillReminderService extends Service {
    String NOTIFICATION_CHANNEL_ID = "example.Refill";
    String channelName = "Refill Background Service";
    NotificationManager notificationManager;
    final static int CHANNEL_ID = 12;
    final static int FOREGROUND_ID = 20;
    MedicationPOJO myMedicine;

    public RefillReminderService() { }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        myMedicine = fomStringPojo(intent.getStringExtra("refill"));
        Log.i("Reminder", "onStartCommand: ");
        notificationChannel();
        startForeground(FOREGROUND_ID, createNotification());
        if (Settings.canDrawOverlays(this)) {
            RefillWindowManager window = new RefillWindowManager(this, intent.getStringExtra("refill"));
            window.open();
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public MedicationPOJO fomStringPojo(String pojoString) {
        Gson gson = new Gson();
        return gson.fromJson(pojoString, MedicationPOJO.class);
    }

    private Notification createNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.refill_icon2_foreground);
        String desc ="you only have "+myMedicine.getLeftNumber()+" "+myMedicine.getMedicationType()+"(s) left";
        return new NotificationCompat.Builder(getApplicationContext(),
                String.valueOf(CHANNEL_ID))
                .setContentTitle("Refill Reminder")
                .setContentText(desc)
                .setAutoCancel(true)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(desc))
                .setSmallIcon(R.mipmap.refill_icon2_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

    }

    private void notificationChannel() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
        Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() +
                "/" + R.raw.refillring);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    String.valueOf(CHANNEL_ID), channelName, importance);
            channel.setDescription(NOTIFICATION_CHANNEL_ID);
            channel.setSound(soundUri, audioAttributes);
            notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}


