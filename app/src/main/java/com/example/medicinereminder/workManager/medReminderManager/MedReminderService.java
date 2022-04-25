package com.example.medicinereminder.workManager.medReminderManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.medicinereminder.R;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.TimeUtility;
import com.example.medicinereminder.splash.MainActivity;
import com.google.gson.Gson;

public class MedReminderService extends Service {

    private MedicationPOJO myMedicine;

    String key;
    boolean isTaken;
    final static int CHANNEL_ID = 5;
    final static int FOREGROUND_ID = 10;
    NotificationManager notificationManager;
    String description;
    MedWindowManager medWindowManager;

    public MedReminderService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        myMedicine = fomStringPojo(intent.getStringExtra(MedOneTimeWorkManger.MEDICINE_TAG));
        key = intent.getStringExtra(MedOneTimeWorkManger.KEY_TAG);
        isTaken = intent.getBooleanExtra(MedOneTimeWorkManger.VALUE_TAG, false);
        Log.i("onStartCommand","start*********");
        notificationChannel();
        startForeground(FOREGROUND_ID, makeNotification());
        if (Settings.canDrawOverlays(this)) {
            medWindowManager = new MedWindowManager(getApplicationContext(), myMedicine, key, isTaken);
            medWindowManager.setMyWindowManger();
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

    private Notification makeNotification() {


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pills);
        String dateSimple= TimeUtility.getDateString(TimeUtility.getDateNowMilli());
        Log.i("makeNotification","start*********");
        description = "take " + myMedicine.getDoseNum() + " " + myMedicine.getMedicationType() + "(s), " +
                myMedicine.getStrength() + " "+ myMedicine.getStrengthType() + " "+myMedicine.getInstruction();
        return new NotificationCompat.Builder(getApplicationContext(),
                String.valueOf(CHANNEL_ID))
                .setSmallIcon(R.mipmap.pills)
                .setContentText("Schedule for " +  dateSimple)
                .setContentTitle("Medication Reminder")
                .setLargeIcon(bitmap)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(description))
                .setAutoCancel(true)
                .build();

    }




    private void notificationChannel() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
        Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() +
                "/" + R.raw.notring);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    String.valueOf(CHANNEL_ID), name, importance);
            channel.setDescription(description);
            channel.setSound(soundUri, audioAttributes);

            notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
