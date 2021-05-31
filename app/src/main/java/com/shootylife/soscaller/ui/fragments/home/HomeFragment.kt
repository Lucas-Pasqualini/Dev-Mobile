package com.shootylife.soscaller.ui.fragments.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shootylife.soscaller.databinding.FragmentHomeBinding
import com.shootylife.soscaller.utils.fragmentAutoCleared
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(), LocationListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding by fragmentAutoCleared()
    var lm: LocationManager? = null
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()
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
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lm!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 110f, this)

        _binding.btnPompiers.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "18"))
            getLocation()
            saveData("pompier")
            startActivity(intent)
        }

        _binding.btnUrgences.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "112"))
            getLocation()
            saveData("urgences")
            startActivity(intent)
        }

        _binding.btnPolice.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17"))
            getLocation()
            saveData("police")
            startActivity(intent)
        }

        _binding.btnSamu.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "15"))
            getLocation()
            saveData("samu")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        checkPermissions()
    }

    private fun checkPermissions() {
    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1234)
            return
        }

        lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lm!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, this)
        }
        if (lm!!.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            lm!!.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 0f, this)
        }
        if (lm!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            lm!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0f, this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1234) {
            checkPermissions()
        }
    }

    private fun getLocation() {
        Toast.makeText(requireContext(), "latitude : $latitude / longitude : $longitude", Toast.LENGTH_LONG)
                .show()
    }

    override fun onLocationChanged(p0: Location) {
        latitude = p0.latitude
        longitude = p0.longitude
    }

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
}