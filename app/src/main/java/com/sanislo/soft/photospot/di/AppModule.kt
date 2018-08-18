package com.sanislo.soft.photospot.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides


@Module(includes = [
    ViewModelModule::class,
    LoginFragmentModule::class,
    SignUpFragmentModule::class,
    MainActivityModule::class
])
class AppModule {

    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}