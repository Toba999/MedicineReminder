package com.example.medicinereminder.login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    TextView txtSign;
    EditText editEmail,editPassword;
    Button btnLogin;
    FloatingActionButton btnLoginWithGoogle;
    LoginPresenterInterface prsenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prsenter = new LoginPresenter(LoginActivity.this,this);
        initUI();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        btnLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // prsenter.signInUsingGoogle();
            }
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

    }
    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

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



        //progressBar.setVisibility(View.VISIBLE);

//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//               // progressBar.setVisibility(View.GONE);
//                if (task.isSuccessful()) {
//                    Toast.makeText(getApplicationContext(), "login scussfully", Toast.LENGTH_SHORT).show();
//
////                    finish();
////                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    txtPassword.setText("");
//                    Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
//                    //  startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                }else {
//                    Toast.makeText(getApplicationContext(), "Error ! invalid user or passwared" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    //   progressBar.setVisibility(View.GONE);
//                }
//
//            }
//        });

    }

    @Override
    public void setSuccessfulResponse() {
        startActivity(new Intent(getApplicationContext(), Home_Screen.class));

    }

    @Override
    public void setFailureResponse(String errorMassage) {
        Toast.makeText(getApplicationContext(), "Error ! invalid user or password" , Toast.LENGTH_SHORT).show();


    }
}