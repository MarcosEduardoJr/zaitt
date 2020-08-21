package com.zaitt.model

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.libraries.places.api.model.PhotoMetadata
import java.io.Serializable

/**
 * Created by marco on 19,August,2020
 */
class Order : Serializable {
    lateinit var description: String
    lateinit var estimate: String
}