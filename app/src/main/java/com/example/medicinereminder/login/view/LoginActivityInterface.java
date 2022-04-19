package com.example.medicinereminder.login.view;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface LoginActivityInterface {
    void setSuccessfulResponse();
    void setFailureResponse(String errorMassage);
}
