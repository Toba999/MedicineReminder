package com.example.medicinereminder.services.model;

import com.google.type.DateTime;

public class Dose {
    private DateTime dateTime;
    private Boolean isTaken;
    private Boolean isSnoozed;

    public Dose() {
    }

    public Dose(DateTime dateTime, Boolean isTaken, Boolean isSnoozed) {
        this.dateTime = dateTime;
        this.isTaken = isTaken;
        this.isSnoozed = isSnoozed;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getTaken() {
        return isTaken;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }

    public Boolean getSnoozed() {
        return isSnoozed;
    }

    public void setSnoozed(Boolean snoozed) {
        isSnoozed = snoozed;
    }
}
