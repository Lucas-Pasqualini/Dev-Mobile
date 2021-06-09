package com.shootylife.soscaller.data.di.module
import com.shootylife.soscaller.data.repositories.AuthRepository
import org.koin.dsl.module
import com.shootylife.soscaller.ui.fragments.account.SigninViewModel
import com.shootylife.soscaller.ui.fragments.account.SignupViewModel

object ViewModelModules {
    val viewModels = module {

        fun createSigninViewModel(authRepository: AuthRepository) = SigninViewModel(authRepository)

        single {createSigninViewModel((get()))}

        fun createSignupViewModel(authRepository: AuthRepository) = SignupViewModel(authRepository)

        single {createSignupViewModel((get()))}
    }
}