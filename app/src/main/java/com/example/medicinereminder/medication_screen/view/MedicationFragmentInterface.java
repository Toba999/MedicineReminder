package com.example.medicinereminder.medication_screen.view;

import com.example.medicinereminder.model.MedicationPOJO;

public interface MedicationFragmentInterface {
    public void setInsideRecyclerView(MedicationMainAdapter.MedicationMainViewHolder holder, int position);
    public void showMeds(boolean active, boolean inActive);
    public void updateMed(MedicationPOJO medication);
    public void deleteMed(MedicationPOJO medication);
    public void refreshRecyclerView();
}
