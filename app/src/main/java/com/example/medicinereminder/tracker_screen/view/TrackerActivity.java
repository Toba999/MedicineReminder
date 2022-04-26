package com.example.medicinereminder.tracker_screen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.medicinereminder.R;
import com.example.medicinereminder.login.view.LoginActivity;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.tracker_screen.presenter.TrakerPresenter;
import com.example.medicinereminder.tracker_screen.presenter.TrakerPresenterInterface;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.List;

public class TrackerActivity extends AppCompatActivity implements TrakerActivityInterface{

    ImageButton imageButton;
    Button btnAddTraker;
    EditText editTrakerEmail;
    TrakerPresenterInterface presenter;
    TrackerAdapter trackerAdapter;
    String trakerEmail,senderEmail,senderUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        imageButton = findViewById(R.id.trackersBackBtn);
        editTrakerEmail = findViewById(R.id.editeTrakerEmail);
        btnAddTraker = findViewById(R.id.btn_Add_Traker);
        presenter = new TrakerPresenter(getApplicationContext(),this);
        FirebaseUser user = presenter.currentUser();
        senderEmail = user.getEmail();
        presenter.loadTrackers(senderEmail);


        imageButton.setOnClickListener(new View.OnClickListener() {
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
//               //sharedPref.getString(LoginActivity.USER_EMAIL,"null");
                presenter.getUserFromRealDB(senderEmail);
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.trackersRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<TrackerDTO> trackers = new ArrayList<>();
         trackerAdapter = new TrackerAdapter(this, trackers,TrackerActivity.this);
        recyclerView.setAdapter(trackerAdapter);
    }

    @Override
    public void setUserExiste(boolean respons) {
        if(respons){
            presenter.trakerExistence(senderEmail,trakerEmail);

//                RequestDTO request = new RequestDTO(senderUserName, trakerEmail, senderEmail, 0, senderEmail.split("\\.")[0]);
//                Log.i("name", senderUserName);
//
//                presenter.sendRequest(request);
//                Toast.makeText(getApplicationContext(), "the request send", Toast.LENGTH_SHORT).show();
////            }else {
////                Toast.makeText(getApplicationContext(), "You add This Tracker before", Toast.LENGTH_SHORT).show();
////            }

        }else{
            Toast.makeText(getApplicationContext(), "the request cant send", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void setTrakerExiste(boolean respons) {
        if(!respons){
            RequestDTO request = new RequestDTO(senderUserName, trakerEmail, senderEmail, 0, senderEmail.split("\\.")[0]);
            Log.i("name", senderUserName);
            if(trakerEmail.equals(senderEmail)){
                editTrakerEmail.setText("");
                Toast.makeText(getApplicationContext(), "sorry but you can't add yourself", Toast.LENGTH_SHORT).show();

            }else{
                presenter.sendRequest(request);
                editTrakerEmail.setText("");
                Toast.makeText(getApplicationContext(), "the request send", Toast.LENGTH_SHORT).show();
            }

            }else {
            editTrakerEmail.setText("");
            Toast.makeText(getApplicationContext(), "You add This request before", Toast.LENGTH_SHORT).show();
            }

    }

    @Override
    public void setonSuccessReturn(String userName) {
        senderUserName = userName;
        presenter.UserExistence(trakerEmail);

    }

    @Override
    public void setonSuccessTracker(List<TrackerDTO> trackerDTOS) {
        trackerAdapter.setListToAdapter(trackerDTOS);
    }

    @Override
    public void deleteTracker(String takerEmail, String patientEmail) {
        presenter.deleteTracker(takerEmail, patientEmail);
    }
}