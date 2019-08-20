package com.countryinformation.dagger.module;

import com.countryinformation.detail.DetailFragment;
import com.countryinformation.home.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment();
}
