package com.countryinformation.dagger.module;

import com.countryinformation.fragments.DetailFragment;
import com.countryinformation.fragments.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment();
}
