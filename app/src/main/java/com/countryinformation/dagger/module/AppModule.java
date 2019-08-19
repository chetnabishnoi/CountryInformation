package com.countryinformation.dagger.module;

import android.app.Application;
import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
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
    static RequestOptions provideRequestOptions() {
        return RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {
        return Glide.with(application).setDefaultRequestOptions(requestOptions);
    }

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
