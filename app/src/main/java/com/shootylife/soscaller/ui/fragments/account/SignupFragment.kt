package com.shootylife.soscaller.ui.fragments.account

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.shootylife.soscaller.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

class SignupFragment : Fragment() {
    private val signupViewModel: SignupViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_signup, container, false)
        signupViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.signUpbutton).setOnClickListener {
            val editTextEmail = view.findViewById<EditText>(R.id.inputEmail)
            val editTextPassword = view.findViewById<EditText>(R.id.inputPassword)

            signupViewModel.signUp(editTextEmail.text.toString(), editTextPassword.text.toString())
                .observe(viewLifecycleOwner, Observer {
                    it?.let { firebaseUser ->
                        firebaseUser.uid
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                })
        }
    }
}