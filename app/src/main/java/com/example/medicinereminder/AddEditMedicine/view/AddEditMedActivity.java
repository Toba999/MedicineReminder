package com.example.medicinereminder.AddEditMedicine.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinereminder.AddEditMedicine.presenter.AddMedicationPresenter;
import com.example.medicinereminder.AddEditMedicine.presenter.AddMedicationPresenterInterface;
import com.example.medicinereminder.DisplayMedicine.DisplayMedActivity;
import com.example.medicinereminder.HomeScreen.view.HomeFragment;
import com.example.medicinereminder.HomeScreen.view.Home_Screen;
import com.example.medicinereminder.databinding.ActivityAddEditMedBinding;
import com.example.medicinereminder.localdatabase.LocalSource;
import com.example.medicinereminder.model.Constants;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.TimeUtility;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.workManager.MyPeriodicManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddEditMedActivity extends AppCompatActivity implements AddAndEditMedicationInterface {
    //select Add or Edit Screen
    public static boolean isAdd = true;

    private ActivityAddEditMedBinding binding;


    private long startDate;
    private long endDate;

    //spinner
    private String medicationType = "";
    private String strengthType = "";
    private String instruction = "";
    private String takeTimePerWeek = "";
    private String takeTimePerDay;
    //editable
    private String dosePerDay = "";
    private String medicationName;
    private String start_date;
    private String end_date;
    private String strength = "";
    private String leftNumber = "";
    private String leftNumberReminder = "";
    private String medicineSize = "";
    private String reason = "";

    private Map<String, Boolean> timeSimpleTaken;
    private Map<String, Boolean> dateTimeSimpleTaken=new HashMap<>();
    private Map<String, Boolean> dateTimeAbsTaken=new HashMap<>();
    private List<String> timeAbs;

    private TimeSelectedAdapter timeSelectedAdapter;

    private String email;


    ArrayAdapter<CharSequence> adapterMedType;
    ArrayAdapter<CharSequence> adapterEating;
    ArrayAdapter<CharSequence> adapterStrType;
    ArrayAdapter<CharSequence> adapterPerWeek;
    ArrayAdapter<CharSequence> adapterPerDay;

    SharedPreferences preferences;

    AddMedicationPresenterInterface presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddEditMedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter=new AddMedicationPresenter(this,this);
        Intent intent=getIntent();
        isAdd=intent.getBooleanExtra("isAdd",true);

         preferences = getSharedPreferences(Constants.SHARED_PER, MODE_PRIVATE);
         email=preferences.getString(Constants.USER_EMAIL,"null");
         Log.i("AddScreen",email);
        handleSpinners();
        if (!isAdd) {
            getModelIntent();
        } else {
            handleAddScreen();
         }

    }

    private void handleSpinners(){

        binding.ivAddBack.setOnClickListener(v -> lunchExitDialog());
        binding.btnStartDateEdit.setOnClickListener(v -> showDatePicker(binding.tvSelectedStartDate, "start"));

        binding.btnEndDate.setOnClickListener(v -> showDatePicker(binding.tvSelectedEndDate, "end"));

        adapterMedType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.medType);
        adapterMedType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spEditMedType.setAdapter(adapterMedType);
        binding.spEditMedType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    medicationType=adapterView.getItemAtPosition(i).toString();


                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterEating = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.eatingArr);
        adapterEating.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spEditMedEating.setAdapter(adapterEating);
        binding.spEditMedEating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    instruction=adapterView.getItemAtPosition(i).toString();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapterPerWeek = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.perWeekArr);
        adapterPerWeek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spOccurrWeek.setAdapter(adapterPerWeek);
        binding.spOccurrWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    takeTimePerWeek=adapterView.getItemAtPosition(i).toString();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterStrType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.strTypeArr);
        adapterStrType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spEditStrengthType.setAdapter(adapterStrType);
        binding.spEditStrengthType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    strengthType=adapterView.getItemAtPosition(i).toString();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterPerDay = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.perDayArr);
        adapterPerDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spOccurrDay.setAdapter(adapterPerDay);
        binding.spOccurrDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    int n;
                    takeTimePerDay=adapterView.getItemAtPosition(i).toString();
                    switch (takeTimePerDay){
                        case "once Daily":
                            n=1;
                            break;
                        case "twice Daily":
                            n=2;
                            break;
                        case "3 times a Day":
                            n=3;
                            break;
                        case "4 times a Day":
                            n=4;
                            break;
                        default:
                            n=1;
                    }
                    setDosePerTime(n);
                    Log.i("Toooba",n+"");
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getModelIntent(){
        presenter.setPresenterModel((MedicationPOJO) getIntent().getSerializableExtra("med"));
    }

    public void handleEditScreen(MedicationPOJO medication) {
        binding.tvTitle.setText(Constants.editTitle);
        setSpinnerResult(medication);
        setEditText(medication);
        binding.btnDoneAddEdit.setOnClickListener(v -> {
            boolean isComplete=isDataValid();
            if (isComplete){
                presenter.buildMedObject(medication);
                presenter.setWorkTimer();
            }
        });
        initRecycleView(medication);
    }

    private void setSpinnerResult(MedicationPOJO medication) {
        if (!medication.getMedicationType().isEmpty()) {
            binding.spEditMedType.setSelection(adapterMedType.getPosition(medication.getMedicationType()));
        }

        if (!medication.getStrengthType().isEmpty()) {
            binding.spEditStrengthType.setSelection(adapterStrType.getPosition(medication.getStrengthType()));
        }

        if (!medication.getTakeTimePerDay().isEmpty()) {
            binding.spOccurrDay.setSelection(adapterPerDay.getPosition(medication.getTakeTimePerDay()));
        }

        if (!medication.getInstruction().isEmpty()) {
            binding.spEditMedEating.setSelection(adapterEating.getPosition(medication.getInstruction()));
        }

        if (!medication.getTakeTimePerWeek().isEmpty()) {
            binding.spOccurrWeek.setSelection(adapterPerWeek.getPosition(medication.getTakeTimePerWeek()));
        }
    }

    private void setEditText(MedicationPOJO medication) {
        binding.etEditMedName.setText(medication.getMedicationName());
        binding.etEditNumDose.setText(medication.getDoseNum());
        binding.tvSelectedStartDate.setText(TimeUtility.getDateString(medication.getStartDate()));
        startDate = medication.getStartDate();
        binding.tvSelectedEndDate.setText(TimeUtility.getDateString(medication.getEndDate()));
        endDate = medication.getEndDate();
        binding.etEditStrengthDose.setText(String.valueOf(medication.getStrength()));
        binding.etEditReason.setText(medication.getMedicationReason().isEmpty() ? "" : medication.getMedicationReason());
        binding.etEditLeft.setText(medication.getLeftNumber()+"");
        binding.etEditMedSize.setText(medication.getMedicineSize()+"");
        binding.etRefillReminder.setText(medication.getLeftNumberReminder()+"");
    }

    private void handleAddScreen() {
        binding.tvTitle.setText(Constants.addTitle);
        binding.btnDoneAddEdit.setOnClickListener(v -> {
            boolean isComplete=isDataValid();
            if (isComplete){
                presenter.buildMedObject(new MedicationPOJO());
                presenter.setWorkTimer();
                startActivity(new Intent(AddEditMedActivity.this,Home_Screen.class));
            }

        });
        initRecycleView(new MedicationPOJO());
        handleSpinners();

    }

    private void lunchExitDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure you want to go back no changes will be saved");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                (dialog, which) -> {
                    if(isAdd){
                        startActivity(new Intent(AddEditMedActivity.this, Home_Screen.class));
                    }else{
                        presenter.cancelEditScreen();
                    }
                    dialog.dismiss();

                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();

    }

    @Override
    public void onClick(MedicationPOJO medication) {
        if (isAdd)
            addMedication(medication);
        else
            updateMedication(medication);
    }

    @Override
    public void updateMedication(MedicationPOJO medication) {
        presenter.updateToDatabase(medication,email);

    }

    @Override
    public void addMedication(MedicationPOJO medication) {
        presenter.addToDatabase(medication,email);
    }

    @Override
    public void onSuccess() {
        if(isAdd){
            Toast.makeText(this, "Successfully Added!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "Failed to Add", Toast.LENGTH_SHORT).show();
    }


    // getters
    @Override
    public String getEtName() {
        return binding.etEditMedName.getEditableText().toString().trim();
    }
    @Override
    public String getEtNumDose(){
        return  binding.etEditNumDose.getEditableText().toString().trim();
    }
    @Override
    public String getMedEmail() {
        return email;
    }
    @Override
    public String getEtStartDate() {
        return binding.tvSelectedStartDate.getText().toString().trim();
    }
    @Override
    public String getEtEndDate() {
        return binding.tvSelectedEndDate.getText().toString().trim();
    }
    @Override
    public String getEtLeftNumber() {
        return binding.etEditLeft.getEditableText().toString();
    }
    @Override
    public String getEtLeftNumberReminder() {
        return binding.etRefillReminder.getEditableText().toString();
    }
    @Override
    public String getEtReason() {
        return binding.etEditReason.getEditableText().toString();
    }
    @Override
    public String getEtMedSize() {
        return binding.etEditMedSize.getEditableText().toString();
    }

    @Override
    public String getEtMedStrength() {
        return binding.etEditStrengthDose.getEditableText().toString();
    }

    @Override
    public String getSpinnerMedType() {
        return medicationType;
    }

    @Override
    public String getSpinnerStrengthType() {
        return strengthType;
    }

    @Override
    public String getSpinnerTimePerDay() {
        return takeTimePerDay;
    }

    @Override
    public String getSpinnerTimePerWeek() {
        return takeTimePerWeek;
    }

    @Override
    public String getSpinnerInstruction() {
        return instruction;
    }

    @Override
    public Map<String, Boolean> getDateTimeAbsISTaken() {
        List<String> timeAbs=timeSelectedAdapter.getTimeList();
        int day;
        switch(takeTimePerWeek){
            case "Everyday":
                day=1;
                break;
            case "every two days":
                day=2;
                break;
            case "every three days":
                day=3;
                break;
            case "every four days":
                day=4;
                break;
            case "every five days":
                day=5;
                break;
            case "once a week":
                day=7;
                break;
            default:
                day=1;
        }
        for (long i= startDate;i<=endDate;i+=(day*24*60*60000L)){
            for (int j=0;j<timeAbs.size();j++) {
                Log.i("onTimeSet", startDate+" "+endDate+" "+i + Integer.parseInt(timeAbs.get(j)));
                dateTimeAbsTaken.put((i + Integer.parseInt(timeAbs.get(j))) + "", false);
            }
        }
        return dateTimeAbsTaken;
    }

    @Override
    public Map<String, Boolean> getDateSimpleISTaken() {
        int day;
        switch(takeTimePerWeek){
            case "Everyday":
                day=1;
                break;
            case "every two days":
                day=2;
                break;
            case "every three days":
                day=3;
                break;
            case "every four days":
                day=4;
                break;
            case "every five days":
                day=5;
                break;
            case "once a week":
                day=7;
                break;
            default:
                day=1;
        }
        for (long i= startDate;i<=endDate;i+=(day*24*60*60000L)){
            dateTimeSimpleTaken.put(TimeUtility.getDateString(i),false);
        }
        return dateTimeSimpleTaken;
    }

    @Override
    public Map<String, Boolean> getTimeSimpleIsTaken() {
        return timeSelectedAdapter.getTimeMap();
    }

    @Override
    public List<String> setDateTimeAbsolute() {
        return timeSelectedAdapter.getTimeList();
    }

    private boolean isDataValid(){
        if (binding.etEditMedName.getText().toString().length()==0){
            binding.etEditMedName.requestFocus();
            binding.etEditMedName.setError("Required Field");
        }
        else if (binding.etEditNumDose.getText().toString().length()==0){
            binding.etEditMedName.setError(null);
            binding.etEditNumDose.requestFocus();
            binding.etEditNumDose.setError("Required Field");
        }
        else if(binding.etEditReason.getText().toString().length()==0){
            binding.etEditMedName.setError(null);
            binding.etEditNumDose.setError(null);
            binding.etEditReason.requestFocus();
            binding.etEditReason.setError("Required Field");
        }
        else if(binding.etEditStrengthDose.getText().toString().length()==0){
            binding.etEditMedName.setError(null);
            binding.etEditNumDose.setError(null);
            binding.etEditReason.setError(null);
            binding.etEditStrengthDose.requestFocus();
            binding.etEditStrengthDose.setError("Required Field");
        }
        else if(binding.etEditLeft.getText().toString().length()==0){
            binding.etEditMedName.setError(null);
            binding.etEditNumDose.setError(null);
            binding.etEditReason.setError(null);
            binding.etEditStrengthDose.setError(null);
            binding.etEditLeft.requestFocus();
            binding.etEditLeft.setError("Required Field");
        }
        else if(binding.etRefillReminder.getText().toString().length()==0){
            binding.etEditMedName.setError(null);
            binding.etEditNumDose.setError(null);
            binding.etEditReason.setError(null);
            binding.etEditStrengthDose.setError(null);
            binding.etEditLeft.setError(null);
            binding.etRefillReminder.requestFocus();
            binding.etRefillReminder.setError("Required Field");
        }
        else if(binding.etEditMedSize.getText().toString().length()==0){
            binding.etEditMedName.setError(null);
            binding.etEditNumDose.setError(null);
            binding.etEditReason.setError(null);
            binding.etEditStrengthDose.setError(null);
            binding.etEditLeft.setError(null);
            binding.etRefillReminder.setError(null);
            binding.etEditMedSize.requestFocus();
            binding.etEditMedSize.setError("Required Field");
        } else {
            return true;
        }

        return false;
    }

    private void showDatePicker(TextView v, String s) {
        final Calendar myCalender = Calendar.getInstance();
        int year = myCalender.get(Calendar.YEAR);
        int month = myCalender.get(Calendar.MONTH);
        int day = myCalender.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener myDateListener = (view, year1, month1, day1) -> {
            if (view.isShown()) {
                month1 = month1 + 1;
                String date = day1 + "-" + month1 + "-" + year1;
                if (s.equals("start")) {
                    startDate = TimeUtility.getDateMillis(date);
                } else {
                    endDate = TimeUtility.getDateMillis(date);
                }
                v.setText(date);
            }

        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myDateListener, year, month, day);
        String title = "Choose " + s + " date";
        datePickerDialog.setTitle(title);

        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }


    private void initRecycleView(MedicationPOJO medication) {
        int n = 0;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (medication.getTimeSimpleTaken() != null && medication.getTimeSimpleTaken().size() != 0) {
            n = medication.getDateTimeAbs().size();
            Log.i("toooba",medication.getTimeSimpleTaken().toString());
            timeSelectedAdapter = new TimeSelectedAdapter(n, this, medication.getTimeSimpleTaken());
            timeSelectedAdapter.setTimeAndDose(medication.getTimeSimpleTaken());
        }else{
            timeSelectedAdapter = new TimeSelectedAdapter(n, this, medication.getTimeSimpleTaken());

        }
        binding.recycleChooseTime.setLayoutManager(layoutManager);
        binding.recycleChooseTime.setAdapter(timeSelectedAdapter);

    }

    private void setDosePerTime(int n) {
        timeSelectedAdapter.setDosePerDay(n);
        timeSelectedAdapter.notifyDataSetChanged();
    }

}
