package com.at.flickerbrowser

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.at.flickerbrowser.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * The Android [Application].
 * Used for accessing singletons.
 *
 *
 * **DEPENDENCY INJECTION**
 * We could extend [dagger.android.DaggerApplication] so we can get the boilerplate
 * dagger code for free. However, we want to avoid inheritance (if possible and it is in this case)
 * so that we have to option to inherit from something else later on if needed
 */
class App : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().create(this).inject(this)
    }

    override fun activityInjector() = dispatchingActivityInjector

    override fun supportFragmentInjector() = dispatchingFragmentInjector

}
