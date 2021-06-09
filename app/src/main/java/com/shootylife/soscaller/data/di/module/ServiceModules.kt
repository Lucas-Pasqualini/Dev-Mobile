package com.shootylife.soscaller.data.di.module

import com.shootylife.soscaller.data.dataSources.remote.firebase.FirebaseAuthService
import org.koin.dsl.module

object ServiceModules {

    val services = module {
        fun createFirebaseAuthService() = FirebaseAuthService();

        single {
            createFirebaseAuthService()
        }
    }
}
