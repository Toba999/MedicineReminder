package com.example.medicinereminder.medication_screen.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.DisplayMedicine.DisplayMedActivity;
import com.example.medicinereminder.R;
import com.example.medicinereminder.model.MedicationPOJO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class MedicationInsideAdapter extends RecyclerView.Adapter<MedicationInsideAdapter.MedicationInsideViewHolder> {

    List<MedicationPOJO> medicineStores;
    Context context;

    public MedicationInsideAdapter(Context context, List<MedicationPOJO> medicineStores) {
        this.medicineStores = medicineStores;
        this.context = context;
    }

    @NonNull
    @Override
    public MedicationInsideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicationInsideViewHolder(LayoutInflater.from(context).inflate(R.layout.medication_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationInsideViewHolder holder, int position) {
        holder.medNameTextView.setText(medicineStores.get(position).getMedicationName());
        String occ = medicineStores.get(position).getTakeTimePerDay();
        holder.medOccuranceTextView.setText(occ);
        String imgName = medicineStores.get(position).getMedicationType();
        setImage(holder, imgName);
        holder.medNotificationSwitch.setChecked(medicineStores.get(position).getIsActive());
        holder.view.setOnClickListener(v -> {
            if(medicineStores.get(position).getIsActive() != false){
                Intent intent = new Intent(v.getContext(), DisplayMedActivity.class);
                intent.putExtra("med", (Serializable) medicineStores.get(position));
                v.getContext().startActivity(intent);
            }
        });
        holder.medNotificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(medicineStores.get(position).getEndDate() > LocalDateTime.now().getLong()){
//                    medicineStores.get(position).setActive(!medicineStores.get(position).getIsActive());
//
//                }
            }
        });
    }

    public void setImage(MedicationInsideViewHolder holder, String imgName){
        if(imgName.equals("drops"))
            holder.medImage.setImageResource(R.mipmap.drops);
        else if(imgName.equals("injections"))
            holder.medImage.setImageResource(R.mipmap.injections);
        else if(imgName.equals("pills"))
            holder.medImage.setImageResource(R.mipmap.pills);
        else if(imgName.equals("syrup"))
            holder.medImage.setImageResource(R.mipmap.syrup);
        else
            holder.medImage.setImageResource(R.mipmap.powder);
    }

    @Override
    public int getItemCount() {
        if(medicineStores != null)
            return medicineStores.size();
        else
            return 0;
    }

    public class MedicationInsideViewHolder extends RecyclerView.ViewHolder {
        ImageView medImage;
        TextView medNameTextView;
        TextView medOccuranceTextView;
        Switch medNotificationSwitch;
        View view;

        public MedicationInsideViewHolder(@NonNull View view) {
            super(view);
            this.view=view;
            medImage = view.findViewById(R.id.medicationRowImageView);
            medNameTextView = view.findViewById(R.id.medicationNameTextView);
            medOccuranceTextView = view.findViewById(R.id.medicationoccuranceTextView);
            medNotificationSwitch = view.findViewById(R.id.medicationSwitchNotification);
        }
    }
}
