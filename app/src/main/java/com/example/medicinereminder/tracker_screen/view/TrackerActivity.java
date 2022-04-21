package com.example.medicinereminder.tracker_screen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.medicinereminder.R;
import com.example.medicinereminder.login.view.LoginActivity;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.tracker_screen.presenter.TrakerPresenter;
import com.example.medicinereminder.tracker_screen.presenter.TrakerPresenterInterface;

import java.util.ArrayList;
import java.util.List;

public class TrackerActivity extends AppCompatActivity implements TrakerActivityInterface{

    ImageView imageView;
    Button btnAddTraker;
    EditText editTrakerEmail;
    TrakerPresenterInterface presenter;
    String trakerEmail;
    String senderEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        imageView = findViewById(R.id.trackersBackBtn);
        editTrakerEmail = findViewById(R.id.editeTrakerEmail);
        btnAddTraker = findViewById(R.id.btn_Add_Traker);
        presenter = new TrakerPresenter(getApplicationContext(),this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddTraker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trakerEmail = editTrakerEmail.getText().toString();
                SharedPreferences sharedPref = getSharedPreferences(LoginActivity.SHARED_PER, Context.MODE_PRIVATE);
                senderEmail = sharedPref.getString(LoginActivity.USER_EMAIL,"null");
                if(presenter.UserExistence(trakerEmail)){
               // presenter.UserExistence(trakerEmail);
                    RequestDTO request = new RequestDTO("tasnem",trakerEmail,senderEmail,0);
                presenter.sendRequest(request);
                Toast.makeText(getApplicationContext(), "the request send", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "the request send", Toast.LENGTH_SHORT).show();

                }

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

   // @Override
//    public void isUserExiste(boolean respons) {
//        if(respons){
//            RequestDTO request = new RequestDTO("tasnem",trakerEmail,senderEmail,0);
//            presenter.sendRequest(request);
//            Toast.makeText(getApplicationContext(), "the request send", Toast.LENGTH_SHORT).show();
//
//        }else{
//            Toast.makeText(getApplicationContext(), "the request cant send", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }
}