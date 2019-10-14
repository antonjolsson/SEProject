package com.example.tripplannr.data_access_layer.type_converters;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class LocalDateTimeConverter {

    @TypeConverter
    public Calendar fromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);

        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(localDateTime.getYear(), localDateTime.getMonthValue()-1, localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        return c;

    }

    @TypeConverter
    public String fromDate(Calendar date) {
        return date.toString();
    }

}
