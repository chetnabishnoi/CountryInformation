package com.countryinformation.dagger.module;

import com.countryinformation.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {ViewModelModule.class,
                    FragmentBuildersModule.class}
    )
    abstract HomeActivity contributeHomeActivity();
}
