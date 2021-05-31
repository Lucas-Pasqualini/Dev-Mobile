package com.shootylife.soscaller.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waykonect.appholder.ui.utils.LocationLiveData

class HomeViewModel(val locationLiveData: LocationLiveData) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}