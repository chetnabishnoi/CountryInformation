package com.countryinformation.dagger.module;

import com.countryinformation.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {MainViewModelModule.class,
            FragmentBuildersModule.class}
    )
    abstract HomeActivity contributeHomeActivity();
}
