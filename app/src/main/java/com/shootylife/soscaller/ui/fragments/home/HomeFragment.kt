package com.shootylife.soscaller.ui.fragments.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
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
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.shootylife.soscaller.R


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
        if (!map.values.contains(false)) {
            test.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    @SuppressLint("MissingPermission")
    private val test = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
        Log.d("latitudelongitude",map.toString())
        if(!map.values.contains(false)) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location->
                    if (location != null) {
//                    Toast.makeText(requireContext(),(location.latitude).toString(), Toast.LENGTH_LONG)
//                        .show()
                        Log.d("latitudelongitude",(location.latitude).toString())
                        Log.d("latitudelongitude",(location.longitude).toString())
                    }


             }
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
       fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
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

        var pompiers = view.findViewById<Button>(R.id.btn_pompiers).setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "18"))
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location->
//                    if (location != null) {
////                    Toast.makeText(requireContext(),(location.latitude).toString(), Toast.LENGTH_LONG)
////                        .show()
//                        Log.d("latitudelongitude",(location.latitude).toString())
//                        Log.d("latitudelongitude",(location.longitude).toString())
//                    }
            startActivity(intent)


                }



        var urgences = view.findViewById<Button>(R.id.btn_urgences).setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "112"))
            startActivity(intent)
        }
        var police = view.findViewById<Button>(R.id.btn_police).setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17"))
            startActivity(intent)
        }
        var samu = view.findViewById<Button>(R.id.btn_samu).setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "15"))
            startActivity(intent)
        }
    }

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