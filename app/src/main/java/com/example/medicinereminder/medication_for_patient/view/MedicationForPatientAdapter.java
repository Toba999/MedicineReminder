package com.example.medicinereminder.medication_for_patient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.medicinereminder.R;

public class MedicationForPatientAdapter extends RecyclerView.Adapter<MedicationForPatientAdapter.MedicationForPatientViewHolder> {

    Context context;

    public MedicationForPatientAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MedicationForPatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicationForPatientViewHolder(LayoutInflater.from(context).inflate(R.layout.patient_medication_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationForPatientViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MedicationForPatientViewHolder extends ViewHolder{
        TextView medName;
        TextView medStartTime;
        TextView medStartDate;
        TextView medEndDate;
        ImageView medImage;
        ImageView medIsTakenImage;

        View view;

        public MedicationForPatientViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            medName = view.findViewById(R.id.patientMedicationNameTextView);
            medStartTime = view.findViewById(R.id.patientMedicationStartTimeTextView);
            medStartDate = view.findViewById(R.id.patientMedicationStartDateTextView);
            medEndDate = view.findViewById(R.id.patientMedicationEndDateTextView);
            medImage = view.findViewById(R.id.patientMedicationRowImageView);
            medIsTakenImage = view.findViewById(R.id.patientMedicationIsTaken);
        }
    }
}
