package com.example.tripplannr.data_access_layer.type_converters;

import androidx.room.TypeConverter;

import com.example.tripplannr.domain_layer.ModeOfTransport;

public class ModeOfTransportConverter {

    @TypeConverter
    public ModeOfTransport fromString(String mode) {
        return ModeOfTransport.valueOf(mode);
    }

    @TypeConverter
    public String fromMode(ModeOfTransport mode) {
        return mode.toString();
    }

}
