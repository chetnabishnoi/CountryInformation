package com.countryinformation.repository;

import com.countryinformation.model.Country;
import com.countryinformation.network.CountryService;
import com.countryinformation.network.Resource;
import com.countryinformation.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class CountryRepository implements Repository {
    private final CountryService countryService;

    @Inject
    public CountryRepository(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Fetches the list of countries,
     *
     * @return {@link com.countryinformation.network.Resource} ERROR with null data or
     * list of countries wrapped in {@link com.countryinformation.network.Resource} with SUCCESS
     */
    public Flowable<Resource<List<Country>>> getCountries() {
        return countryService.getCountries()
                .onErrorReturn(throwable -> new ArrayList<>())
                .map((Function<List<Country>, Resource<List<Country>>>) countryInfoList -> {
                    if (countryInfoList.size() == 0) {
                        return Resource.error(Constants.EMPTY_LIST_ERROR, null);
                    }
                    return Resource.success(countryInfoList);
                })
                .subscribeOn(Schedulers.io());
    }
}
