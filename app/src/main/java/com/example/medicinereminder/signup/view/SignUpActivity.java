package com.example.medicinereminder.signup.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinereminder.R;
import com.example.medicinereminder.login.view.LoginActivity;
import com.example.medicinereminder.signup.presenter.SignUpPresenter;
import com.example.medicinereminder.signup.presenter.SignupPresenterInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements SignUpActivityInterface {

    public static final String SHARED_PER = "SHAREDfILE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";
    String email ;
    String name ;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public static String EMAIL;

    TextView txtLogin;
    EditText editUserName,editEmail,editPassword,editConfirmPassword;
    Button btnSignUp;
    ProgressBar progressBar;

    SignupPresenterInterface presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        presenter = new SignUpPresenter(SignUpActivity.this,this);
        txtLogin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

        btnSignUp.setOnClickListener(view -> registerUser());

    }
    public void initUI(){
        txtLogin = findViewById(R.id.txtLogin);
        editEmail = findViewById(R.id.editEmailSign);
        editPassword = findViewById(R.id.editPasworedSign);
        editConfirmPassword =findViewById(R.id.editConfirmPassword);
        editUserName = findViewById(R.id.editUserNameSign);
        btnSignUp = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.progressBar2);

    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String name = editUserName.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();

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

        if(name.isEmpty()){
            editUserName.setError("user name is required");
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Minimum length of password should be 6");
            editPassword.requestFocus();
            return;
        }
        if(!confirmPassword.equals(password)){
            editConfirmPassword.setError("this not match password");
            return;
        }


        presenter.registerWithEmailAndPass(SignUpActivity.this,email,password,name);
        progressBar.setVisibility(View.VISIBLE);
        initShared(email);
    }

    private void initShared(String myEmail) {
        Context context = this;
        sharedPref = context.getSharedPreferences(SHARED_PER, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString(USER_EMAIL, myEmail);
        editor.apply();
    }

    @Override
    public void setSuccessfulResponse() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setFailureResponse( Task<AuthResult> task) {
        progressBar.setVisibility(View.INVISIBLE);
        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
            Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}