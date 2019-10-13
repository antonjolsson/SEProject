package com.example.tripplannr.data_access_layer.type_converters;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter {

    @TypeConverter
    public LocalDateTime fromString(String date) {
        return LocalDateTime.parse(date);
    }

    @TypeConverter
    public String fromDate(LocalDateTime date) {
        return date.toString();
    }

}
