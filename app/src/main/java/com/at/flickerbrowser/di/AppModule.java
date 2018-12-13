package com.at.flickerbrowser.di;

import android.app.Application;

import com.at.flickerbrowser.App;
import com.at.flickerbrowser.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Provides application-wide dependencies.
 */
@Module(includes = {NetworkModule.class})
abstract class AppModule {

    @Binds
    @Singleton
    /*
     * Singleton annotation isn't necessary since Application instance is unique but is here for
     * convention. In general, providing Activity, Fragment, BroadcastReceiver, etc does not require
     * them to be scoped since they are the components being injected and their instance is unique.
     *
     * However, having a scope annotation makes the module easier to read. We wouldn't have to look
     * at what is being provided in order to understand its scope.
     */
    abstract Application application(App app);

    /**
     * Provides the injector for the {@link MainActivity}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @ContributesAndroidInjector(modules = {})
    abstract MainActivity mainActivityInjector();
}
