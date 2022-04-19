package com.example.medicinereminder.signup.view;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface SignUpActivityInterface {
     void setSuccessfulResponse();
    void setFailureResponse(Task<AuthResult> task);
}
