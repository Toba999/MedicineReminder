package com.example.medicinereminder.services.model;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Converters {

    Gson gson = new Gson();


    @TypeConverter
    public String fromIsTakenListToString(List<String> dateTimeAbs) {
        return gson.toJson(dateTimeAbs);
    }

    @TypeConverter
    public List<String> fromStringToGenre(String dateTimeAbsString) {
        if (dateTimeAbsString == null) {
            return Collections.emptyList();
        } else {
            Type list = new TypeToken<List<String>>() {
            }.getType();
            return gson.fromJson(dateTimeAbsString, list);
        }
    }

    // for hash map
    @TypeConverter
    public String fromHashMapToString(Map<String, Boolean> TimeSimpleTaken) {
        return gson.toJson(TimeSimpleTaken);
    }

    @TypeConverter
    public Map<String, Boolean> fromStringToHashMap(String timeAndDoseString) {
        if (timeAndDoseString == null) {
            return  Collections.<String, Boolean>emptyMap();
        } else {
            Type list = new TypeToken<Map<String, Boolean>>() {
            }.getType();
            return gson.fromJson(timeAndDoseString, list);
        }
    }
}
