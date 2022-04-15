package com.example.medicinereminder.HomeScreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;

import java.util.List;

public class MedicinesRecyleViewAdapter extends RecyclerView.Adapter<MedicinesRecyleViewAdapter.MedicinesViewHolder> {

    Context context;
    //list of medicines Obj
    List<String> medicines;

     MedicinesRecyleViewAdapter(Context context, List<String> medicines) {
        this.context = context;
        this.medicines = medicines;
    }

    @NonNull
    @Override
    public MedicinesRecyleViewAdapter.MedicinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicinesViewHolder(LayoutInflater.from(context).inflate(R.layout.medicine_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicinesRecyleViewAdapter.MedicinesViewHolder holder, int position) {

        holder.medName.setText(medicines.get(position));
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public static class MedicinesViewHolder extends RecyclerView.ViewHolder{

        TextView medName;
        TextView dosTxt;
        TextView noteTxt;
        public MedicinesViewHolder(@NonNull View itemView) {
            super(itemView);
            medName = itemView.findViewById(R.id.H_medicine_name_txt);
            dosTxt  = itemView.findViewById(R.id.H_dos_txt);
            noteTxt = itemView.findViewById(R.id.H_note_txt);
        }
    }
}
