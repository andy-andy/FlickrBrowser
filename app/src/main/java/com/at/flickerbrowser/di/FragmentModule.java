package com.at.flickerbrowser.di;

import com.at.flickerbrowser.ui.main.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MainFragment mainFragmentInjector();

}
