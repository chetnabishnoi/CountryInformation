package com.countryinformation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.countryinformation.model.CountryInfo;
import com.countryinformation.network.Resource;
import com.countryinformation.repository.CountryRepository;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Resource<List<CountryInfo>>> mObservableCountries;

    @Inject
    MainViewModel(CountryRepository countryRepository) {
        mObservableCountries = new MediatorLiveData<>();
        mObservableCountries.setValue(null);
        LiveData<Resource<List<CountryInfo>>> source = countryRepository.getCountries();
        mObservableCountries.addSource(source, mObservableCountries::setValue);
    }

    public LiveData<Resource<List<CountryInfo>>> getCountries() {
        return mObservableCountries;
    }
}
