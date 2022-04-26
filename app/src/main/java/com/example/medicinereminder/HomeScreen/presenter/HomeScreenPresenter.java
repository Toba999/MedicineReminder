package com.example.medicinereminder.HomeScreen.presenter;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.medicinereminder.HomeScreen.view.HomeFragmentInterface;
import com.example.medicinereminder.model.MedicationPOJO;
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.repository.Repository;
import com.example.medicinereminder.services.network.NetworkDelegate;
import com.example.medicinereminder.workManager.MyPeriodicManager;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HomeScreenPresenter implements HomePresenterInterface, NetworkDelegate {
    
    private HomeFragmentInterface view;
    private Repository repo;
    private Context context;
    List<MedicationPOJO> morningMed;
    List<MedicationPOJO> afternoonMed;
    List<MedicationPOJO> eveningMed;

    public HomeScreenPresenter(HomeFragmentInterface view,Context context) {
        this.view = view;
        repo = Repository.getInstance(this,context);
        this.context = context;
    }

    //HomePresenterInterface Implementation
    @Override
    public void getAllMedicine() {

        view.getData(repo.getAllMedication());
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<MedicationPOJO> filterMedicinesOfCurrentDate(List<MedicationPOJO> allData,String currentData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<MedicationPOJO> todayMed = new ArrayList<>();

        for(MedicationPOJO med : allData)
        {
            for(Map.Entry<String,Boolean> opp : med.getDateTimeSimpleTaken().entrySet())
            {

                LocalDate date = LocalDate.parse(opp.getKey(),formatter);
                LocalDate currentLocal = LocalDate.parse(currentData,formatter);
                if(date.isEqual(currentLocal))
                {
                    todayMed.add(med);
                }
            }
        }
        return todayMed;
    }
    @Override
    public void filterMedicinesOfSameDate(List<MedicationPOJO> medicinesOfToday)
    {
        morningMed = new ArrayList<>();
        afternoonMed = new ArrayList<>();
        eveningMed = new ArrayList<>();
        for(MedicationPOJO med : medicinesOfToday)
        {
            for(Map.Entry<String,Boolean> date:med.getTimeSimpleTaken().entrySet())
            {
                if(isInMorning(date.getKey()))
                {
                    morningMed.add(med);
                }
                else if(isInAfternoon(date.getKey())){
                    afternoonMed.add(med);
                }
                else if(isInEvening(date.getKey()))
                {
                    eveningMed.add(med);
                }
            }
        }
    }

    @Override
    public List<MedicationPOJO> getMorningMed() {
        return morningMed;
    }

    @Override
    public List<MedicationPOJO> getAfternoonMed() {
        return afternoonMed;
    }

    @Override
    public List<MedicationPOJO> getEveningMed() {
        return eveningMed;
    }

    @Override
    public void updateMedStatus(MedicationPOJO med,String interval,String timeStr,String dateStr) {
        Long time = simpleDateTimeToAbs(timeStr,dateStr);
        switch (interval)
        {
            case "Morning":
                for(MedicationPOJO medicine : morningMed)
                {
                    if(med == medicine) {
                        med.getDateTimeAbsTaken().put(time.toString(),true);
                        med.setLeftNumber(med.getLeftNumber() - 1 );
                    }
                }
                break;
            case "Afternoon":
                for(MedicationPOJO medicine : afternoonMed)
                {
                    if(med == medicine) {
                        med.getDateTimeAbsTaken().put(time.toString(),true);
                        med.setLeftNumber(med.getLeftNumber() - 1 );
                    }
                }
                break;
            case "Evening":
                for(MedicationPOJO medicine : eveningMed)
                {
                    if(med == medicine) {
                        med.getDateTimeAbsTaken().put(time.toString(),true);
                        med.setLeftNumber(med.getLeftNumber() - 1 );
                    }
                }
                break;
        }
        setWorkTimer();
        repo.updateTakenMedicine(med);
    }
    private void setWorkTimer() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicManager.class,
                3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("Counter", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);

    }

    private Long simpleDateTimeToAbs(String myTime,String myDate)
    {
        String[] dateDet = myDate.split("-");

        String[] timeWS = myTime.split(" ");
        String[] timeDet = timeWS[0].split(":");
        int hours = Integer.parseInt(timeDet[0]);
        String format = timeWS[1];
        if(hours !=  12 &&format.equals("PM"))
        {
            hours+=12;
        }
        else if(hours == 12 && format.equals("PM"))
        {
            hours = 12;
        }
        else if(hours == 12 && format.equals("AM"))
        {
            hours = 0;
        }


        System.out.println(timeDet[0] + ":"+timeDet[1]);
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, hours);
        date.set(Calendar.MINUTE, Integer.parseInt(timeDet[1]));
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateDet[0]));
        date.set(Calendar.MONTH,Integer.parseInt(dateDet[1]) -1 );
        return date.getTimeInMillis();
    }
    private Long simpleTimeToAbs(String time)
    {
        String[] times = time.split(":");
        String part1 = times[0]; // 004
        String[] part2 = times[1].split(" ");
        Long hours = Long.parseLong(part1);
        Long mins = Long.parseLong(part2[0]);
        String  format = part2[1];
        Long res ;
        if(hours == 12 && format.equals("AM"))
        {
            hours = 0L;
        }
        else if(hours == 12 && format.equals("PM"))
        {
            hours = 12L;
        }
        else if(hours !=  12 && format.equals("PM"))
        {
            hours += 12;
        }
        res = hours*60 + mins;
        return res;

    }
    private void setWorkTimer() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicManager.class,
                3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("Counter", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);

    }

    private Boolean isInEvening(String dateStr)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        try {
            Date start = sdf.parse("4:00 PM");
            Date end = sdf.parse("12:00 AM");
            Date date = sdf.parse(dateStr);

            if (end.before(start)) {
                Calendar mCal = Calendar.getInstance();
                mCal.setTime(end);
                mCal.add(Calendar.DAY_OF_YEAR, 1);
                end.setTime(mCal.getTimeInMillis());
            }

            long sDate = start.getTime();
            long eDate = end.getTime();
            long lDate = date.getTime();
//            System.out.print("\n" + date.toString() + ":");
            if ( lDate >= sDate && lDate < eDate) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    private Boolean isInMorning(String dateStr)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        try {
            Date start = sdf.parse("12:00 AM");
            Date end = sdf.parse("12:00 PM");
            Date date = sdf.parse(dateStr);

            if (end.before(start)) {
                Calendar mCal = Calendar.getInstance();
                mCal.setTime(end);
                mCal.add(Calendar.DAY_OF_YEAR, 1);
                end.setTime(mCal.getTimeInMillis());
            }

            long sDate = start.getTime();
            long eDate = end.getTime();
            long lDate = date.getTime();
//            System.out.print("\n" + date.toString() + ":");
            if ( lDate >= sDate && lDate < eDate) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    private Boolean isInAfternoon(String dateStr)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        try {
            Date start = sdf.parse("12:00 PM");
            Date end = sdf.parse("4:00 PM");
            Date date = sdf.parse(dateStr);

            if (end.before(start)) {
                Calendar mCal = Calendar.getInstance();
                mCal.setTime(end);
                mCal.add(Calendar.DAY_OF_YEAR, 1);
                end.setTime(mCal.getTimeInMillis());
            }

            long sDate = start.getTime();
            long eDate = end.getTime();
            long lDate = date.getTime();
//            System.out.print("\n" + date.toString() + ":");
            if ( lDate >= sDate && lDate < eDate) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //network delegate methods
    @Override
    public void onSuccess() {}

    @Override
    public void onFailure(Task<AuthResult> task) {}

    @Override
    public void onFailure(String errorMessage) {}

    @Override
    public void onSuccessReturn(String userName) {}

    @Override
    public void onSuccessRequest(List<RequestDTO> requestDTOS) {}

    @Override
    public void onSuccessTracker(List<TrackerDTO> trackerDTOS) {}

    @Override
    public void onSuccess(boolean response) {}

    @Override
    public void onSuccessPatient(List<PatientDTO> patientDTOS) {}

    @Override
    public void isUserExist(boolean existance) {}

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {}

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {}
}
