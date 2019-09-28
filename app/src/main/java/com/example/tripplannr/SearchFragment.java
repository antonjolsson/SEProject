package com.example.tripplannr;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

public class SearchFragment extends Fragment {

    private EditText toTextField, fromTextField;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //toTextField = Objects.requireNonNull(getView()).findViewById(R.id.toText);
        //fromTextField = Objects.requireNonNull(getView()).findViewById(R.id.fromText);
        TripViewModel model =
                ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripViewModel.class);
        model.getOrigin().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                fromTextField.setText(tripLocation.name);
            }
        });
        model.getDestination().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                toTextField.setText(tripLocation.name);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_frag, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.search_frag, container, false);
        toTextField = Objects.requireNonNull(view).findViewById(R.id.toText);
        fromTextField = Objects.requireNonNull(view).findViewById(R.id.fromText);
        return view;
    }

}
