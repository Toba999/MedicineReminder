package com.example.medicinereminder.patient_screen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private Context context;
    List<String> patients;

    public PatientAdapter(Context context, List<String> patients){
        this.context = context;
        this.patients = patients;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PatientViewHolder(LayoutInflater.from(context).inflate(R.layout.patient_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        holder.emailTextView.setText(patients.get(position));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        View view;

        public PatientViewHolder(@NonNull View view){
            super(view);
            this.view = view;
            emailTextView = view.findViewById(R.id.patientEmail);
        }
    }
}
