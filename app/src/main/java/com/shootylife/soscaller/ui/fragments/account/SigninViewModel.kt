package com.shootylife.soscaller.ui.fragments.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shootylife.soscaller.data.repositories.AuthRepository

class SigninViewModel(val authRepository: AuthRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is login Fragment"
    }
    val text: LiveData<String> = _text

    fun signIn(email: String, password: String) = authRepository.signIn(email, password)


}