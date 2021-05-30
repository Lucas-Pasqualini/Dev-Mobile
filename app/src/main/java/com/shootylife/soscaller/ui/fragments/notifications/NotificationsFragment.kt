package com.shootylife.soscaller.ui.fragments.notifications

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shootylife.soscaller.databinding.FragmentNotificationBinding
import com.shootylife.soscaller.ui.adapters.CallListAdapters
import com.shootylife.soscaller.utils.fragmentAutoCleared

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationBinding by fragmentAutoCleared()
    private var list: MutableList<String> = arrayOf("").toMutableList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        loadData()
        val adapter = CallListAdapters(list)
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        _binding.recyclerView.adapter = adapter
        return _binding.root
    }

    private fun loadData() {
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences?.getString("CALL_LIST", "[]")
        val type = object: TypeToken<ArrayList<String>>() {}.type
        list = gson.fromJson(json, type)
    }
}