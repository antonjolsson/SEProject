package com.example.tripplannr.controller;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.TripViewModel;

import java.util.Calendar;
import java.util.Objects;

public class DateTimeFragment extends Fragment {

    private Button nowButton, hourButton, minutesButton, departureButton, arrivalButton;
    private TextView setTextView, cancelTextView;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private TripViewModel model;
    private Calendar desiredTime;
    private boolean timeIsDeparture = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).
                get(TripViewModel.class);
        desiredTime = Calendar.getInstance();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datetime_picker_frag, container, false);

        initControls(view);
        setControlListeners();
        return view;
    }

    private void initControls(View view) {
        initTimePicker(view);
        initDatePicker(view);

        nowButton = Objects.requireNonNull(view).findViewById(R.id.nowButton);
        minutesButton = view.findViewById(R.id.fifteenMinButton);
        hourButton = view.findViewById(R.id.oneHourButton);
        departureButton = view.findViewById(R.id.depButton);
        arrivalButton = view.findViewById(R.id.arrivalButton);

        setTextView = view.findViewById(R.id.setTextView);
        cancelTextView = view.findViewById(R.id.cancelTextView);
    }

    private void initTimePicker(View view) {
        timePicker = Objects.requireNonNull(view).findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                desiredTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                desiredTime.set(Calendar.MINUTE, minute);
            }
        });
    }

    private void initDatePicker(View view) {
        datePicker = view.findViewById(R.id.date_picker);
        datePicker.setMinDate(System.currentTimeMillis());
        datePicker.setMaxDate(getFutureTimeInstance(Calendar.YEAR, 1).getTimeInMillis());
        Calendar rightNow = Calendar.getInstance();
        datePicker.init(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                rightNow.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                        desiredTime.set(year, monthOfYear, dayOfMonth);
                    }
                });
    }

    private void setControlListeners() {
        nowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeInPickers(0);
            }
        });
        minutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeInPickers(15);
            }
        });
        hourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeInPickers(60);
            }
        });
        departureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timeIsDeparture) {
                    swapSelectedTimeButton(departureButton, arrivalButton);
                    timeIsDeparture = true;
                }
            }
        });
        arrivalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeIsDeparture) {
                    swapSelectedTimeButton(arrivalButton, departureButton);
                    timeIsDeparture = false;
                }
            }
        });
        setSetListeners();
        setCancelListeners();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setCancelListeners() {
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desiredTime = Calendar.getInstance();
                model.showMap();
            }
        });
        cancelTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setAlphaLevels(v, event);
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setSetListeners() {
        setTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setTime(desiredTime, timeIsDeparture);
                model.showMap();
            }
        });
        /*setTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setAlphaLevels(v, event);
                return false;
            }
        });*/
    }

    private void setAlphaLevels(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.5f);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1f);
            default:
                v.setAlpha(1f);
        }
    }

    private void swapSelectedTimeButton(Button button1, Button button2) {
        button1.setBackgroundTintList(ColorStateList.valueOf(getResources().
                getColor(R.color.colorPrimary, null)));
        button1.setTextColor(Color.WHITE);
        button2.setBackgroundTintList(ColorStateList.valueOf(getResources().
                getColor(R.color.greyButton, null)));
        button2.setTextColor(getResources().getColor(R.color.black, null));
    }

    private void setTimeInPickers(int minutes) {
        Calendar rightNow = getFutureTimeInstance(Calendar.MINUTE, minutes);

        timePicker.setHour(rightNow.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(rightNow.get(Calendar.MINUTE));

        datePicker.updateDate(rightNow.get(Calendar.YEAR),
                rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DATE));
    }

    private Calendar getFutureTimeInstance(int field, int number) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(field, number);
        return rightNow;
    }
}
