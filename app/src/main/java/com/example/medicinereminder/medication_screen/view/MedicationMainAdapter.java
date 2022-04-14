package com.example.medicinereminder.medication_screen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.R;
import com.example.medicinereminder.medication_screen.presenter.MedicationFragmentPresenter;
import com.example.medicinereminder.medication_screen.presenter.MedicationFragmentPresenterInterface;

import java.util.List;

public class MedicationMainAdapter extends RecyclerView.Adapter<MedicationMainAdapter.MedicationMainViewHolder> {

    private Context context;
    List<String> activeInActive;
    MedicationFragmentInterface medicationFragmentInterface;

    public MedicationMainAdapter(Context context, List<String> activeInActive, MedicationFragmentInterface medicationFragmentInterface) {
        this.context = context;
        this.activeInActive = activeInActive;
        this.medicationFragmentInterface = medicationFragmentInterface;
    }

    @NonNull
    @Override
    public MedicationMainAdapter.MedicationMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicationMainViewHolder(LayoutInflater.from(context).inflate(R.layout.medication_inactive_active_row, parent, false));
    }

    @Override
    public int getItemCount() {
        return activeInActive.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationMainViewHolder holder, int position) {
        holder.activeInActiveTextView.setText(activeInActive.get(position).toString());
        medicationFragmentInterface.setInsideRecyclerView(holder, position);
    }

    public class MedicationMainViewHolder extends RecyclerView.ViewHolder {
        TextView activeInActiveTextView;
        RecyclerView recyclerView;
        public MedicationMainViewHolder(@NonNull View view) {
            super(view);
            activeInActiveTextView = view.findViewById(R.id.inactiveActiveTextView);
            recyclerView = view.findViewById(R.id.inactiveActiveRecyclerView);
        }
    }
}
