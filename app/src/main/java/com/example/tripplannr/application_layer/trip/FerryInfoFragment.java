package com.example.tripplannr.application_layer.trip;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.util.InjectorUtils;
import com.example.tripplannr.databinding.FragmentFerryInfoBinding;
import com.example.tripplannr.domain_layer.FerryInfo;
import com.example.tripplannr.domain_layer.ModeOfTransport;
import com.example.tripplannr.domain_layer.Route;

import java.util.function.Predicate;


public class FerryInfoFragment extends Fragment {

    private FragmentFerryInfoBinding binding;

    private TripResultViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ferry_info, container, false);
        initViewModel();
        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = InjectorUtils.getTripResultViewModel(getContext(), getActivity());
        binding.setFerryInfo(viewModel.getFerryInfo().getValue());

    }
}