package com.example.tripplannr.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.example.tripplannr.R;

import java.util.Objects;

public class DateTimeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datetime_picker_frag, container, false);
        TimePicker timePicker = Objects.requireNonNull(view).findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        return view;
    }
}
