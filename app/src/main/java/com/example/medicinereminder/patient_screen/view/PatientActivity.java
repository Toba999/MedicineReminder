package com.example.medicinereminder.patient_screen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.medicinereminder.R;
import com.example.medicinereminder.medication_screen.view.MedicationMainAdapter;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.patientsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<String> patients = new ArrayList<>();
        patients.add(new String("thoraya@gmail.com"));
        patients.add(new String("thoria743@gmail.com"));

        PatientAdapter patientAdapter = new PatientAdapter(this, patients);
        recyclerView.setAdapter(patientAdapter);
    }
}