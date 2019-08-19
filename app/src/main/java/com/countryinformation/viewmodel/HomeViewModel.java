package com.countryinformation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.countryinformation.model.Country;
import com.countryinformation.network.Resource;
import com.countryinformation.repository.CountryRepository;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Resource<List<Country>>> mObservableCountries = new MediatorLiveData<>();
    private CountryRepository countryRepository;

    @Inject
    HomeViewModel(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void getCountries() {
        mObservableCountries.setValue(Resource.loading(null));
        LiveData<Resource<List<Country>>> source = LiveDataReactiveStreams.fromPublisher(countryRepository.getCountries());
        mObservableCountries.addSource(source, resource -> {
            mObservableCountries.postValue(resource);
            mObservableCountries.removeSource(source);
        });
    }

    /**
     *
     * @return LiveData for list of countries
     */
    public LiveData<Resource<List<Country>>> observeCountryList() {
        return mObservableCountries;
    }
}
