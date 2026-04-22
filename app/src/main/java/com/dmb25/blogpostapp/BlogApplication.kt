package com.dmb25.blogpostapp

import android.app.Application
import com.dmb25.blogpostapp.di.dataModule
import com.dmb25.blogpostapp.di.domainModule
import com.dmb25.blogpostapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BlogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BlogApplication)
            modules(
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}