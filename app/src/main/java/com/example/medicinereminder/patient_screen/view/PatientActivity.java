package com.example.medicinereminder.patient_screen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.medication_for_patient.view.MedicationForPatient;
import com.example.medicinereminder.medication_screen.view.MedicationMainAdapter;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.patient_screen.presenter.PatientPresenter;
import com.example.medicinereminder.patient_screen.presenter.PatientPresenterInterface;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity implements PatientActivityInterface {

    ImageButton imageView;
    PatientPresenterInterface patientPresenter;
    PatientAdapter patientAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        imageView = findViewById(R.id.iv_patients_back);
        patientPresenter = new PatientPresenter(this, this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        showProgressDialog();
        initRecyclerView();
        patientPresenter.getPatients();
    }

    public void showProgressDialog(){
        progressDialog = new ProgressDialog(PatientActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.patientsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        patientAdapter = new PatientAdapter(this, this);
        recyclerView.setAdapter(patientAdapter);
    }

    @Override
    public void showPatient(List<PatientDTO> patients) {
        progressDialog.dismiss();
        patientAdapter.setPatients(patients);
    }

    @Override
    public void deletePatient(String patientEmail, String trackerEmail) {
        patientPresenter.deletePatient(patientEmail, trackerEmail);
    }
}