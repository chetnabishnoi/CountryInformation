package com.countryinformation.network;

import com.countryinformation.model.Country;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface CountryService {

    @GET("rest/v2/all")
    Flowable<List<Country>> getCountries();

}