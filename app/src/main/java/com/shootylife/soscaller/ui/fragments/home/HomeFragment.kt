package com.shootylife.soscaller.ui.fragments.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.google.android.gms.location.FusedLocationProviderClient
import android.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.shootylife.soscaller.R
import com.shootylife.soscaller.ui.activities.MainActivity


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private var hasGPS = false
    private var hasNetwork = false
    private  var locationGPS : Location? = null
    private  var locationNetwork : Location? = null

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasGPS||hasNetwork){
            if (hasGPS){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0F, object : LocationListener{
                    override fun onLocationChanged(location: Location?) {
                        if (location != null){
                            locationGPS = location
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        TODO("Not yet implemented")
                    }

                    override fun onProviderEnabled(provider: String?) {
                        TODO("Not yet implemented")
                    }

                    override fun onProviderDisabled(provider: String?) {
                        TODO("Not yet implemented")
                    }
                })
               val localGPSLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGPSLocation != null){
                    locationGPS = localGPSLocation
                }
            }
            if (hasNetwork){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,0F, object : LocationListener{
                    override fun onLocationChanged(location: Location?) {
                        if (location != null){
                            locationNetwork = location
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        TODO("Not yet implemented")
                    }

                    override fun onProviderEnabled(provider: String?) {
                        TODO("Not yet implemented")
                    }

                    override fun onProviderDisabled(provider: String?) {
                        TODO("Not yet implemented")
                    }
                })
                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null){
                    locationNetwork = localNetworkLocation
                }
            }
            if ( locationGPS!= null && locationNetwork != null){
                if (locationGPS!!.accuracy > locationNetwork!!.accuracy){
                    Toast.makeText(requireContext(),("network"+locationNetwork!!.latitude+" "+ locationNetwork!!.longitude), Toast.LENGTH_LONG)
                        .show()
                }
                else{
                    Toast.makeText(requireContext(),("gps"+locationGPS!!.latitude+" "+ locationGPS!!.longitude), Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
    }

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
//       fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
        getLocation()
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
        getLocation()

        var pompiers = view.findViewById<Button>(R.id.btn_pompiers).setOnClickListener{
//            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "18"))
//                    Toast.makeText(requireContext(),(longitude + " " + latitude), Toast.LENGTH_LONG)
//                        .show()
//            startActivity(intent)
            getLocation()
        }

//        var urgences = view.findViewById<Button>(R.id.btn_urgences).setOnClickListener{
//            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "112"))
//            Toast.makeText(requireContext(),(longitude + " " + latitude), Toast.LENGTH_LONG)
//                .show()
//            startActivity(intent)
//        }
//
//        var police = view.findViewById<Button>(R.id.btn_police).setOnClickListener{
//            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17"))
//            Toast.makeText(requireContext(),(longitude + " " + latitude), Toast.LENGTH_LONG)
//                .show()
//            startActivity(intent)
//        }
//
//        var samu = view.findViewById<Button>(R.id.btn_samu).setOnClickListener{
//            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "15"))
//            Toast.makeText(requireContext(),(longitude + " " + latitude), Toast.LENGTH_LONG)
//                .show()
//            startActivity(intent)
//        }
    }

//    override fun onLocationChanged(p0: Location?) {
//        latitude = p0?.latitude.toString()
//        longitude = p0?.longitude.toString()
//    }

//    fun getLastKnownLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                requireActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireActivity(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            Log.d("lat123",("test"))
//            ActivityCompat.requestPermissions(requireActivity(),
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION), 1)
//            return
//        }
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location->
//                if (location != null) {
//                   Log.d("latitudelongitude",(location.latitude).toString())
//                    Log.d("latitudelongitude",(location.longitude).toString())
//                }
//
//            }
//
//    }
}