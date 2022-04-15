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
import com.example.medicinereminder.services.model.MedicineStore;
import com.example.medicinereminder.services.model.MedicineType;

import java.util.List;

public class MedicationInsideAdapter extends RecyclerView.Adapter<MedicationInsideAdapter.MedicationInsideViewHolder> {

    List<MedicineStore> medicineStores;
    Context context;

    public MedicationInsideAdapter(Context context, List<MedicineStore> medicineStores) {
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
        holder.medNameTextView.setText(medicineStores.get(position).getName());
        String occ = medicineStores.get(position).getRepetition();
        holder.medOccuranceTextView.setText(occ);
        String imgName = medicineStores.get(position).getType();
        setImage(holder, imgName);
        holder.medNotificationSwitch.setChecked(medicineStores.get(position).getActive());
        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DisplayMedActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    public void setImage(MedicationInsideViewHolder holder, String imgName){
        if(imgName.equals(MedicineType.drops.name()))
            holder.medImage.setImageResource(R.mipmap.drops);
        else if(imgName.equals(MedicineType.injection.name()))
            holder.medImage.setImageResource(R.mipmap.injections);
        else if(imgName.equals(MedicineType.pills.name()))
            holder.medImage.setImageResource(R.mipmap.pills);
        else if(imgName.equals(MedicineType.syrup.name()))
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
