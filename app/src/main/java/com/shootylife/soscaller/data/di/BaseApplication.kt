package com.shootylife.soscaller.data.di

import android.app.Application
import com.shootylife.soscaller.data.di.module.LocationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.shootylife.soscaller.data.di.module.RepositoryModules
import com.shootylife.soscaller.data.di.module.ServiceModules
import com.shootylife.soscaller.data.di.module.ViewModelModules
import com.shootylife.soscaller.ui.fragments.account.SigninViewModel

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    LocationModule.locationModule,
                    LocationModule.homeViewModel,
                    ServiceModules.services,
                    RepositoryModules.repositories,
                    ViewModelModules.viewModels
                )
            )
            koin.createRootScope()
        }

    }
}