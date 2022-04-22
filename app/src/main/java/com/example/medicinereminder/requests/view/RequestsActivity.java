package com.example.medicinereminder.requests.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.patient_screen.view.PatientAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestsActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        imageView = findViewById(R.id.RequestsBackBtn);
        initRecyclerView();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.requestsRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<RequestDTO> rquests = new ArrayList<>();
        RequestDTO request1 = new RequestDTO( "tasnem", "tasnem@gmail.com", "tasneem@gmail.com", 0);
        RequestDTO request2 = new RequestDTO( "tasnem", "tasnem@gmail.com", "tasneem@gmail.com", 0);
        rquests.add(request1);
        rquests.add(request2);

        RequestsAdapter requestsAdapter = new RequestsAdapter(this, rquests);
        recyclerView.setAdapter(requestsAdapter);


    }
}