package com.example.medicinereminder.services.model;

import android.hardware.lights.LightState;

import com.google.type.DateTime;

import java.util.List;

public class MedicineStore {
    private int id;
    private String name;
    private String type;
    private DateTime startDate;
    private DateTime endDate;
    private String repetition; //once-daily-weekly-certainDays(ex.saturday, monday, wednesday)
    private int dosesPerDay; //for example 2 in day
    private int bottleAmount;
    private int currentPills;
    private int refillPills;
    private Boolean isActive;
    private DateTime dateTime;
    private Boolean isTaken;
    private Boolean isSnoozed;
    private List<String> allDosesDates;
    private List<String> timesPerDay;

    public MedicineStore() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public int getDosesPerDay() {
        return dosesPerDay;
    }

    public void setDosesPerDay(int dosesPerDay) {
        this.dosesPerDay = dosesPerDay;
    }


    public int getBottleAmount() {
        return bottleAmount;
    }

    public void setBottleAmount(int bottleAmount) {
        this.bottleAmount = bottleAmount;
    }

    public int getCurrentPills() {
        return currentPills;
    }

    public void setCurrentPills(int currentPills) {
        this.currentPills = currentPills;
    }

    public int getRefillPills() {
        return refillPills;
    }

    public void setRefillPills(int refillPills) {
        this.refillPills = refillPills;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
