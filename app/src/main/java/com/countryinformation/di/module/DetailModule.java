package com.countryinformation.di.module;

import com.countryinformation.detail.DetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailModule {

    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment();
}
