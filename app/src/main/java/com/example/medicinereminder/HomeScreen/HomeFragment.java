package com.example.medicinereminder.HomeScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicinereminder.HomeScreen.view.MedicineOfDayRecyleAdapter;
import com.example.medicinereminder.R;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MedicineOfDayRecyleAdapter medicineOfDayRecyleAdapter = new MedicineOfDayRecyleAdapter(getContext());
        recyclerView = view.findViewById(R.id.outter_recyleview);
        recyclerView.setAdapter(medicineOfDayRecyleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}