package com.example.medicinereminder.medication_screen.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.medication_screen.presenter.MedicationFragmentPresenter;
import com.example.medicinereminder.medication_screen.presenter.MedicationFragmentPresenterInterface;
import com.example.medicinereminder.model.MedicationPOJO;

public class MedicationsFragment extends Fragment implements MedicationFragmentInterface{

    MedicationFragmentPresenterInterface medicationPresenter;
    ProgressDialog progressDialog;
    boolean active, inActive;
    RecyclerView recyclerView;
    ImageView noMedImage;
    View view;
    SharedPreferences preferences;
    public static final String SHARED_PER = "SHAREDfILE";
    public static final String USER_EMAIL = "USER_EMAIL";

    MedicationMainAdapter medicationMainAdapter;
    String email;

    public MedicationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medicationPresenter = new MedicationFragmentPresenter(this.getContext(), this, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medications, container, false);
        preferences = getActivity().getSharedPreferences(SHARED_PER, Context.MODE_PRIVATE);
        email=preferences.getString(USER_EMAIL,"null");

        showProgressDialod();
        noMedImage = (ImageView) view.findViewById(R.id.medicationsNoMedYet);
        recyclerView = view.findViewById(R.id.medicationRecyclerView);
        this.view = view;
        return view;
    }
    public void showProgressDialod(){
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
    }

    private void initRecyclerView(View view, boolean active, boolean inActive){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        medicationMainAdapter = new MedicationMainAdapter(this.getContext(), this);
        recyclerView.setAdapter(medicationMainAdapter);
    }

    @Override
    public void setInsideRecyclerView(MedicationMainAdapter.MedicationMainViewHolder holder, int position){
        RecyclerView recyclerView = holder.recyclerView;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MedicationInsideAdapter medicationInsideAdapter;
        medicationInsideAdapter = new MedicationInsideAdapter(this.getContext(), medicationPresenter.getMedicines(position), this);
        recyclerView.setAdapter(medicationInsideAdapter);
    }

    @Override
    public void showMeds(boolean active, boolean inActive) {
        progressDialog.dismiss();
        if(active == true || inActive == true)
            initRecyclerView(view, active, inActive);
        else{
            noMedImage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
            //make text no Medicines
    }

    @Override
    public void updateMed(MedicationPOJO medication) {
        medicationPresenter.updateMed(medication, email);
    }

    @Override
    public void deleteMed(MedicationPOJO medication) {
        medicationPresenter.deleteMed(medication, email);
    }

    @Override
    public void refreshRecyclerView() {
        medicationMainAdapter.notifyDataSetChanged();
    }
}