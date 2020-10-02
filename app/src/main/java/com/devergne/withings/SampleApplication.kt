package com.devergne.withings

import android.app.Application
import com.devergne.withings.di.sampleModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin {
            androidContext(this@SampleApplication)
            modules(sampleModule)
        }
    }
}