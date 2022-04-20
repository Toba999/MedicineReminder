package com.example.medicinereminder.medication_screen.view;

public interface MedicationFragmentInterface {
    public void setInsideRecyclerView(MedicationMainAdapter.MedicationMainViewHolder holder, int position);
    public void showMeds(boolean active, boolean inActive);
}
