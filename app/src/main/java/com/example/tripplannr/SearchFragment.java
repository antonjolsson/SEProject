package com.example.tripplannr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

public class SearchFragment extends Fragment {

    private EditText toTextField, fromTextField;
    private String name;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TripViewModel model =
                ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripViewModel.class);
        model.getOrigin().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                name = formatLocationName(tripLocation.name);
                fromTextField.setText(name);
            }
        });
        model.getDestination().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                name = formatLocationName(tripLocation.name);
                toTextField.setText(name);
            }
        });
    }

    private String formatLocationName(String name) {
        return name.replaceAll(",(\\s\\d+)+", ",").
                replaceAll(",(\\s\\D+)+,(\\s\\D+)+", ",$1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_frag, container, false);

        toTextField = Objects.requireNonNull(view).findViewById(R.id.toText);
        fromTextField = Objects.requireNonNull(view).findViewById(R.id.fromText);
        return view;
    }

}
