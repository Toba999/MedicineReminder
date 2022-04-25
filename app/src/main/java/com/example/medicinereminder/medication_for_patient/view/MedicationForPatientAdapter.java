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
import com.example.medicinereminder.medication_screen.view.MedicationInsideAdapter;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MedicationForPatientAdapter extends RecyclerView.Adapter<MedicationForPatientAdapter.MedicationForPatientViewHolder> {

    Context context;
    List<MedicationPOJO> medications = null;

    public MedicationForPatientAdapter(Context context){
        this.context = context;
    }

    public void setMedications(List<MedicationPOJO> medications) {
        this.medications = medications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedicationForPatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicationForPatientViewHolder(LayoutInflater.from(context).inflate(R.layout.patient_medication_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationForPatientViewHolder holder, int position) {
        setImage(holder, medications.get(position).getMedicationType());
        holder.medName.setText(medications.get(position).getMedicationName().toString());
        Date date=new Date(medications.get(position).getStartDate());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String dateText = df2.format(date);
        holder.medStartDate.setText(dateText); //
        date=new Date(medications.get(position).getEndDate());
        df2 = new SimpleDateFormat("dd/MM/yyyy");
        dateText = df2.format(date);
        holder.medEndDate.setText(dateText); //
        holder.medStartTime.setText(medications.get(position)
                .getTimeSimpleTaken().keySet().toArray()[0].toString());
        //boolean isTaken = medications.get(position).getTimeSimpleTaken().values().toArray()[0];
//        boolean isTaken = true;
//        if(isTaken){
//            holder.medIsTakenImage.setImageResource(R.mipmap.correct_icon);
//        }
//        else
//            holder.medIsTakenImage.setImageResource(R.mipmap.wrong_icon);
    }

    public void setImage(MedicationForPatientAdapter.MedicationForPatientViewHolder holder,
                         String imgName){
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
        if(medications == null)
            return 0;
        return medications.size();
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
