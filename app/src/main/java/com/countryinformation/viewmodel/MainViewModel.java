package com.countryinformation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.countryinformation.model.CountryInfo;
import com.countryinformation.network.ApiService;
import com.countryinformation.network.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private MediatorLiveData<Resource<List<CountryInfo>>> countryData = new MediatorLiveData<>();
    private ApiService apiService;

    @Inject
    public MainViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<Resource<List<CountryInfo>>> observeCountryList() {
        return countryData;
    }

    public void fetchMovies() {
        countryData.setValue(Resource.loading(null));
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


        countryData.addSource(source, resource -> {
            countryData.setValue(resource);
            countryData.removeSource(source);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
