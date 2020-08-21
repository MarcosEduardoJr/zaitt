package com.zaitt.fragments

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.*
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.zaitt.BuildConfig
import com.zaitt.MainActivity
import com.zaitt.R
import com.zaitt.adapter.StoreListAdapter
import com.zaitt.model.Store
import kotlinx.android.synthetic.main.fragment_search_store.*
import java.net.CacheRequest

class SearchStoreFragment : Fragment() {


    lateinit var locationRequest: LocationRequest
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private lateinit var viewAdapter: StoreListAdapter
    private lateinit var viewManager: LinearLayoutManager
    private var listStore = ArrayList<Store>()
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    lateinit var activity: MainActivity

    private lateinit var locationManager: LocationManager
    var gpsStatus = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         activity =(requireActivity() as MainActivity)
        Dexter.withActivity(requireActivity())
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    updateLocation()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {}
            }).check()

    }

    private fun updateLocation() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_store, container, false)
    }

    override fun onStart() {
        super.onStart()

        Places.initialize(requireContext(), BuildConfig.GOOGLE_API_KEY)
        placesClient = Places.createClient(requireContext())
        initializeRecycleView()
        configSearchPlacesApi()
    }

    override fun onResume() {
        super.onResume()
        checkGpsStatus()
    }

    private fun checkGpsStatus() {
        locationManager =
            requireContext()?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (gpsStatus) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        activity.userLatlng = LatLng(location.latitude, location.longitude)
                    }
                }
        } else {
            Snackbar.make(
                requireView(),
                "Você precisa habilitar o gps",
                Snackbar.LENGTH_LONG
            ).setAction(R.string.ok) {
                gpsStatus(requireView())
            }.show()
        }
    }

    fun gpsStatus(view: View) {
        var intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    private fun initializeRecycleView() {
        viewManager = LinearLayoutManager(requireContext())
        viewAdapter = StoreListAdapter(listStore)

        recyclerview.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    private fun configSearchPlacesApi() {
        search.doOnTextChanged { text, start, before, count ->
            val placeFields: List<Place.Field> = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )

            // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
            // and once again when the user makes a selection (for example when calling fetchPlace()).
            val token = AutocompleteSessionToken.newInstance()

            // Create a RectangularBounds object.
            val bounds = RectangularBounds.newInstance(
                activity.userLatlng,
                activity.userLatlng
            )
            // Use the builder to create a FindAutocompletePredictionsRequest.
            val request =
                FindAutocompletePredictionsRequest.builder()
                    // Call either setLocationBias() OR setLocationRestriction().
                    .setLocationBias(bounds)
                    //.setLocationRestriction(bounds)
                    .setOrigin(activity.userLatlng)
                    .setCountries("BR")
                    .setTypeFilter(TypeFilter.ESTABLISHMENT)
                    .setSessionToken(token)
                    .setQuery(text.toString())
                    .build()


            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                    if (response.autocompletePredictions.isNotEmpty())
                        listStore = ArrayList<Store>()

                    response.autocompletePredictions.forEachIndexed { index, prediction ->
                        fetchPlace(
                            prediction,
                            placeFields,
                            token,
                            (index + 1),
                            response.autocompletePredictions.size
                        )
                    }

                }.addOnFailureListener { exception: Exception? ->
                    if (exception is ApiException) {
                        //  Log.e(TAG, "Place not found: " + exception.statusCode)
                    }
                }


        }
    }

    private fun fetchPlace(
        prediction: AutocompletePrediction,
        placeFields: List<Place.Field>,
        token: AutocompleteSessionToken,
        index: Int,
        size: Int
    ) {
        var store = Store()
        placesClient.fetchPlace(
            FetchPlaceRequest.builder(prediction.placeId, placeFields).setSessionToken(token)
                .build()
        )
            .addOnSuccessListener { response: FetchPlaceResponse ->
                if (response.place.photoMetadatas != null) {
                    store.img = response.place.photoMetadatas!!.first()
                }
                store.placeId = response.place.id.toString()
                store.name = response.place.name.toString()
                store.address = response.place.address.toString()
                store.storeLatitude = response.place.latLng?.latitude!!
                store.storeLongitude = response.place.latLng?.longitude!!
                listStore.add(store)
                if (index == size)
                    viewAdapter.update(listStore)
            }
            .addOnFailureListener { exception: java.lang.Exception ->

            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        search.setText(place.name)
                        // Log.i(TAG, "Place: ${place.name}, ${place.id}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        //  Log.i(TAG, status.statusMessage)
                    }
                }

            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}