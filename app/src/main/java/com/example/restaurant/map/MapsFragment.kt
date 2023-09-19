package com.example.restaurant.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.location.LocationManagerCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.example.restaurant.R
import com.example.restaurant.database.UserImp
import com.example.restaurant.localData.SharedPrefs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.random.Random

class MapsFragment : Fragment() {
    var lat = 0.0
    var long = 0.0
    var cityName ="test"
    val userData = UserImp()

    private val callback = OnMapReadyCallback { googleMap ->
        val randomInt = Random.nextDouble(1.0, 9.0)
        val latlong = LatLng(lat, long)
        val latlongSecand = LatLng(lat+randomInt, long+randomInt)
        googleMap.addMarker(MarkerOptions().position(latlongSecand))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong,7f))
        googleMap.uiSettings.isZoomControlsEnabled=true
        googleMap.addPolyline(
            PolylineOptions()
                .add(latlong)
                .add(latlongSecand)
                .color(Color.CYAN)
                .visible(true)

        )
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?


        val checkPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                getLastLocation()

                }

            else
                Log.e("hzm","Please Enable Location Permission")
        }
        if(isLocationEnabled(requireContext())){
            checkPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

        }else
        {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }

    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        val locationProvider: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationProvider.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    lat = location.latitude
                    long = location.longitude

                    GlobalScope.launch {
                        cityName = fetchCityName(lat, long)
                        userData.addUserLocationToFirestoreById(
                            SharedPrefs.getUserIdFromPrefs(requireContext()),
                            cityName
                        )
                        withContext(Dispatchers.Main) {
                            if (cityName.isNotEmpty()) {
                            }

                            val latLng = LatLng(lat, long)

                            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                            mapFragment?.getMapAsync(callback)
                        }
                    }
                } else { Log.e("hzm", "Error Location")
                }
            }
            .addOnFailureListener { exception ->

            }
    }

    private suspend fun fetchCityName(lat: Double, long: Double): String = withContext(Dispatchers.IO) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, long, 1)

        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                return@withContext addresses?.get(0)?.countryName?: ""
            }
        }

        return@withContext ""
    }

    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

}