package com.example.medicinereminder.medication_screen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.services.model.Medicine;
import com.example.medicinereminder.services.model.MedicineType;

import java.util.List;

public class MedicationInsideAdapter extends RecyclerView.Adapter<MedicationInsideAdapter.MedicationInsideViewHolder> {

    List<Medicine> medicines;
    Context context;

    public MedicationInsideAdapter(Context context, List<Medicine> medicines) {
        this.medicines = medicines;
        this.context = context;
    }

    @NonNull
    @Override
    public MedicationInsideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicationInsideViewHolder(LayoutInflater.from(context).inflate(R.layout.medication_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationInsideViewHolder holder, int position) {
        holder.medNameTextView.setText(medicines.get(position).getName());
        String occ = medicines.get(position).getRepetition();
        holder.medOccuranceTextView.setText(occ);
        String imgName = medicines.get(position).getType();
        setImage(holder, imgName);
        holder.medNotificationSwitch.setChecked(medicines.get(position).getActive());
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
        if(medicines != null)
            return medicines.size();
        else
            return 0;
    }

    public class MedicationInsideViewHolder extends RecyclerView.ViewHolder {
        ImageView medImage;
        TextView medNameTextView;
        TextView medOccuranceTextView;
        Switch medNotificationSwitch;

        public MedicationInsideViewHolder(@NonNull View view) {
            super(view);
            medImage = view.findViewById(R.id.medicationRowImageView);
            medNameTextView = view.findViewById(R.id.medicationNameTextView);
            medOccuranceTextView = view.findViewById(R.id.medicationoccuranceTextView);
            medNotificationSwitch = view.findViewById(R.id.medicationSwitchNotification);
        }
    }
}
