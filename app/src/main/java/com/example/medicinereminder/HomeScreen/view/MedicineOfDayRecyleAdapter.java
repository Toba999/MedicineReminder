package com.example.medicinereminder.HomeScreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.model.MedicationPOJO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicineOfDayRecyleAdapter extends RecyclerView.Adapter<MedicineOfDayRecyleAdapter.MedViewHolder> {

    Context context;
    List<MedicationPOJO> mediData;
    List<MedicationPOJO> morningMed;
    List<MedicationPOJO> afternoonMed;
    List<MedicationPOJO> eveningMed;
    HomeFragmentViewInterface view;

    public void setDateToday(String dateToday) {
        this.dateToday = dateToday;
    }

    String dateToday;
    public MedicineOfDayRecyleAdapter(Context context,HomeFragmentViewInterface view) {
        this.context = context;
        mediData = new ArrayList<>();
        this.view= view;
        this.dateToday = dateToday;
    }

    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedViewHolder(LayoutInflater.from(context).inflate(R.layout.medicines_section_cell,parent,false));
    }

    @Override
    public int getItemCount() {
        // Three interval : Morning , Afternoon, Evening
        if(morningMed == null && afternoonMed == null && eveningMed == null || morningMed.size() == 0 && afternoonMed.size() == 0 && eveningMed.size() == 0)
        {
            return 0;
        }
        return 3;
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {

        if(position == 0){
            holder.dayTxt.setText("Morning");
            if(morningMed.size() == 0)
            {
                holder.noMedicineImg.setVisibility(View.VISIBLE);
            }
            else{
                holder.noMedicineImg.setVisibility(View.GONE);
            }
            setInnerRecycleView(holder.medsRecyleView,morningMed,"Morning");
        }
        else if(position == 1){
                holder.dayTxt.setText("Afternoon");
            if(afternoonMed.size() == 0)
            {
                holder.noMedicineImg.setVisibility(View.VISIBLE);
            }
            else{
                holder.noMedicineImg.setVisibility(View.GONE);
            }
                setInnerRecycleView(holder.medsRecyleView,afternoonMed,"Afternoon");
        }
        else {
            holder.dayTxt.setText("Evening");
            if(eveningMed.size() == 0)
            {
                holder.noMedicineImg.setVisibility(View.VISIBLE);
            }
            else{
                holder.noMedicineImg.setVisibility(View.GONE);
            }
            setInnerRecycleView(holder.medsRecyleView,eveningMed,"Evening");
        }


    }

    public class MedViewHolder extends RecyclerView.ViewHolder
    {
        TextView dayTxt;
        RecyclerView medsRecyleView;
        ImageView noMedicineImg;
        public MedViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTxt = itemView.findViewById(R.id.H_day_txt);
            medsRecyleView = itemView.findViewById(R.id.H_meds_recyleview);
            noMedicineImg = itemView.findViewById(R.id.no_medicine_img);
        }
    }

    void setInnerRecycleView(RecyclerView recyclerView, List<MedicationPOJO> data,String interval)
    {
        MedicinesRecyleViewAdapter medicinesRecyleViewAdapter = new MedicinesRecyleViewAdapter(context,data,interval,view,dateToday);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(medicinesRecyleViewAdapter);
    }
    public void getData(List<MedicationPOJO> morningMed,List<MedicationPOJO> afternoonMed,List<MedicationPOJO> eveningMed)
    {
           this.morningMed   = morningMed;
           this.afternoonMed = afternoonMed;
           this.eveningMed   = eveningMed;
    }
}
