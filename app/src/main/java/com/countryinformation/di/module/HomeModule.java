package com.countryinformation.di.module;

import androidx.lifecycle.ViewModel;

import com.countryinformation.di.ViewModelKey;
import com.countryinformation.home.HomeFragment;
import com.countryinformation.home.viewmodel.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class HomeModule {

    @ContributesAndroidInjector(modules = ViewModelFactoryModule.class)
    abstract HomeFragment contributeHomeFragment();

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public abstract ViewModel bindMainViewModel(HomeViewModel viewModel);
}
