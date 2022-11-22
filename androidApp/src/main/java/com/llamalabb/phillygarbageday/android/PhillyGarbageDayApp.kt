package com.llamalabb.phillygarbageday.android

import android.app.Application
import com.llamalabb.phillygarbageday.domain.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


class PhillyGarbageDayApp : Application(){

    override fun onCreate() {
        super.onCreate()

        initKoin(baseUrl = "https://newsapi.org/v2/", enableNetworkLogs = BuildConfig.DEBUG) {
            androidContext(this@PhillyGarbageDayApp)
            // androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.INFO)
            modules(
                listOf(module {
                    /**
                     * android specific modules
                     */
                })
            )
        }
    }
}