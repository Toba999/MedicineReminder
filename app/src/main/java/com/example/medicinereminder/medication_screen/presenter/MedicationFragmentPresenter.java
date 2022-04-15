package com.example.medicinereminder.medication_screen.presenter;

import android.content.Context;

import com.example.medicinereminder.medication_screen.view.MedicationFragmentInterface;
import com.example.medicinereminder.services.model.MedicineStore;
import com.example.medicinereminder.services.model.MedicineType;
import com.example.medicinereminder.services.model.Occurance;

import java.util.ArrayList;
import java.util.List;

public class MedicationFragmentPresenter implements MedicationFragmentPresenterInterface {
    List<MedicineStore> medicineStores;
    List<MedicineStore> activeMedicineStores;
    List<MedicineStore> inActiveMedicineStores;
    MedicationFragmentInterface medicationFragmentInterface;

    public MedicationFragmentPresenter(Context context, MedicationFragmentInterface medicationFragmentInterface) {
        this.medicationFragmentInterface = medicationFragmentInterface;
        seperateMedicines();
    }

    @Override
    public List<MedicineStore> getMedicines() {
        medicineStores = new ArrayList<>();
        //int id, String name, String type, DateTime startDate, DateTime endDate, String repetition, int dosesPerDay,
        //                    int bottleAmount, int currentPills, int refillPills, Boolean isActive, List<Dose> doses)
        //fake data
        MedicineStore medicineStore = new MedicineStore();
        medicineStore.setName("Depacken");
        medicineStore.setType(MedicineType.pills.name());
        medicineStore.setActive(true);
        medicineStore.setRepetition(Occurance.once.name());
        medicineStores.add(medicineStore);
        medicineStore = new MedicineStore();
        medicineStore.setName("panadol");
        medicineStore.setType(MedicineType.pills.name());
        medicineStore.setActive(true);
        medicineStore.setRepetition(Occurance.once.name());
        medicineStores.add(medicineStore);
        medicineStore = new MedicineStore();
        medicineStore.setName("Elcarnatine");
        medicineStore.setType(MedicineType.syrup.name());
        medicineStore.setActive(false);
        medicineStore.setRepetition(Occurance.once.name());
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
    public List<MedicineStore> getActiveMedicines(){
        return activeMedicineStores;
    }

    @Override
    public List<MedicineStore> getInactiveMedicines(){
        return inActiveMedicineStores;
    }
    @Override
    public void seperateMedicines(){
        getMedicines();
        activeMedicineStores = new ArrayList<>();
        inActiveMedicineStores = new ArrayList<>();
        for (MedicineStore medicineStore : medicineStores) {
            if (medicineStore.getActive() == true)
                activeMedicineStores.add(medicineStore);
            else
                inActiveMedicineStores.add(medicineStore);
        }
    }
}
