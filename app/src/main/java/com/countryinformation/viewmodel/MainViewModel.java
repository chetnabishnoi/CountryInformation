package com.countryinformation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.countryinformation.model.CountryInfo;
import com.countryinformation.network.Resource;
import com.countryinformation.repository.CountryRepository;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Resource<List<CountryInfo>>> mObservableCountries = new MediatorLiveData<>();
    private CountryRepository countryRepository;

    @Inject
    MainViewModel(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void getCountries() {
        mObservableCountries.setValue(Resource.loading(null));
        LiveData<Resource<List<CountryInfo>>> source = LiveDataReactiveStreams.fromPublisher(countryRepository.getCountries());
        mObservableCountries.addSource(source, resource -> {
            mObservableCountries.postValue(resource);
            mObservableCountries.removeSource(source);
        });
    }

    /**
     * Get the list of countries from the repository and get notified when the data changes.
     */
    public LiveData<Resource<List<CountryInfo>>> observeCountryList() {
        return mObservableCountries;
    }
}
