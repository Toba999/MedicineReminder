package com.example.medicinereminder.HomeScreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    String interval;
     MedicinesRecyleViewAdapter(Context context, List<MedicationPOJO> medicines,String interval) {
        this.context = context;
        this.medicines = medicines;
        this.interval = interval;
    }

    @NonNull
    @Override
    public MedicinesRecyleViewAdapter.MedicinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicinesViewHolder(LayoutInflater.from(context).inflate(R.layout.medicine_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicinesRecyleViewAdapter.MedicinesViewHolder holder, int position) {

        holder.medName.setText(medicines.get(position).getMedicationName());
        holder.dosTxt.setText(medicines.get(position).getStrength()+""+medicines.get(position).getStrengthType());
        holder.noteTxt.setText(medicines.get(position).getInstruction());

        String finalTime = isAmorPm(medicines.get(position).getTimeSimpleTaken(),interval);
        holder.timeTxt.setText(finalTime);
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
        }
    }
    public String isAmorPm(Map<String,Boolean> data,String interval)
    {

        loop:for(Map.Entry<String,Boolean> time : data.entrySet()) {
            Long timeAbs = simpleTimeToAbs(time.getKey());
            switch (interval) {
                case "Morning":
                    if (timeAbs >= 0 && timeAbs <= 720) {
                        return time.getKey();
//                        break loop;
                    }
                    break;
                case "Afternoon":
                    if (timeAbs > 720 && timeAbs < 960) {
                        return time.getKey();
//                        break loop;
                    }
                    break;
                case "Evening":
                    if (timeAbs >= 960 && timeAbs <= 1439) {
                        return time.getKey();
//                        break loop;
                    }
                    break;
                default:
//                    return "";
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

}
