package com.countryinformation.repository;

import com.countryinformation.model.Country;
import com.countryinformation.network.Resource;

import java.util.List;

import io.reactivex.Flowable;

public interface Repository {

    Flowable<Resource<List<Country>>> getCountries();
}
