package com.shootylife.soscaller.ui.fragments.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.shootylife.soscaller.R
import com.shootylife.soscaller.ui.adapters.CallListAdapters

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val adapter = CallListAdapters(listOf("a", "b", "c"))
        // _binding.recyclerView.adapter = adapter

        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val rec = root.findViewById<RecyclerView>(R.id.recyclerView)
        rec.adapter = adapter
        return root
    }
}