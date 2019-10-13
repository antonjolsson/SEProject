package com.example.tripplannr.data_access_layer.type_converters;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter {

    @TypeConverter
    public String fromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }

    @TypeConverter
    public LocalDateTime fromString(String localDateTime) {
        return LocalDateTime.parse(localDateTime);
    }

}
