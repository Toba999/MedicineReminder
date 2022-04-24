package com.example.medicinereminder.HomeScreen.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.model.MedicationPOJO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MedicinesRecyleViewAdapter extends RecyclerView.Adapter<MedicinesRecyleViewAdapter.MedicinesViewHolder> {

    Context context;
    //list of medicines Obj
    List<MedicationPOJO> medicines;

    List<MedicationPOJO> morningMed;
    List<MedicationPOJO> afternoonMed;
    List<MedicationPOJO> eveningMed;
    HomeFragmentViewInterface view;
    String dateToday;
    String interval;
    Boolean isTaken = false;
     MedicinesRecyleViewAdapter(Context context, List<MedicationPOJO> medicines,String interval,HomeFragmentViewInterface view,String dateToday) {
        this.context = context;
        this.medicines = medicines;
        this.interval = interval;
        this.view = view;
        this.dateToday = dateToday;
    }

    @NonNull
    @Override
    public MedicinesRecyleViewAdapter.MedicinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicinesViewHolder(LayoutInflater.from(context).inflate(R.layout.medicine_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicinesRecyleViewAdapter.MedicinesViewHolder holder, int position) {
        isTaken = false;
        holder.medName.setText(medicines.get(position).getMedicationName());
        holder.dosTxt.setText(medicines.get(position).getStrength()+""+medicines.get(position).getStrengthType());
        holder.noteTxt.setText(medicines.get(position).getInstruction());
        String finalTime = isAmorPm(medicines.get(position).getTimeSimpleTaken(),interval);
        holder.timeTxt.setText(finalTime);
        if(medicines.get(position).getTimeSimpleTaken().get(finalTime) == true && medicines.get(position).getDateTimeSimpleTaken().get(dateToday) == true)
        {
            holder.checkImg.setImageResource(R.mipmap.chech_icon_foreground);
            isTaken = true;
        }
        if(!isTaken)
        {
            setActionOnMedicineCell(holder.layout,medicines.get(position),finalTime,interval);
        }
    }
    public void setActionOnMedicineCell(ConstraintLayout layout,MedicationPOJO med,String time,String interval)
    {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMedicineDialog(med,time,interval);
            }
        });
    }

    @Override
    public int getItemCount() {
         if(medicines == null)
         {
             return 0;
         }
         else
            return medicines.size();
    }

    public static class MedicinesViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout layout;
        TextView medName;
        TextView dosTxt;
        TextView noteTxt;
        TextView timeTxt;
        ImageView checkImg;

        public MedicinesViewHolder(@NonNull View itemView) {
            super(itemView);
            medName = itemView.findViewById(R.id.H_medicine_name_txt);
            dosTxt  = itemView.findViewById(R.id.H_dos_txt);
            noteTxt = itemView.findViewById(R.id.H_note_txt);
            timeTxt = itemView.findViewById(R.id.time_txt);
            checkImg = itemView.findViewById(R.id.check_icon);
            layout = itemView.findViewById(R.id.medicine_cell_id);
        }
    }
    public String isAmorPm(Map<String,Boolean> data,String interval)
    {

        for(Map.Entry<String,Boolean> time : data.entrySet()) {
            Long timeAbs = simpleTimeToAbs(time.getKey());
            switch (interval) {
                case "Morning":
                    if (timeAbs >= 0 && timeAbs <= 720) {
                        return time.getKey();
                    }
                    break;
                case "Afternoon":
                    if (timeAbs > 720 && timeAbs < 960) {
                        return time.getKey();
                    }
                    break;
                case "Evening":
                    if (timeAbs >= 960 && timeAbs <= 1439) {
                        return time.getKey();
                    }
                    break;
                default:
                    break ;
            }
        }
        return "";
    }
    public Long simpleTimeToAbs(String time)
    {
        String[] times = time.split(":");
        String part1 = times[0]; // 004
        String[] part2 = times[1].split(" ");
        Long hours = Long.parseLong(part1);
        Long mins = Long.parseLong(part2[0]);
        Long res = hours*60 + mins;
        if(part2[1].equals("PM"))
        {
            res += (12*60);
        }
        return res;

    }


    void openMedicineDialog(MedicationPOJO medicine,String timeStr,String interval){
        view.showMedicineDialog(medicine,timeStr,interval);
    }
    private HomeFragmentViewInterface getView()
    {
        return view;
    }

}
