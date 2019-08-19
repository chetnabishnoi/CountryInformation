package com.countryinformation;

import android.os.Bundle;

import com.countryinformation.fragments.DetailFragment;
import com.countryinformation.fragments.HomeFragment;
import com.countryinformation.model.CountryInfo;

import dagger.android.support.DaggerAppCompatActivity;

public class HomeActivity extends DaggerAppCompatActivity {

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment, HomeFragment.TAG).commit();
        }
    }

    /**
     * Shows the country detail homeFragment
     */
    public void show(CountryInfo countryInfo) {
        DetailFragment detailFragment = DetailFragment.createInstance(countryInfo);

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .addToBackStack("detail")
                .add(R.id.fragment_container, detailFragment, null)
                .hide(homeFragment)
                .commit();
    }
}