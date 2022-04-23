package com.example.medicinereminder.HomeScreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public MedicineOfDayRecyleAdapter(Context context) {
        this.context = context;
        mediData = new ArrayList<>();
    }

    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedViewHolder(LayoutInflater.from(context).inflate(R.layout.medicines_section_cell,parent,false));
    }

    @Override
    public int getItemCount() {
        // Three interval : Morning , Afternoon, Evening
        return 3;
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {

        if(position == 0){
            holder.dayTxt.setText("Morning");
            setInnerRecycleView(holder.medsRecyleView,morningMed,"Morning");
        }
        else if(position == 1){
                holder.dayTxt.setText("Afternoon");
                setInnerRecycleView(holder.medsRecyleView,afternoonMed,"Afternoon");
        }
        else {
            holder.dayTxt.setText("Evening");
            setInnerRecycleView(holder.medsRecyleView,eveningMed,"Evening");
        }


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

    void setInnerRecycleView(RecyclerView recyclerView, List<MedicationPOJO> data,String interval)
    {
        MedicinesRecyleViewAdapter medicinesRecyleViewAdapter = new MedicinesRecyleViewAdapter(context,data,interval);
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
