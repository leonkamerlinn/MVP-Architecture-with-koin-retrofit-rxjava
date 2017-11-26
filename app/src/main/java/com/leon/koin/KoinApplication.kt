package com.leon.koin

import android.app.Application
import com.leon.koin.di.appModules
import org.koin.android.ext.android.startAndroidContext

/**
 * Created by Leon on 20.11.2017..
 */
class KoinApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        
        startAndroidContext(this, appModules())
        
    }
}