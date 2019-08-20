package com.countryinformation.di.module;

import android.app.Application;
import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.RequestBuilder;
import com.countryinformation.R;
import com.countryinformation.glide.GlideApp;
import com.countryinformation.glide.SvgSoftwareLayerSetter;
import com.countryinformation.network.CountryService;
import com.countryinformation.repository.CountryRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

@Module
public class AppModule {

    @Singleton
    @Provides
    static RequestBuilder<PictureDrawable> provideGlideSvgInstance(Application application) {
        return GlideApp.with(application.getApplicationContext())
                .as(PictureDrawable.class)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter());
    }

    @Singleton
    @Provides
    static CountryRepository provideCountryRepository(CountryService countryService) {
        return new CountryRepository(countryService);
    }
}
