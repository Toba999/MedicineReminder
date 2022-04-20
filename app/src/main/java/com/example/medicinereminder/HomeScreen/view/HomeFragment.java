package com.example.medicinereminder.HomeScreen.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.AddEditMedicine.view.AddEditMedActivity;
import com.example.medicinereminder.HomeScreen.view.MedicineOfDayRecyleAdapter;
import com.example.medicinereminder.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton addMedicineBtn;
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
        initView(view);
        initRecycleView(view);
        initCalendarView(view);
    }

    void initView(View view){
        addMedicineBtn = view.findViewById(R.id.addMedicine);
        addMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditMedActivity.class);
                intent.putExtra("isAdd",true);
                startActivity(intent);
            }
        });
    }

    void initRecycleView(View view) {
        MedicineOfDayRecyleAdapter medicineOfDayRecyleAdapter = new MedicineOfDayRecyleAdapter(getContext());
        recyclerView = view.findViewById(R.id.outter_recyleview);
        recyclerView.setAdapter(medicineOfDayRecyleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    void initCalendarView(View view) {
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.myCalendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //do something
            }
        });
    }
}