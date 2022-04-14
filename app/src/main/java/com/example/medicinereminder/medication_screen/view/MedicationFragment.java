package com.example.medicinereminder.medication_screen.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicinereminder.R;
import com.example.medicinereminder.medication_screen.presenter.MedicationFragmentPresenter;
import com.example.medicinereminder.medication_screen.presenter.MedicationFragmentPresenterInterface;
import com.example.medicinereminder.services.model.Medicine;

import java.util.List;

public class MedicationFragment extends Fragment implements MedicationFragmentInterface{

    MedicationFragmentPresenterInterface medicationPresenter;

    public MedicationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medicationPresenter = new MedicationFragmentPresenter(this.getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.medicationRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        MedicationMainAdapter medicationMainAdapter = new MedicationMainAdapter(this.getContext(), medicationPresenter.getActiveInactive(), this);
        recyclerView.setAdapter(medicationMainAdapter);
    }

    @Override
    public void setInsideRecyclerView(MedicationMainAdapter.MedicationMainViewHolder holder, int position){
        RecyclerView recyclerView = holder.recyclerView;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MedicationInsideAdapter medicationInsideAdapter;
        if(position == 0)
            medicationInsideAdapter = new MedicationInsideAdapter(this.getContext(), medicationPresenter.getActiveMedicines());
        else
            medicationInsideAdapter = new MedicationInsideAdapter(this.getContext(), medicationPresenter.getInactiveMedicines());
        recyclerView.setAdapter(medicationInsideAdapter);
    }
}