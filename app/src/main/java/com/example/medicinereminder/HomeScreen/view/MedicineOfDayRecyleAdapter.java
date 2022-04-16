package com.example.medicinereminder.HomeScreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicineOfDayRecyleAdapter extends RecyclerView.Adapter<MedicineOfDayRecyleAdapter.MedViewHolder> {

    Context context;
    List<String> dummData ;
    public MedicineOfDayRecyleAdapter(Context context) {
        this.context = context;
        dummData = new ArrayList<>(Arrays.asList("Pandola","plapla","plapla2"));
    }

    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedViewHolder(LayoutInflater.from(context).inflate(R.layout.medicines_section_cell,parent,false));
    }

    @Override
    public int getItemCount() {
        //for now :
        return 7;
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {

        if(position != 0){
            holder.dayTxt.setText("Tomorrow");
        }
        else{
            holder.dayTxt.setText("Today");

        }
        setInnerRecycleView(holder.medsRecyleView,dummData);

    }

    public class MedViewHolder extends RecyclerView.ViewHolder
    {
        TextView dayTxt;
        RecyclerView medsRecyleView;
        public MedViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTxt = itemView.findViewById(R.id.H_day_txt);
            medsRecyleView = itemView.findViewById(R.id.H_meds_recyleview);
        }
    }

    void setInnerRecycleView(RecyclerView recyclerView, List<String> data)
    {
        MedicinesRecyleViewAdapter medicinesRecyleViewAdapter = new MedicinesRecyleViewAdapter(context,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(medicinesRecyleViewAdapter);
    }
}
