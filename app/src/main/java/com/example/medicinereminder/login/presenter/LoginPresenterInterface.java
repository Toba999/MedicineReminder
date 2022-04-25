package com.example.medicinereminder.login.presenter;

import android.app.Activity;

public interface LoginPresenterInterface {
    void signInWithEmailAndPass(Activity activity, String email, String password);
    void signInUsingGoogle(String idToken);
    void isAlreadySignedWithGoogle(String email);

}
