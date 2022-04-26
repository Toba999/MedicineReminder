package com.example.medicinereminder.more_screen.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicinereminder.R;

import com.example.medicinereminder.login.view.LoginActivity;

import com.example.medicinereminder.model.NetworkValidation;

import com.example.medicinereminder.patient_screen.view.PatientActivity;
import com.example.medicinereminder.requests.view.RequestsActivity;
import com.example.medicinereminder.signup.view.SignUpActivity;
import com.example.medicinereminder.tracker_screen.view.TrackerActivity;


public class MoreFragment extends Fragment {

    TextView nameTextView;
    TextView emailTextView;
    ImageView imageView;
    Button patientsBtn,trackersBtn,requestBtn,logOutBtn;
    FragmentTransaction fragmentTransaction;
    SharedPreferences preferences;
    public static final String SHARED_PER = "SHAREDfILE";
    public static final String USER_EMAIL = "USER_EMAIL";
    String email;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_more, container, false);

        patientsBtn = view.findViewById(R.id.morePatientButton);
        trackersBtn = view.findViewById(R.id.moreTrackerButton);
        requestBtn = view.findViewById(R.id.moreRequestButton);

        logOutBtn = view.findViewById(R.id.sign_out);


        if(!NetworkValidation.isOnline(getContext())){
            patientsBtn.setEnabled(false);
            trackersBtn.setEnabled(false);
            requestBtn.setEnabled(false);
            logOutBtn.setVisibility(View.GONE);

        } else {
            patientsBtn.setEnabled(true);
            trackersBtn.setEnabled(true);
            requestBtn.setEnabled(true);
            logOutBtn.setVisibility(View.VISIBLE);

        }
        preferences = getActivity().getSharedPreferences(SHARED_PER, Context.MODE_PRIVATE);
        email=preferences.getString(USER_EMAIL,"null");
        imageView = view.findViewById(R.id.moreImageProfile);
        emailTextView = view.findViewById(R.id.moreEmail);
        if(email != "null"){
            emailTextView.setText(email);
        }else{
            emailTextView.setText("Me");
        }





        patientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), PatientActivity.class));
            }
        });
        trackersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), TrackerActivity.class));
            }
        });

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), RequestsActivity.class));
            }
        });
        sigoutUser();
        return view;
    }

    private void sigoutUser()
    {
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences(SignUpActivity.SHARED_PER, Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("USER_EMAIL").apply();
                sharedPreferences.edit().remove("isLogin").apply();
                sharedPreferences.edit().apply();
                startActivity(new Intent(context, LoginActivity.class));
            }
        });
    }
}