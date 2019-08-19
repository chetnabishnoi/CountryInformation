package com.countryinformation.repository;

import com.countryinformation.model.CountryInfo;
import com.countryinformation.network.CountryService;
import com.countryinformation.network.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CountryRepositoryTest {

    @Mock
    private CountryService countryService;

    private CountryRepository countryRepository;

    @BeforeEach
    void initEach() {
        initMocks(this);
        countryRepository = new CountryRepository(countryService);
    }

    @Test
    void fetchCountries_returnSuccess() {
        final List<CountryInfo> countryInfoList = new ArrayList<>();
        countryInfoList.add(new CountryInfo("India", "Delhi", "Asia", 12334, "flag.svg"));

        when(countryService.getCountries()).thenReturn(Flowable.just(countryInfoList));

        Resource<List<CountryInfo>> listResource = countryRepository.getCountries().blockingFirst();

        verify(countryService).getCountries();
        verifyNoMoreInteractions(countryService);

        Assertions.assertEquals(Resource.success(countryInfoList), listResource);
    }

    @Test
    void fetchCountries_returnError() {
        when(countryService.getCountries()).thenReturn(Flowable.error(new Throwable()));

        Resource<List<CountryInfo>> listResource = countryRepository.getCountries().blockingFirst();

        verify(countryService).getCountries();
        verifyNoMoreInteractions(countryService);

        Assertions.assertEquals(Resource.error("Could not get the list of countries", null), listResource);
    }

}


