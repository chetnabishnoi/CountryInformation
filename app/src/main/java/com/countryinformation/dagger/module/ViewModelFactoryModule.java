package com.countryinformation.dagger.module;


import androidx.lifecycle.ViewModelProvider;

import com.countryinformation.utils.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);

}
