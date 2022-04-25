package com.example.medicinereminder.medication_for_patient.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.medication_for_patient.presenter.MedicationForPatientPresenter;
import com.example.medicinereminder.medication_for_patient.presenter.MedicationForPatientPresenterInterface;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MedicationForPatient extends AppCompatActivity implements MedicationForPatientInterface {

    RecyclerView recyclerView;
    ImageButton backImageButton;
    MedicationForPatientAdapter myAdapter;
    PatientDTO patient;
    MedicationForPatientPresenterInterface presenter;
    ProgressDialog progressDialog;
    Boolean oldOneIsTaken, oldTwoIsTaken, oldThreeIsTaken, oldFourIsTaken;

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

        myAdapter = new MedicationForPatientAdapter(this, this);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void showMedications(List<MedicationPOJO> medications) {
        Log.i("medSize", String.valueOf(medications.size()));
        progressDialog.dismiss();
        myAdapter.setMedications(medications);
    }

    public void showMedicineDialog(MedicationPOJO medicine, String timeStr) {
        Dialog medicineDialog = new Dialog(MedicationForPatient.this);
        medicineDialog.setContentView(R.layout.medicine_dialog_second);
        medicineDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        medicineDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button doneBtn,cancelBtn;

        CardView cardView = medicineDialog.findViewById(R.id.second_dialog_medicine_cardView);
        doneBtn = medicineDialog.findViewById(R.id.second_dialog_done_Btn);
        cancelBtn = medicineDialog.findViewById(R.id.second_dialog_cancel_Btn);

        TextView mediName = medicineDialog.findViewById(R.id.second_dialog_medicine_name);
        mediName.setText(medicine.getMedicationName());

        TextView dos = medicineDialog.findViewById(R.id.second_dialog_medicine_dos);
        dos.setText(medicine.getStrength()+medicine.getStrengthType()+","+medicine.getDoseNum()+medicine.getMedicationType());

        TextView AfBeEating = medicineDialog.findViewById(R.id.second_dialog_medicine_note);
        AfBeEating.setText(medicine.getInstruction());

        TextView mediReason = medicineDialog.findViewById(R.id.second_dialog_medicine_reason);
        mediReason.setText(medicine.getMedicationReason());

        CheckBox timeOne = medicineDialog.findViewById(R.id.second_dialog_medicine_time_one);
        CheckBox timeTwo = medicineDialog.findViewById(R.id.second_dialog_medicine_time_two);
        CheckBox timeThree = medicineDialog.findViewById(R.id.second_dialog_medicine_time_three);
        CheckBox timeFour = medicineDialog.findViewById(R.id.second_dialog_medicine_time_four);

        int size = medicine.getTimeSimpleTaken().size();
        timeOne.setText(medicine.getTimeSimpleTaken().keySet().toArray()[0].toString());

        oldOneIsTaken = Boolean.getBoolean(medicine.getTimeSimpleTaken().values().toArray()[0].toString());
        MedicationPOJO oldMedication = medicine;

        timeOne.setChecked(oldOneIsTaken);
        if(size >= 2){
            oldTwoIsTaken = Boolean.getBoolean(medicine.getTimeSimpleTaken().values().toArray()[1].toString());
            timeTwo.setText(medicine.getTimeSimpleTaken().keySet().toArray()[1].toString());
            timeTwo.setChecked(oldTwoIsTaken);
        }
        if(size >= 3){
            oldThreeIsTaken = Boolean.getBoolean(medicine.getTimeSimpleTaken().values().toArray()[2].toString());
            timeThree.setText(medicine.getTimeSimpleTaken().keySet().toArray()[2].toString());
            timeThree.setChecked(oldThreeIsTaken);
        }
        if(size == 4){
            oldFourIsTaken = Boolean.getBoolean(medicine.getTimeSimpleTaken().values().toArray()[3].toString());
            timeFour.setText(medicine.getTimeSimpleTaken().keySet().toArray()[3].toString());
            timeFour.setChecked(oldFourIsTaken);

        }
        switch (size){
            case 1:
                timeTwo.setVisibility(View.INVISIBLE);
                timeThree.setVisibility(View.INVISIBLE);
                timeFour.setVisibility(View.INVISIBLE);
                break;
            case 2:
                timeThree.setVisibility(View.INVISIBLE);
                timeFour.setVisibility(View.INVISIBLE);
                break;
            case 3:
                timeFour.setVisibility(View.INVISIBLE);
                break;
        }

        timeOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(oldOneIsTaken != true && timeOne.isChecked() == true){
                    updateMed(timeOne.isChecked(), medicine, 0);
                }
            }
        });
        timeTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(oldTwoIsTaken != true && timeTwo.isChecked() == true){
                    updateMed(timeTwo.isChecked(), medicine, 1);
                }
            }
        });
        timeThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(oldThreeIsTaken != true && timeThree.isChecked() == true){
                    updateMed(timeThree.isChecked(), medicine, 2);
                }
            }
        });
        timeFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(oldFourIsTaken != true && timeFour.isChecked() == true){
                    updateMed(timeFour.isChecked(), medicine, 3);
                }
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.updateMedStatus(patient.getEmail(),medicine);
                medicineDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicineDialog.dismiss();
            }
        });
        medicineDialog.show();
    }
    private Long simpleTimeToAbs(String time) {
        String[] times = time.split(":");
        String part1 = times[0]; // 004
        String[] part2 = times[1].split(" ");
        Long hours = Long.parseLong(part1);
        Long mins = Long.parseLong(part2[0]);
        Long res = hours * 60 + mins;
        if (part2[1].equals("PM")) {
            res += (12 * 60);
        }
        return res;
    }
    private void updateMed(Boolean checked, MedicationPOJO medicine, int position){
        String time = medicine.getTimeSimpleTaken().keySet().toArray()[position].toString();
        medicine.getTimeSimpleTaken().put(time, checked);
        Long absTime = simpleTimeToAbs(time);
        medicine.getDateTimeAbsTaken().put(absTime.toString(),true);
        Date date=new Date(Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        String dateText = df2.format(date);
        medicine.getDateTimeSimpleTaken().put(dateText,true);
    }
}