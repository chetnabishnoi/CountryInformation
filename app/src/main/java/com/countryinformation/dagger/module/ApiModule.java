package com.countryinformation.dagger.module;


import com.countryinformation.network.CountryService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    CountryService provideApiService(Retrofit retrofit) {
        return retrofit.create(CountryService.class);
    }
}