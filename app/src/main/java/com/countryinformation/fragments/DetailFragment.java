package com.countryinformation.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.countryinformation.R;
import com.countryinformation.model.CountryInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    static final String ARG_COUNTRY_DETAIL = "COUNTRY_DETAIL";
    private CountryInfo countryInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            countryInfo = arguments.getParcelable(ARG_COUNTRY_DETAIL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView countryName = view.findViewById(R.id.country_name);
        TextView capital = view.findViewById(R.id.captial);
        TextView region = view.findViewById(R.id.region);
        countryName.setText(countryInfo.getName());
        capital.setText(countryInfo.getCapital());
        region.setText(countryInfo.getRegion());
    }
}
