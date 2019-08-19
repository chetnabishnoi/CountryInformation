package com.countryinformation.fragments;


import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.RequestBuilder;
import com.countryinformation.R;
import com.countryinformation.model.Country;

import java.text.DecimalFormat;

import javax.inject.Inject;

/**
 * This fragment shows detail about the country selected like
 * name, capital, population etc.
 */
public class DetailFragment extends BaseFragment {
    private static final String ARG_COUNTRY_DETAIL = "COUNTRY_DETAIL";
    //For showing the svg pictures
    @Inject
    RequestBuilder<PictureDrawable> glide;
    private Country country;

    /**
     * Creates detail fragment for specific country
     */
    public static DetailFragment createInstance(Country country) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COUNTRY_DETAIL, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            country = arguments.getParcelable(ARG_COUNTRY_DETAIL);
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
        TextView capital = view.findViewById(R.id.capital);
        TextView region = view.findViewById(R.id.region);
        TextView population = view.findViewById(R.id.population);

        countryName.setText(country.getName());
        capital.setText(country.getCapital());
        region.setText(country.getRegion());
        DecimalFormat formatter = new DecimalFormat("##,###,###");
        String populationValue = formatter.format(country.getPopulation());
        population.setText(populationValue);
        glide.load(country.getFlag()).into((ImageView) view.findViewById(R.id.image_flag));

        setupToolbar(view.findViewById(R.id.toolbar));
    }


    private void setupToolbar(Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.detail_fragment_title));
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }
}
