package com.example.medicinereminder.medication_for_patient.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.medicinereminder.R;
import com.example.medicinereminder.medication_for_patient.presenter.MedicationForPatientPresenter;
import com.example.medicinereminder.medication_for_patient.presenter.MedicationForPatientPresenterInterface;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;

import java.util.List;

public class MedicationForPatient extends AppCompatActivity implements MedicationForPatientInterface {

    RecyclerView recyclerView;
    ImageButton backImageButton;
    MedicationForPatientAdapter myAdapter;
    PatientDTO patient;
    MedicationForPatientPresenterInterface presenter;
    ProgressDialog progressDialog;

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
        patient = (PatientDTO) getIntent().getSerializableExtra("patient");
        showProgressDialog();
        initRecyclerView();
        presenter = new MedicationForPatientPresenter(this, this);
        presenter.getMedicines(patient.getEmail());
    }
    public void showProgressDialog(){
        progressDialog = new ProgressDialog(MedicationForPatient.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        myAdapter = new MedicationForPatientAdapter(this);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void showMedications(List<MedicationPOJO> medications) {
        Log.i("medSize", String.valueOf(medications.size()));
        progressDialog.dismiss();
        myAdapter.setMedications(medications);
    }
}