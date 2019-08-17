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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.RequestBuilder;
import com.countryinformation.R;
import com.countryinformation.glide.GlideApp;
import com.countryinformation.glide.SvgSoftwareLayerSetter;
import com.countryinformation.model.CountryInfo;

import java.text.DecimalFormat;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private static final String ARG_COUNTRY_DETAIL = "COUNTRY_DETAIL";
    private CountryInfo countryInfo;

    /**
     * Creates detail fragment for specific country
     */
    public static DetailFragment createInstance(CountryInfo countryInfo) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COUNTRY_DETAIL, countryInfo);
        fragment.setArguments(args);
        return fragment;
    }

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
        TextView capital = view.findViewById(R.id.capital);
        TextView region = view.findViewById(R.id.region);
        TextView population = view.findViewById(R.id.population);

        countryName.setText(countryInfo.getName());
        capital.setText(countryInfo.getCapital());
        region.setText(countryInfo.getRegion());
        DecimalFormat formatter = new DecimalFormat("##,###,###");
        String populationValue = formatter.format(countryInfo.getPopulation());
        population.setText(populationValue);
        RequestBuilder<PictureDrawable> requestBuilder = initGlide();
        requestBuilder.load(countryInfo.getFlag()).into((ImageView) view.findViewById(R.id.image_flag));

        setupToolbar(view.findViewById(R.id.toolbar));
    }

    private void setupToolbar(Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.detail_fragment_title));
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    private RequestBuilder<PictureDrawable> initGlide() {
        return GlideApp.with(this)
                .as(PictureDrawable.class)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter());
    }

}
