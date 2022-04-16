package com.example.medicinereminder.addAndEditMedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.medicinereminder.databinding.ActivityAddEditMedBinding;

public class AddAndEditMedActivity extends AppCompatActivity {
    private ActivityAddEditMedBinding binding;

    ArrayAdapter<CharSequence> adapterMedType;
    ArrayAdapter<CharSequence> adapterEating;
    ArrayAdapter<CharSequence> adapterStrType;
    ArrayAdapter<CharSequence> adapterPerWeek;
    ArrayAdapter<CharSequence> adapterPerDay;



    String[] medType = {"pills", "drops", "injection", "powder", "syrup"};
    String[] eatingArr = {"Before eating", "While eating","After eating"};
    String[] strTypeArr = {"mg", "mEq", "mL", "mg/g", "mg/cm","mcg","IU","g","%"};
    String[] perWeekArr = {"Everyday", "every two days", "every three days", "every four days", "every five days" ,"once a week"};
    String[] perDayArr = {"once Daily", "twice Daily", "3 times a Day", "4 times a Day"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddEditMedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleSpinners();


    }

    private void handleSpinners(){
        adapterMedType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, medType);
        adapterMedType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spEditMedType.setAdapter(adapterMedType);
        binding.spEditMedEating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "This is " +
                        adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();

                try {
                    //Your task here
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterEating = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eatingArr);
        adapterEating.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spEditMedEating.setAdapter(adapterEating);
        binding.spEditMedEating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "This is " +
                        adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();

                try {
                    //Your task here
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapterPerWeek = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, perWeekArr);
        adapterPerWeek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spOccurrWeek.setAdapter(adapterPerWeek);
        binding.spOccurrWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "This is " +
                        adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();

                try {
                    //Your task here
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterStrType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, strTypeArr);
        adapterStrType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spEditStrengthType.setAdapter(adapterStrType);
        binding.spEditStrengthType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "This is " +
                        adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();

                try {
                    //Your task here
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterPerDay = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, perDayArr);
        adapterPerDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spOccurrDay.setAdapter(adapterPerDay);
        binding.spOccurrDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "This is " +
                        adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();

                try {
                    //Your task here
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

}