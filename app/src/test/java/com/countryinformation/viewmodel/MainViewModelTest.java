package com.countryinformation.viewmodel;

import com.countryinformation.model.CountryInfo;
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
class MainViewModelTest {

    @Mock
    CountryRepository countryRepository;
    private MainViewModel mainViewModel;

    @BeforeEach
    void initEach() {
        initMocks(this);
        mainViewModel = new MainViewModel(countryRepository);
    }

    @Test
    void observeCountryList() throws InterruptedException {
        final List<CountryInfo> countryInfoList = new ArrayList<>();
        countryInfoList.add(new CountryInfo("India", "Delhi", "Asia", 12334, "flag.svg"));

        Resource<List<CountryInfo>> returnedData = Resource.success(countryInfoList);

        LiveDataTestUtil<Resource<List<CountryInfo>>> liveDataTestUtil = new LiveDataTestUtil<>();
        Flowable<Resource<List<CountryInfo>>> returnedValue = Flowable.just(returnedData);
        Mockito.when(countryRepository.getCountries()).thenReturn(returnedValue);

        mainViewModel.getCountries();

        Resource<List<CountryInfo>> observedData = liveDataTestUtil.getValue(mainViewModel.observeCountryList());

        Assertions.assertEquals(returnedData, observedData);

    }

    @Test
    void observeCountryList_error() throws InterruptedException {
        Resource<List<CountryInfo>> returnedData = Resource.error("Error", null);

        LiveDataTestUtil<Resource<List<CountryInfo>>> liveDataTestUtil = new LiveDataTestUtil<>();
        Flowable<Resource<List<CountryInfo>>> returnedValue = Flowable.just(returnedData);
        Mockito.when(countryRepository.getCountries()).thenReturn(returnedValue);

        mainViewModel.getCountries();

        Resource<List<CountryInfo>> observedData = liveDataTestUtil.getValue(mainViewModel.observeCountryList());

        Assertions.assertEquals(returnedData, observedData);

    }

}
