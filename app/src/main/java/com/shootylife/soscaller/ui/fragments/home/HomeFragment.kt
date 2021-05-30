package com.shootylife.soscaller.ui.fragments.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.location.LocationListener
import android.widget.TextView
import com.shootylife.soscaller.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shootylife.soscaller.databinding.FragmentHomeBinding
import com.shootylife.soscaller.utils.fragmentAutoCleared

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding by fragmentAutoCleared()
    private lateinit var locationManager: LocationManager
    private var hasGPS = false
    private var hasNetwork = false
    private  var locationGPS : Location? = null
    private  var locationNetwork : Location? = null

    private var list: MutableList<String> = arrayOf("").toMutableList()

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

//    @SuppressLint("MissingPermission")
//    private fun getLocation(){
//        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//
//        if (hasGPS||hasNetwork){
//            if (hasGPS){
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0F, object : LocationListener{
//                    override fun onLocationChanged(location: Location) {
//                        if (location != null){
//                            locationGPS = location
//                        }
//                    }
//
//                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onProviderEnabled(provider: String) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onProviderDisabled(provider: String) {
//                        TODO("Not yet implemented")
//                    }
//                })
//               val localGPSLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//                if (localGPSLocation != null){
//                    locationGPS = localGPSLocation
//                }
//            }
//            if (hasNetwork){
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,0F, object : LocationListener{
//                    override fun onLocationChanged(location: Location) {
//                        if (location != null){
//                            locationNetwork = location
//                        }
//                    }
//
//                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onProviderEnabled(provider: String) {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onProviderDisabled(provider: String) {
//                        TODO("Not yet implemented")
//                    }
//                })
//                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//                if (localNetworkLocation != null){
//                    locationNetwork = localNetworkLocation
//                }
//            }
//            if ( locationGPS!= null && locationNetwork != null){
//                if (locationGPS!!.accuracy > locationNetwork!!.accuracy){
//                    Toast.makeText(requireContext(),("network"+locationNetwork!!.latitude+" "+ locationNetwork!!.longitude), Toast.LENGTH_LONG)
//                        .show()
//                }
//                else{
//                    Toast.makeText(requireContext(),("gps"+locationGPS!!.latitude+" "+ locationGPS!!.longitude), Toast.LENGTH_LONG)
//                        .show()
//                }
//            }
//
//        }
//    }

    private val permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
        if (!map.values.contains(false)) {
           test.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    @SuppressLint("MissingPermission")
    private val test = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        loadData()
        // getLocation()
        return _binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionResultLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        // getLocation()

        _binding.btnPompiers.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "18"))
            // getLocation()
            saveData("pompier")
            startActivity(intent)
        }

        _binding.btnUrgences.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "112"))
            // getLocation()
            saveData("urgences")
            startActivity(intent)
        }

        _binding.btnPolice.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17"))
            // getLocation()
            saveData("police")
            startActivity(intent)
        }

        _binding.btnSamu.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "15"))
            // getLocation()
            saveData("samu")
            startActivity(intent)
        }
    }
}