package com.example.medicinereminder.DisplayMedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicinereminder.AddEditMedicine.view.AddEditMedActivity;
import com.example.medicinereminder.R;
import com.example.medicinereminder.model.MedicationPOJO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayMedActivity extends AppCompatActivity {

    MedicationPOJO medicationPOJO;
    TextView medNameTextView;
    TextView medStrengthTextView;
    TextView medDoseAmountTextView;
    TextView medBottleAmountTextView;
    TextView medRemainingAmountTextView;
    TextView medStartTimeTextView;
    TextView medStartDateTextView;
    TextView medEndDateTextView;
    TextView medTimePerDayTextView;
    TextView medTimePerWeekTextView;
    ImageView medImageView;
    ImageView backIcon;
    ImageView editIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_med);
        Intent intent = getIntent();
        medicationPOJO =(MedicationPOJO) getIntent().getSerializableExtra("med");
        initializeViews();
        setDataToViews();
        backIcon = findViewById(R.id.iv_dis_back);
        editIcon = findViewById(R.id.iv_edit_med);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DisplayMedActivity.this, AddEditMedActivity.class);
                intent1.putExtra("med", (Serializable) medicationPOJO);
                intent1.putExtra("isAdd", false);
                startActivity(intent1);
            }
        });
    }

    public void initializeViews(){

        medImageView = findViewById(R.id.displayImage);
        medBottleAmountTextView = findViewById(R.id.displayBottleAmount); //med size
        medRemainingAmountTextView = findViewById(R.id.displayRemainAmount); //left
        medTimePerWeekTextView = findViewById(R.id.displayTimePerWeek); //once
        medTimePerDayTextView = findViewById(R.id.displayTimePerDay); //after eating
        medNameTextView = findViewById(R.id.displayMedName); //name
        medStartDateTextView = findViewById(R.id.displayStartDate);
        medEndDateTextView = findViewById(R.id.displayEndDate);
        medStrengthTextView = findViewById(R.id.displayMedStrength);
        medDoseAmountTextView = findViewById(R.id.displayDoseAmount); //2
        medStartTimeTextView = findViewById(R.id.displayStartTime); //
    }

    public void setDataToViews(){
        setImage(medicationPOJO.getMedicationType());
        medBottleAmountTextView.setText(String.valueOf(medicationPOJO.getMedicineSize()));
        medRemainingAmountTextView.setText(String.valueOf(medicationPOJO.getLeftNumber()));
        medTimePerWeekTextView.setText(medicationPOJO.getTakeTimePerWeek());
        medTimePerDayTextView.setText(medicationPOJO.getInstruction());
        medNameTextView.setText(medicationPOJO.getMedicationName());
        Date date=new Date(medicationPOJO.getStartDate());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String dateText = df2.format(date);
        medStartDateTextView.setText(dateText); //
        date=new Date(medicationPOJO.getEndDate());
        df2 = new SimpleDateFormat("dd/MM/yyyy");
        dateText = df2.format(date);
        medEndDateTextView.setText(dateText); //
        medStrengthTextView.setText(String.valueOf(medicationPOJO.getStrength()));
        medDoseAmountTextView.setText(String.valueOf(medicationPOJO.getTakeTimePerDay()));
    }
    public void setImage(String imgName){
        if(imgName.equals("drops"))
            medImageView.setImageResource(R.mipmap.drops);
        else if(imgName.equals("injections"))
            medImageView.setImageResource(R.mipmap.injections);
        else if(imgName.equals("pills"))
            medImageView.setImageResource(R.mipmap.pills);
        else if(imgName.equals("syrup"))
            medImageView.setImageResource(R.mipmap.syrup);
        else
            medImageView.setImageResource(R.mipmap.powder);
    }
}