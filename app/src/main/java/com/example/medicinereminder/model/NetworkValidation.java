package com.example.medicinereminder.model;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.medicinereminder.signup.view.SignUpActivity;

public class NetworkValidation {


    private static SharedPreferences sharedPref;
    private static String email;

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo.isConnectedOrConnecting();
    }

    public static String checkShared(Context context) {
        sharedPref = context.getSharedPreferences(SignUpActivity.SHARED_PER, Context.MODE_PRIVATE);
        email = sharedPref.getString(SignUpActivity.EMAIL, "null");
        return email;
    }

}


