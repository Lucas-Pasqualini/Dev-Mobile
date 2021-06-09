package com.shootylife.soscaller.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.shootylife.soscaller.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SigninActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
    }
}