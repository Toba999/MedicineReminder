package com.example.medicinereminder.localdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.medicinereminder.model.Converters;
import com.example.medicinereminder.model.MedicationPOJO;


@TypeConverters(Converters.class)
@Database(entities = {MedicationPOJO.class},version =1)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase instance = null;
    public static final String DATABASE_NAME = "MyDataBase";

    public static  synchronized MyDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDataBase.class, DATABASE_NAME).build();
        }
        return instance;
    }

    public abstract DAO getDao();


}
