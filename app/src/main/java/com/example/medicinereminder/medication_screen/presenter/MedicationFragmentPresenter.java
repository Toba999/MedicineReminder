package com.example.medicinereminder.medication_screen.presenter;

import android.content.Context;

import com.example.medicinereminder.medication_screen.view.MedicationFragmentInterface;
import com.example.medicinereminder.services.model.MedicationPOJO;

import java.util.ArrayList;
import java.util.List;

public class MedicationFragmentPresenter implements MedicationFragmentPresenterInterface {
    List<MedicationPOJO> medicineStores;
    List<MedicationPOJO> activeMedicineStores;
    List<MedicationPOJO> inActiveMedicineStores;
    MedicationFragmentInterface medicationFragmentInterface;

    public MedicationFragmentPresenter(Context context, MedicationFragmentInterface medicationFragmentInterface) {
        this.medicationFragmentInterface = medicationFragmentInterface;
        seperateMedicines();
    }

    @Override
    public List<MedicationPOJO> getMedicines() {
        medicineStores = new ArrayList<>();
        //int id, String name, String type, DateTime startDate, DateTime endDate, String repetition, int dosesPerDay,
        //                    int bottleAmount, int currentPills, int refillPills, Boolean isActive, List<Dose> doses)
        //fake data
        MedicationPOJO medicineStore = new MedicationPOJO();
        medicineStore.setMedicationName("Depacken");
        medicineStore.setMedicationType("pills");
        medicineStore.setActive(true);
        medicineStore.setTakeTimePerDay("once");
        medicineStores.add(medicineStore);
        medicineStore = new MedicationPOJO();
        medicineStore.setMedicationName("panadol");
        medicineStore.setMedicationType("pills");
        medicineStore.setActive(true);
        medicineStore.setTakeTimePerDay("once");
        medicineStores.add(medicineStore);
        medicineStore = new MedicationPOJO();
        medicineStore.setMedicationName("Elcarnatine");
        medicineStore.setMedicationType("syrup");
        medicineStore.setActive(false);
        medicineStore.setTakeTimePerDay("once");
        medicineStores.add(medicineStore);
        //----------------------------------------
        return medicineStores;
    }

    @Override
    public List<String> getActiveInactive() {
        List<String> activeInactive = new ArrayList<>();
        activeInactive.add("Active Meds");
        activeInactive.add("Inactive Meds");
        return activeInactive;
    }

    @Override
    public List<MedicationPOJO> getActiveMedicines(){
        return activeMedicineStores;
    }

    @Override
    public List<MedicationPOJO> getInactiveMedicines(){
        return inActiveMedicineStores;
    }
    @Override
    public void seperateMedicines(){
        getMedicines();
        activeMedicineStores = new ArrayList<>();
        inActiveMedicineStores = new ArrayList<>();
        for (MedicationPOJO medicineStore : medicineStores) {
            if (medicineStore.getIsActive() == true)
                activeMedicineStores.add(medicineStore);
            else
                inActiveMedicineStores.add(medicineStore);
        }
    }
}
