package com.countryinformation.di.component;

import android.app.Application;

import com.countryinformation.MainApplication;
import com.countryinformation.di.module.AppModule;
import com.countryinformation.di.module.DetailModule;
import com.countryinformation.di.module.HomeModule;
import com.countryinformation.di.module.NetworkModule;
import com.countryinformation.di.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {AndroidSupportInjectionModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
                NetworkModule.class,
                HomeModule.class,
                DetailModule.class
        }
)
public interface AppComponent extends AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
