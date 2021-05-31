package com.shootylife.soscaller.data.di.module

import com.shootylife.soscaller.ui.fragments.home.HomeViewModel
import com.waykonect.appholder.ui.utils.LocationLiveData
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object LocationModule {
    val locationModule = module { single { LocationLiveData(androidContext()) } }
    val homeViewModel = module { single {HomeViewModel(get())} }
}