package com.countryinformation.viewmodel;

import com.countryinformation.model.Country;
import com.countryinformation.network.Resource;
import com.countryinformation.repository.CountryRepository;
import com.countryinformation.util.InstantExecutorExtension;
import com.countryinformation.util.LiveDataTestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(InstantExecutorExtension.class)
class HomeViewModelTest {

    @Mock
    CountryRepository countryRepository;
    private HomeViewModel homeViewModel;

    @BeforeEach
    void initEach() {
        initMocks(this);
        homeViewModel = new HomeViewModel(countryRepository);
    }

    @Test
    void observeCountryList() throws InterruptedException {
        final List<Country> countryList = new ArrayList<>();
        countryList.add(new Country("India", "Delhi", "Asia", 12334, "flag.svg"));

        Resource<List<Country>> returnedData = Resource.success(countryList);

        LiveDataTestUtil<Resource<List<Country>>> liveDataTestUtil = new LiveDataTestUtil<>();
        Flowable<Resource<List<Country>>> returnedValue = Flowable.just(returnedData);
        Mockito.when(countryRepository.getCountries()).thenReturn(returnedValue);

        homeViewModel.getCountries();

        Resource<List<Country>> observedData = liveDataTestUtil.getValue(homeViewModel.observeCountryList());

        Assertions.assertEquals(returnedData, observedData);

    }

    @Test
    void observeCountryList_error() throws InterruptedException {
        Resource<List<Country>> returnedData = Resource.error("Error", null);

        LiveDataTestUtil<Resource<List<Country>>> liveDataTestUtil = new LiveDataTestUtil<>();
        Flowable<Resource<List<Country>>> returnedValue = Flowable.just(returnedData);
        Mockito.when(countryRepository.getCountries()).thenReturn(returnedValue);

        homeViewModel.getCountries();

        Resource<List<Country>> observedData = liveDataTestUtil.getValue(homeViewModel.observeCountryList());

        Assertions.assertEquals(returnedData, observedData);

    }

}
