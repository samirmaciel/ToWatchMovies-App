package com.sm.towatchmovies

import android.app.Application
import com.sm.iwanttowatch.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToWatchMoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { androidContext(this@ToWatchMoviesApplication); modules(appModule) }
    }
}
