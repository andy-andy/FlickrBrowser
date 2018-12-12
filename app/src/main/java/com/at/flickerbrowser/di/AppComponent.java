package com.at.flickerbrowser.di;

import com.at.flickerbrowser.App;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Injects application dependencies.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, FragmentModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }

}
