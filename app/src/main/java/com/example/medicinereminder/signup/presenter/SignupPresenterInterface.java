package com.example.medicinereminder.signup.presenter;

import android.app.Activity;
import android.content.Context;

public interface SignupPresenterInterface {
    void registerWithEmailAndPass(Activity activity, String email, String password, String name);

}
