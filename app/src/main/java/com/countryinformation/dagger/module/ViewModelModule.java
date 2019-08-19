package com.countryinformation.dagger.module;


import androidx.lifecycle.ViewModel;

import com.countryinformation.dagger.ViewModelKey;
import com.countryinformation.viewmodel.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public abstract ViewModel bindMainViewModel(HomeViewModel viewModel);
}
