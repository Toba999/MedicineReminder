package com.example.medicinereminder.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity {
    //TextView txtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
//        txtLogin = findViewById(R.id.txtLogin);
//        txtLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//
//            }
//        });

    }
}