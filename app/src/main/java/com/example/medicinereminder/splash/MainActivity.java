package com.example.medicinereminder.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import com.example.medicinereminder.R;
import com.example.medicinereminder.broadcast.NetworkStateListener;
import com.example.medicinereminder.login.view.LoginActivity;

public class MainActivity extends AppCompatActivity {

   // MediaPlayer mySong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mySong= MediaPlayer.create(MainActivity.this,R.raw.intro);
        //mySong.start();
        initConnectionListener();
        SplashScreen();
    }
    private void initConnectionListener(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkStateListener(), intentFilter);
    }
    private void SplashScreen(){
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
        }, 3000);
    }

}
