package com.countryinformation.dagger.module;


import androidx.lifecycle.ViewModel;

import com.countryinformation.dagger.ViewModelKey;
import com.countryinformation.viewmodel.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
