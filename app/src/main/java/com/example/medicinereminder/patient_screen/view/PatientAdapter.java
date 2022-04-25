package com.example.medicinereminder.patient_screen.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.medication_for_patient.view.MedicationForPatient;
import com.example.medicinereminder.model.PatientDTO;

import java.io.Serializable;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private Context context;
    List<PatientDTO> patients = null;
    PatientActivityInterface activity;

    public PatientAdapter(Context context, PatientActivityInterface activity){
        this.context = context;
        this.activity = activity;
    }

    public void setPatients(List<PatientDTO> patients){
        this.patients = patients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PatientViewHolder(LayoutInflater.from(context).inflate(R.layout.patient_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        holder.emailTextView.setText(patients.get(position).getEmail());
        holder.nameTextView.setText(patients.get(position).getName());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  patientEmail = patients.get(position).getEmail();
                String trackerEmail = patients.get(position).gettrakerEmail();
                activity.deletePatient(patientEmail,trackerEmail);
                notifyDataSetChanged();
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MedicationForPatient.class);
                intent.putExtra("patient", (Serializable)patients.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(patients != null)
            return patients.size();
        return 0;
    }

    class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        TextView nameTextView;
        ImageButton imageButton;
        View view;

        public PatientViewHolder(@NonNull View view){
            super(view);
            this.view = view;
            emailTextView = view.findViewById(R.id.patientEmail);
            imageButton = view.findViewById(R.id.deletePatientBtn);
            nameTextView = view.findViewById(R.id.patientName);
        }
    }
}
