package com.example.tripplannr.application_layer.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.util.InjectorUtils;
import com.example.tripplannr.databinding.FragmentFerryInfoBinding;


public class FerryInfoFragment extends Fragment {

    private FragmentFerryInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ferry_info, container, false);
        initViewModel();
        return binding.getRoot();
    }

    private void initViewModel() {
        TripResultViewModel viewModel = InjectorUtils.getTripResultViewModel(getContext(), getActivity());
        binding.setFerryInfo(viewModel.getFerryInfo().getValue());

    }
}