package com.example.tripplannr.application_layer.search;

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
import com.example.tripplannr.application_layer.util.InjectorUtils;

import java.util.Calendar;
import java.util.Objects;

/* Displays fragment for picking date and time for desired trip */

public class DateTimeFragment extends Fragment {

    private Button nowButton, hourButton, minutesButton, departureButton, arrivalButton;
    private TextView setTextView, cancelTextView;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private SearchViewModel model;
    private Calendar desiredTime;
    private boolean timeIsDeparture = true; // Is the specified time time of departure or arrival?

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = InjectorUtils.getSearchViewModel(getContext(), getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datetime_picker_frag, container, false);

        initControls(view);
        setControlListeners();
        desiredTime = Calendar.getInstance();
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
                setTextView.performClick();
            }
        });
        minutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeInPickers(15);
                setTextView.performClick();
            }
        });
        hourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeInPickers(60);
                setTextView.performClick();
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
                desiredTime = model.getDesiredTime().getValue();
                model.showMap();
            }
        });
        cancelTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewUtilities.setAlphaLevels(v, event);
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
        setTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewUtilities.setAlphaLevels(v, event);
                return false;
            }
        });
    }

    private void swapSelectedTimeButton(Button button1, Button button2) {
        button1.setBackgroundTintList(ColorStateList.valueOf(getResources().
                getColor(R.color.colorPrimary, null)));
        button1.setTextColor(Color.WHITE);
        button2.setBackgroundTintList(ColorStateList.valueOf(getResources().
                getColor(R.color.greyButton, null)));
        button2.setTextColor(getResources().getColor(R.color.black, null));
    }

    // Advance the time in pickers by minutes
    private void setTimeInPickers(int minutes) {
        Calendar rightNow = getFutureTimeInstance(Calendar.MINUTE, minutes);

        timePicker.setHour(rightNow.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(rightNow.get(Calendar.MINUTE));

        datePicker.updateDate(rightNow.get(Calendar.YEAR),
                rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DATE));
        desiredTime.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                rightNow.get(Calendar.DATE), rightNow.get(Calendar.HOUR_OF_DAY),
                rightNow.get(Calendar.MINUTE));
    }

    // Get calendar representing current time plus [number] [field (hours, minutes, etc...)]
    private Calendar getFutureTimeInstance(int field, int number) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(field, number);
        return rightNow;
    }
}
