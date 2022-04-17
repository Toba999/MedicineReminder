package com.example.medicinereminder.tracker_screen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medicinereminder.HomeScreen.HomeFragment;
import com.example.medicinereminder.R;
import com.example.medicinereminder.more_screen.view.MoreFragment;
import com.example.medicinereminder.patient_screen.view.PatientAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrackerActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        imageView = findViewById(R.id.trackersBackBtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initRecyclerView();
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.trackersRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<String> trackers = new ArrayList<>();
        trackers.add(new String("mariam@gmail.com"));
        trackers.add(new String("hanan@gmail.com"));
        TrackerAdapter trackerAdapter = new TrackerAdapter(this, trackers);
        recyclerView.setAdapter(trackerAdapter);
    }
}