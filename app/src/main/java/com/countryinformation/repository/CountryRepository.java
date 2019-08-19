package com.countryinformation.repository;

import com.countryinformation.model.CountryInfo;
import com.countryinformation.network.CountryService;
import com.countryinformation.network.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class CountryRepository {
    private final CountryService countryService;

    @Inject
    public CountryRepository(CountryService countryService) {
        this.countryService = countryService;
    }

    public Flowable<Resource<List<CountryInfo>>> getCountries() {
        return countryService.getCountries()
                .onErrorReturn(throwable -> new ArrayList<>())
                .map((Function<List<CountryInfo>, Resource<List<CountryInfo>>>) countryInfoList -> {
                    if (countryInfoList.size() == 0) {
                        return Resource.error("Could not get the list of countries", null);
                    }
                    return Resource.success(countryInfoList);
                })
                .subscribeOn(Schedulers.io());
    }
}
