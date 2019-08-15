package com.countryinformation;

import android.os.Bundle;

import com.countryinformation.fragments.DetailFragment;
import com.countryinformation.fragments.HomeFragment;
import com.countryinformation.model.CountryInfo;

import dagger.android.support.DaggerAppCompatActivity;

public class HomeActivity extends DaggerAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            HomeFragment fragment = new HomeFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, HomeFragment.TAG).commit();
        }
    }

    /**
     * Shows the product detail fragment
     */
    public void show(CountryInfo countryInfo) {
        DetailFragment productFragment = DetailFragment.createInstance(countryInfo);

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .addToBackStack("detail")
                .add(R.id.fragment_container, productFragment, null)
                .commit();
    }
}