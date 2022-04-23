package com.example.medicinereminder.HomeScreen.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.AddEditMedicine.view.AddEditMedActivity;
import com.example.medicinereminder.HomeScreen.presenter.HomePresenterInterface;
import com.example.medicinereminder.HomeScreen.presenter.HomeScreenPresenter;
import com.example.medicinereminder.HomeScreen.view.MedicineOfDayRecyleAdapter;
import com.example.medicinereminder.R;
import com.example.medicinereminder.model.MedicationPOJO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class HomeFragment extends Fragment implements HomeFragmentInterface,HomeFragmentViewInterface{

    HomePresenterInterface presenter;
    RecyclerView recyclerView;
    FloatingActionButton addMedicineBtn;
    String currentData;
    MedicineOfDayRecyleAdapter medicineOfDayRecyleAdapter;
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
        presenter = new HomeScreenPresenter(this,getContext());
        presenter.getAllMedicine();
        DateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        currentData = f.format(Calendar.getInstance().getTime());
        presenter.getAllMedicine();
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
        medicineOfDayRecyleAdapter = new MedicineOfDayRecyleAdapter(getContext());
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
                   DateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

                   // Log.i("date", "onDateSelected: " + f.format(date.getTime()));
                   currentData = f.format(date.getTime());
                   presenter.getAllMedicine();
            }
        });
    }

    @Override
    public String getCurrentData() {
        return currentData;
    }
    public void updateUI(List<MedicationPOJO> morning,List<MedicationPOJO> afternoon,List<MedicationPOJO> evening)
    {
        medicineOfDayRecyleAdapter.getData(morning,afternoon,evening);
        medicineOfDayRecyleAdapter.notifyDataSetChanged();
    }

    @Override
    public void getData(LiveData<List<MedicationPOJO>> medicalList) {
        medicalList.observe(this, new Observer<List<MedicationPOJO>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<MedicationPOJO> medicalList) {
                presenter.filterMedicinesOfSameDate(presenter.filterMedicinesOfCurrentDate(medicalList,currentData));
                updateUI(presenter.getMorningMed(),presenter.getAfternoonMed(),presenter.getEveningMed());
            }
        });
    }


}