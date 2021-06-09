package com.shootylife.soscaller.data.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.shootylife.soscaller.data.dataSources.remote.firebase.FirebaseAuthService

class AuthRepository(val firebaseAuthService: FirebaseAuthService) {

    fun getCurrentFirebaseUser() = firebaseAuthService.getCurrentFirebaseUser()

    fun signIn(email: String, password: String): MutableLiveData<FirebaseUser?> {
        val response = MutableLiveData<FirebaseUser?>()
        firebaseAuthService.signIn(email, password).addOnSuccessListener {
            response.value = it.user
        }.addOnFailureListener {

        }

        return response
    }

    fun signUp(email: String, password: String) : MutableLiveData<FirebaseUser?> {
        val response = MutableLiveData<FirebaseUser?>()
        firebaseAuthService.signUp(email, password).addOnSuccessListener {
            response.value = it.user
        }.addOnFailureListener {

        }

        return response
    }
}