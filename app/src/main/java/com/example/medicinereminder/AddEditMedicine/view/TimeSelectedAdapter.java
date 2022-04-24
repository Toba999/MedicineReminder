package com.example.medicinereminder.AddEditMedicine.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.databinding.AddEditTimeCellBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSelectedAdapter extends RecyclerView.Adapter<TimeSelectedAdapter.ViewHolder> {
    private int dosePerDay = 0;
    private Context context;
    private Map<String, Boolean> timeMap;
    private List<String> timeAbs=new ArrayList<>();
    public Map<String, Boolean> getTimeMap() {
        return timeMap;
    }
    public List<String> getTimeList() {
        return timeAbs;
    }


    public void setTimeAndDose(Map<String, Boolean> map){
        timeMap = map;
    }

    public TimeSelectedAdapter(int n ,  Context context, Map<String,Boolean> map) {
        dosePerDay=n;
        this.context = context;
        if(map==null)
            timeMap = new HashMap<>();
        else
            timeMap = map;
    }
    public void setDosePerDay(int n) {
        if(AddEditMedActivity.isAdd){
            timeMap.clear();
            dosePerDay = n;
        }else{
            if (n != timeMap.size()) {
                timeMap.clear();
            }
            dosePerDay=n;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeSelectedAdapter.ViewHolder(AddEditTimeCellBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSelectedAdapter.ViewHolder holder, int position) {
        if(timeMap.isEmpty()) {
            holder.binding.etTimeSelcted.setText("Choose Time");
        }else{

            holder.result= (String) timeMap.keySet().toArray()[position];
            holder.binding.etTimeSelcted.setText(holder.result);
        }

        holder.binding.btnSelectTime.setOnClickListener(v -> {
            final Calendar myCalender = Calendar.getInstance();
            int hour = myCalender.get(Calendar.HOUR_OF_DAY);
            int minute = myCalender.get(Calendar.MINUTE);
            TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                String format;

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    long hourAbs=hourOfDay*60*60000L;
                    long minAbs=minute* 60000L;
                    Log.i("onTimeSet", hourAbs+" "+minAbs);


                    if (!holder.result.isEmpty())
                        timeMap.remove(holder.result);

                    if (view.isShown()) {
                        myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalender.set(Calendar.MINUTE, minute);
                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            format = "AM";
                        } else if (hourOfDay == 12) {
                            format = "PM";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }
                        holder.result = hourOfDay + ":" + minute + " " + format;
                        timeMap.put(holder.result, false);
                        timeAbs.add((hourAbs+minAbs)+"");
                        holder.binding.etTimeSelcted.setText(holder.result);
                    }
                }
            };
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    myTimeListener, hour, minute, false);
            timePickerDialog.setTitle("Choose time");
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();
        });


    }

    @Override
    public int getItemCount() {
        return dosePerDay;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AddEditTimeCellBinding binding;
        String result;
        public ViewHolder(@NonNull AddEditTimeCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            result="";
        }
    }
}

