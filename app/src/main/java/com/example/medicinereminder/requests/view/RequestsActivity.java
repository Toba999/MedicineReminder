package com.example.medicinereminder.requests.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.medicinereminder.R;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.requests.presenter.RequestsPresenter;
import com.example.medicinereminder.requests.presenter.RequestsPresenterInterface;
import java.util.ArrayList;
import java.util.List;

public class RequestsActivity extends AppCompatActivity implements RequestsActivityInterface{
    ImageView imageView;
    RecyclerView recyclerView;
    RequestsAdapter requestsAdapter;
    RequestsPresenterInterface prsenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        imageView = findViewById(R.id.RequestsBackBtn);
        initRecyclerView();
        prsenter = new RequestsPresenter(RequestsActivity.this,this);
        String email = prsenter.currentUser().getEmail();
        Log.i("email",email);
        prsenter.loadRequests(email);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.requestsRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<RequestDTO> rquests = new ArrayList<>();
        requestsAdapter = new RequestsAdapter(this, rquests,RequestsActivity.this);
        recyclerView.setAdapter(requestsAdapter);
    }

    @Override
    public void setonSuccessRequest(List<RequestDTO> requestPojos) {
        requestsAdapter.setListToAdapter(requestPojos);
    }

    @Override
    public void setonSuccessReturn(String userName) {
        System.out.println("in activiyy");
        requestsAdapter.setonSuccessReturn(userName);

    }

    @Override
    public void getUserFromRealDB(String email) {
        prsenter.getUserFromRealDB(email);
    }

    @Override
    public void confirem(TrackerDTO tracker, PatientDTO patient) {
        prsenter.onAccept(tracker,patient);
    }

    @Override
    public void delete(String key, String email) {
        prsenter.onReject(key,email);
    }
}