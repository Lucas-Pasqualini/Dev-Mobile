package com.shootylife.soscaller.ui.fragments.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shootylife.soscaller.databinding.FragmentHomeBinding
import com.shootylife.soscaller.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding by fragmentAutoCleared()
    private var list: MutableList<String> = arrayOf("").toMutableList()
    private var latitude = 0.0
    private var longitude = 0.0

    private val permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
        if (!map.values.contains(false)) {
            getLocation()
        }
        else{
            Toast.makeText(context,"Permission denied",Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun loadData() {
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences?.getString("CALL_LIST", "[]")
        val type = object: TypeToken<ArrayList<String>>() {}.type
        list = gson.fromJson(json, type)
    }

    private fun saveData(call: String) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        list.add("$currentDate / $call")

        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor?.putString("CALL_LIST", json)
        editor?.apply()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        loadData()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionResultLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))

        _binding.btnPompiers.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "18"))
            saveData("pompier")
            Toast.makeText(context,"longitude : $longitude / latitude : $latitude",Toast.LENGTH_LONG)
                .show()
            startActivity(intent)
        }

        _binding.btnUrgences.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "112"))
            saveData("urgences")
            Toast.makeText(context,"longitude : $longitude / latitude : $latitude",Toast.LENGTH_LONG)
                .show()
            startActivity(intent)
        }

        _binding.btnPolice.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17"))
            saveData("police")
            Toast.makeText(context,"longitude : $longitude / latitude : $latitude",Toast.LENGTH_LONG)
                .show()
            startActivity(intent)
        }

        _binding.btnSamu.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "15"))
            saveData("samu")
            Toast.makeText(context,"longitude : $longitude / latitude : $latitude",Toast.LENGTH_LONG)
                .show()
            startActivity(intent)

        }
    }

    private fun getLocation() {
        homeViewModel.locationLiveData.observe( viewLifecycleOwner,
            Observer {
                longitude = it.longitude
                latitude = it.latitude
            }
        )
    }

}