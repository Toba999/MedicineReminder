package com.example.medicinereminder.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.firebase.database.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity(tableName = "medications")
public class MedicationPOJO implements Serializable {


    @PrimaryKey
    @NonNull
    private String id;

    @NotNull
    private String email;



    @NotNull
    private String medicationName;
    private int strength;
    private String strengthType;
    private String medicationType;
    private String instruction;


    private int leftNumber;
    private int leftNumberReminder; // refill reminder
    private int medicineSize;
    private boolean isActive;
    private String medicationReason;

    @NotNull
    private Long startDate;
    @NotNull
    private Long endDate;
    private String takeTimePerDay;
    private String takeTimePerWeek;

    @TypeConverters(Converters.class)
    private Map<String, Boolean> TimeSimpleTaken;

    @TypeConverters(Converters.class)
    private Map<String, Boolean> dateTimeSimpleTaken;

    @TypeConverters(Converters.class)
    private Map<String, Boolean> dateTimeAbsTaken;

    @TypeConverters(Converters.class)
    private List<String> dateTimeAbs;

    public Map<String, Boolean> getTimeSimpleTaken() {
        return TimeSimpleTaken;
    }

    public void setTimeSimpleTaken(Map<String, Boolean> timeSimpleTaken) {
        TimeSimpleTaken = timeSimpleTaken;
    }

    public int getMedicineSize() {
        return medicineSize;
    }

    public void setMedicineSize(int medicineSize) {
        this.medicineSize = medicineSize;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getStrengthType() {
        return strengthType;
    }

    public void setStrengthType(String strengthType) {
        this.strengthType = strengthType;
    }


    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getLeftNumber() {
        return leftNumber;
    }

    public void setLeftNumber(int leftNumber) {
        this.leftNumber = leftNumber;
    }

    public int getLeftNumberReminder() {
        return leftNumberReminder;
    }

    public void setLeftNumberReminder(int leftNumberReminder) {
        this.leftNumberReminder = leftNumberReminder;
    }
    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }


    public Map<String, Boolean> getDateTimeSimpleTaken() {
        return dateTimeSimpleTaken;
    }

    public void setDateTimeSimpleTaken(Map<String, Boolean> dateTimeSimpleTaken) {
        this.dateTimeSimpleTaken = dateTimeSimpleTaken;
    }

    public Map<String, Boolean> getDateTimeAbsTaken() {
        return dateTimeAbsTaken;
    }

    public void setDateTimeAbsTaken(Map<String, Boolean> dateTimeAbsTaken) {
        this.dateTimeAbsTaken = dateTimeAbsTaken;
    }

    public List<String> getDateTimeAbs() {
        return dateTimeAbs;
    }

    public void setDateTimeAbs(List<String> dateTimeAbs) {
        this.dateTimeAbs = dateTimeAbs;
    }

    public String getMedicationReason() {
        return medicationReason;
    }

    public void setMedicationReason(String medicationReason) {
        this.medicationReason = medicationReason;
    }

    public String getTakeTimePerWeek() {
        return takeTimePerWeek;
    }

    public void setTakeTimePerWeek(String takeTimePerWeek) {
        this.takeTimePerWeek = takeTimePerWeek;
    }

    public String getId() {
        return id;
    }



    @NotNull
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(@NotNull String medicationName) {
        this.medicationName = medicationName;
    }



    public String getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(String medicationType) {
        this.medicationType = medicationType;
    }


    @NotNull
    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull Long startDate) {
        this.startDate = startDate;
    }

    @NotNull
    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull Long endDate) {
        this.endDate = endDate;
    }

    public String getTakeTimePerDay() {
        return takeTimePerDay;
    }

    public void setTakeTimePerDay(String takeTimePerDay) {
        this.takeTimePerDay = takeTimePerDay;
    }



    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}


