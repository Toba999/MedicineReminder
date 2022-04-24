package com.example.medicinereminder.login.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class LoginActivity extends AppCompatActivity implements LoginActivityInterface {
    public static final String SHARED_PER = "SHAREDfILE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";
    String email ;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private static final int REQUEST_PERMISSION = 14;
    public final static int REQUEST_CODE = -1010101;
    TextView txtSign;
    EditText editEmail,editPassword;
    Button btnLogin;
    FloatingActionButton btnLoginWithGoogle;
    ProgressBar progressBar;
    LoginPresenterInterface presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkDrawOverlayPermission();
        sharedPref = getSharedPreferences(SHARED_PER, Context.MODE_PRIVATE);
       // sharedPref.edit().remove(USER_EMAIL).commit();
        //sharedPref.edit().remove("isLogin").commit();

       if(sharedPref.getBoolean("isLogin",false)){
           startActivity(new Intent(getApplicationContext(), Home_Screen.class));
           finish();
       }

        presenter = new LoginPresenter(LoginActivity.this,this);
        initUI();
        btnLogin.setOnClickListener(view -> userLogin());
        btnLoginWithGoogle.setOnClickListener(view -> {
           // prsenter.signInUsingGoogle();
        });
        txtSign.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));

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
        presenter.signInWithEmailAndPass(LoginActivity.this,email,password);
        progressBar.setVisibility(View.VISIBLE);


    }

    private void initShared(String myEmail) {
        sharedPref = getSharedPreferences(SHARED_PER, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

       // editor.putString(USER_EMAIL, email);

        editor.putBoolean("isLogin",true);
       editor.putString(USER_EMAIL, myEmail);

      //  editor.apply();
      editor.commit();

    }

    @Override
    public void setSuccessfulResponse() {

        startActivity(new Intent(getApplicationContext(), Home_Screen.class));
        progressBar.setVisibility(View.INVISIBLE);
        finish();


    }

    @Override
    public void setFailureResponse(String errorMassage) {
        Toast.makeText(getApplicationContext(), errorMassage , Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("NewApi")
    public void checkDrawOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Permission Required");
            alertDialog.setMessage("Enable Overlay Permission");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Enable",
                    (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getApplicationContext().getPackageName()));
                        // request permission via start activity for result
                        startActivityForResult(intent, REQUEST_CODE); //It will call onActivityResult Function After you press Yes/No and go Back after giving permission
                        dialog.dismiss();
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int permissionRequestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(permissionRequestCode, permissions, grantResults);
        if (permissionRequestCode == REQUEST_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "please we need your permission to have all our features", Toast.LENGTH_LONG).show();
            }
        }
    }
}