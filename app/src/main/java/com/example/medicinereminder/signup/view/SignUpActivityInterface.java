package com.example.medicinereminder.signup.view;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface SignUpActivityInterface {
    public void setSuccessfulResponse();
    void setFailureResponse(String errormessge, Task<AuthResult> task);
}
