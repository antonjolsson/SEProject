package com.example.tripplannr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tripplannr.stdanica.R;

import java.util.Objects;

public class SearchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setControlIcons();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search_frag, container, false);
    }

    private void setControlIcons() {
        //TextView toTextView = Objects.requireNonNull(getView()).findViewById(R.id.toText);
        //toTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.swap_vert_24px,
        //        0, 0, 0);
    }

}
