package com.example.medicinereminder.medication_screen.presenter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medicinereminder.medication_screen.view.MedicationFragmentInterface;
import com.example.medicinereminder.services.model.Medicine;
import com.example.medicinereminder.services.model.MedicineType;
import com.example.medicinereminder.services.model.Occurance;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MedicationFragmentPresenter implements MedicationFragmentPresenterInterface {
    List<Medicine> medicines;
    List<Medicine> activeMedicines;
    List<Medicine> inActiveMedicines;
    MedicationFragmentInterface medicationFragmentInterface;

    public MedicationFragmentPresenter(Context context, MedicationFragmentInterface medicationFragmentInterface) {
        this.medicationFragmentInterface = medicationFragmentInterface;
        seperateMedicines();
    }

    @Override
    public List<Medicine> getMedicines() {
        medicines = new ArrayList<>();
        //int id, String name, String type, DateTime startDate, DateTime endDate, String repetition, int dosesPerDay,
        //                    int bottleAmount, int currentPills, int refillPills, Boolean isActive, List<Dose> doses)
        //fake data
        Medicine medicine = new Medicine();
        medicine.setName("Depacken");
        medicine.setType(MedicineType.pills.name());
        medicine.setActive(true);
        medicine.setRepetition(Occurance.once.name());
        medicines.add(medicine);
        medicine = new Medicine();
        medicine.setName("panadol");
        medicine.setType(MedicineType.pills.name());
        medicine.setActive(true);
        medicine.setRepetition(Occurance.once.name());
        medicines.add(medicine);
        medicine = new Medicine();
        medicine.setName("Elcarnatine");
        medicine.setType(MedicineType.syrup.name());
        medicine.setActive(false);
        medicine.setRepetition(Occurance.once.name());
        medicines.add(medicine);
        //----------------------------------------
        return medicines;
    }

    @Override
    public List<String> getActiveInactive() {
        List<String> activeInactive = new ArrayList<>();
        activeInactive.add("Active Meds");
        activeInactive.add("Inactive Meds");
        return activeInactive;
    }

    @Override
    public List<Medicine> getActiveMedicines(){
        return activeMedicines;
    }

    @Override
    public List<Medicine> getInactiveMedicines(){
        return inActiveMedicines;
    }
    @Override
    public void seperateMedicines(){
        getMedicines();
        activeMedicines = new ArrayList<>();
        inActiveMedicines = new ArrayList<>();
        for (Medicine medicine: medicines) {
            if (medicine.getActive() == true)
                activeMedicines.add(medicine);
            else
                inActiveMedicines.add(medicine);
        }
    }
}
