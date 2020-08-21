package com.zaitt.model

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.libraries.places.api.model.PhotoMetadata
import java.io.Serializable

/**
 * Created by marco on 19,August,2020
 */
class Store :Serializable{
    lateinit var placeId: String
    lateinit var img: PhotoMetadata
      var bitmap: Bitmap? = null
    lateinit var name: String
    lateinit var address: String

    var storeLatitude: Double = 0.0
    var storeLongitude: Double = 0.0
}