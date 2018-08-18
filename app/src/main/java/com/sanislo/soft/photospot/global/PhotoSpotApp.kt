package com.sanislo.soft.photospot.global

import android.app.Activity
import android.app.Application
import com.sanislo.soft.photospot.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class PhotoSpotApp : Application(), HasActivityInjector {
    @Inject
    lateinit var mActivityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return mActivityDispatchingAndroidInjector
    }
}