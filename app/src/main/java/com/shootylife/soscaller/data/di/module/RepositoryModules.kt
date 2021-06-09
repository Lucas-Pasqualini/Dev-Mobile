package com.shootylife.soscaller.data.di.module

import com.shootylife.soscaller.data.dataSources.remote.firebase.FirebaseAuthService
import com.shootylife.soscaller.data.repositories.AuthRepository
import org.koin.dsl.module

object RepositoryModules {
    val repositories = module {
        fun createAuthRepositoty(firebaseAuthService: FirebaseAuthService) = AuthRepository(firebaseAuthService )

        single { createAuthRepositoty(get()) }
    }
}