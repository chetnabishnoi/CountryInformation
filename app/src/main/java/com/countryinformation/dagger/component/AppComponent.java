package com.countryinformation.dagger.component;

import android.app.Application;

import com.countryinformation.MainApplication;
import com.countryinformation.dagger.module.ActivityBuildersModule;
import com.countryinformation.dagger.module.AppModule;
import com.countryinformation.dagger.module.NetworkModule;
import com.countryinformation.dagger.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
                NetworkModule.class
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
