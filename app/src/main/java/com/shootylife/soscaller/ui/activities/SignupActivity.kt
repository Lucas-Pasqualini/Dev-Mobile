package com.shootylife.soscaller.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shootylife.soscaller.R
import com.shootylife.soscaller.databinding.ActivityLoginBinding
import com.shootylife.soscaller.utils.activityViewBinding

class SignupActivity : AppCompatActivity() {
    private var _binding by activityViewBinding(ActivityLoginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        _binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}