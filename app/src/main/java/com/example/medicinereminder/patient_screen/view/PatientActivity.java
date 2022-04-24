package com.example.medicinereminder.patient_screen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.medicinereminder.R;
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
        initRecyclerView();
        patientPresenter.getPatients();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.patientsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        patientAdapter = new PatientAdapter(this);
        recyclerView.setAdapter(patientAdapter);
    }

    @Override
    public void showPatient(List<PatientDTO> patients) {
        patientAdapter.setPatients(patients);
    }
}