package com.countryinformation.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.countryinformation.R;
import com.countryinformation.detail.DetailFragment;
import com.countryinformation.model.Country;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment, HomeFragment.TAG).commit();
        }
    }

    /**
     * Shows the country detail homeFragment
     */
    public void show(Country country) {
        DetailFragment detailFragment = DetailFragment.createInstance(country);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(HomeFragment.TAG);
        FragmentTransaction transaction = supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .addToBackStack("detail")
                .add(R.id.fragment_container, detailFragment, null);
        if (fragmentByTag != null) {
            transaction.hide(fragmentByTag);
        }
        transaction.commit();
    }
}