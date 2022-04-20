package com.example.medicinereminder.login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinereminder.HomeScreen.view.Home_Screen;
import com.example.medicinereminder.R;
import com.example.medicinereminder.login.presenter.LoginPresenter;
import com.example.medicinereminder.login.presenter.LoginPresenterInterface;
import com.example.medicinereminder.signup.view.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity implements LoginActivityInterface {
    public static final String SHARED_PER = "SHAREDfILE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";
    String email ;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    TextView txtSign;
    EditText editEmail,editPassword;
    Button btnLogin;
    FloatingActionButton btnLoginWithGoogle;
    ProgressBar progressBar;
    LoginPresenterInterface prsenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prsenter = new LoginPresenter(LoginActivity.this,this);
        initUI();
        btnLogin.setOnClickListener(view -> userLogin());
        btnLoginWithGoogle.setOnClickListener(view -> {
           // prsenter.signInUsingGoogle();
        });
        txtSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

    }
    public void initUI(){
        editEmail = findViewById(R.id.editEmailLogin);
        editPassword = findViewById(R.id.editPasworedLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginWithGoogle = findViewById(R.id.btnLoginWithGoogle);
        txtSign = findViewById(R.id.txtSign);
        progressBar= findViewById(R.id.progressBar);

    }
    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        initShared(email);

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Minimum lenght of password should be 6");
            editPassword.requestFocus();
            return;
        }
        prsenter.signInWithEmailAndPass(LoginActivity.this,email,password);
        progressBar.setVisibility(View.VISIBLE);


    }

    private void initShared(String myEmail) {
        sharedPref = getSharedPreferences(SHARED_PER, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString(USER_EMAIL, myEmail);
        editor.apply();
        Log.i("login",myEmail);
    }

    @Override
    public void setSuccessfulResponse() {
        startActivity(new Intent(getApplicationContext(), Home_Screen.class));
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setFailureResponse(String errorMassage) {
        Toast.makeText(getApplicationContext(), errorMassage , Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }
}