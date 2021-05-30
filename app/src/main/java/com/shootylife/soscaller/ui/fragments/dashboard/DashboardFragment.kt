package com.shootylife.soscaller.ui.fragments.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.shootylife.soscaller.databinding.FragmentDashboardBinding
import com.shootylife.soscaller.utils.fragmentAutoCleared

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding by fragmentAutoCleared()

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            Toast.makeText(context, "Maximum Limit Reached", Toast.LENGTH_LONG)
                    .show()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initForm()
        initListener()
        checkUpdate()
    }

    fun checkUpdate() {
        when {
            _binding.etNom.text.equals("") -> _binding.bUpdate.isEnabled = false
            _binding.etPrenom.text.equals("") -> _binding.bUpdate.isEnabled = false
            _binding.etPhone.text.equals("") -> _binding.bUpdate.isEnabled = false
            _binding.etMail.text.equals("") -> _binding.bUpdate.isEnabled = false
            _binding.etContact.text.equals("") -> _binding.bUpdate.isEnabled = false
            _binding.etGroupe.text.equals("") -> _binding.bUpdate.isEnabled = false
            _binding.etTaille.text.equals("") -> _binding.bUpdate.isEnabled = false
        }
    }

    private fun initForm() {
        _binding.etNom.setText("test")
        _binding.etPrenom.setText("test")
        _binding.etPhone.setText("test")
        _binding.etMail.setText("test")
        _binding.etContact.setText("test")
        _binding.etGroupe.setText("test")
        _binding.etTaille.setText("test")
    }

    private fun initListener() {
        _binding.etNom.addTextChangedListener(textWatcher)
        _binding.etPrenom.addTextChangedListener(textWatcher)
        _binding.etPhone.addTextChangedListener(textWatcher)
        _binding.etMail.addTextChangedListener(textWatcher)
        _binding.etContact.addTextChangedListener(textWatcher)
        _binding.etGroupe.addTextChangedListener(textWatcher)
        _binding.etTaille.addTextChangedListener(textWatcher)
    }
}