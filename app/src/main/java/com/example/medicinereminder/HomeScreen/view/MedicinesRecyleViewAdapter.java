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
import com.example.medicinereminder.model.TimeUtility;

import java.sql.Time;
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
        String timeAbs = simpleTimeToAbs(finalTime).toString();

        for(Map.Entry<String,Boolean> time:medicines.get(position).getDateTimeAbsTaken().entrySet())
        {
            Long todayAbs = TimeUtility.getDateInMilli(dateToday);
            String timeAbsKey = time.getKey();
            Long timeDataAbs = Long.parseLong(timeAbsKey);
            if( (timeDataAbs - todayAbs) == simpleTimeToAbs(finalTime)*60000 )
            {
                if(medicines.get(position).getDateTimeAbsTaken().get(timeAbsKey) == true)
                {
                    holder.checkImg.setImageResource(R.mipmap.chech_icon_foreground);
                    isTaken = true;

                }
            }

//
        }
        if(!isTaken && medicines.get(position).getLeftNumber() > 0)
        {
            setActionOnMedicineCell(holder.layout,medicines.get(position),interval,finalTime);
        }
    }
    public void setActionOnMedicineCell(ConstraintLayout layout,MedicationPOJO med,String interval,String timeStr)
    {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMedicineDialog(med,interval,timeStr);
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
        String  format = part2[1];
        Long res ;
        if(hours == 12 && format.equals("AM"))
        {
            hours = 0L;
        }
        else if(hours == 12 && format.equals("PM"))
        {
            hours = 12L;
        }
        else if(hours !=  12 && format.equals("PM"))
        {
            hours += 12;
        }
        res = hours*60 + mins;
        return res;

    }


    void openMedicineDialog(MedicationPOJO medicine,String interval,String timeStr){
        view.showMedicineDialog(medicine,interval,timeStr);
    }
    private HomeFragmentViewInterface getView()
    {
        return view;
    }

}
