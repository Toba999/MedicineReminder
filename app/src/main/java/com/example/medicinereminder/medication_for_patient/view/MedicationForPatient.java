package com.example.medicinereminder.medication_for_patient.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.medicinereminder.R;

public class MedicationForPatient extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton backImageButton;
    MedicationForPatientAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_for_patient);
        recyclerView = findViewById(R.id.patient_medication_recyclerView);
        backImageButton = findViewById(R.id.iv_patientMed_back);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        myAdapter = new MedicationForPatientAdapter(this);
        recyclerView.setAdapter(myAdapter);
    }
}