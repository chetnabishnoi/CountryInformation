package com.countryinformation.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.countryinformation.model.CountryInfo;
import com.countryinformation.network.ApiService;
import com.countryinformation.network.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CountryRepository {
    private MediatorLiveData<Resource<List<CountryInfo>>> mObservableCountry;

    @Inject
    CountryRepository(ApiService apiService) {
        mObservableCountry = new MediatorLiveData<>();
        mObservableCountry.setValue(Resource.loading(null));
        final LiveData<Resource<List<CountryInfo>>> source = LiveDataReactiveStreams.fromPublisher(
                apiService.fetchMovies()
                        .onErrorReturn(throwable -> new ArrayList<>())
                        .map((Function<List<CountryInfo>, Resource<List<CountryInfo>>>) countryInfoList -> {
                            if (countryInfoList.size() == 0) {
                                return Resource.error("Could not get the list of countries", null);
                            }
                            return Resource.success(countryInfoList);
                        })
                        .subscribeOn(Schedulers.io())
        );


        mObservableCountry.addSource(source, resource -> {
            mObservableCountry.postValue(resource);
            mObservableCountry.removeSource(source);
        });
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<Resource<List<CountryInfo>>> getCountries() {
        return mObservableCountry;
    }
}
