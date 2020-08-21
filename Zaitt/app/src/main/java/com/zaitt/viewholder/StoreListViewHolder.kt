package com.zaitt.viewholder

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import com.makeramen.roundedimageview.RoundedImageView
import com.zaitt.R
import com.zaitt.fragments.SearchStoreFragmentDirections
import com.zaitt.model.Store
import kotlinx.android.synthetic.main.item_store.view.*

/**
 * Created by marco on 10,August,2020
 */
class StoreListViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    var itemClick: ((String) -> Unit)? = null

    fun bindView(item: Store) {
        itemView.name.text = item.name
        itemView.address.text = item.address

        fetchImage(itemView, itemView.img, item.placeId, item)


        //For Handling RecyclerView Item Click
        itemView?.setOnClickListener {
            val action = SearchStoreFragmentDirections.actionNext(item)
            Navigation.findNavController(itemView).navigate(action)
        }

    }

    private fun fetchImage(
        itemView: View,
        img: RoundedImageView,
        placeId: String,
        item: Store
    ) {


// Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
        val fields = listOf(Place.Field.PHOTO_METADATAS)

// Get a Place object (this example uses fetchPlace(), but you can also use findCurrentPlace())
        val placeRequest = FetchPlaceRequest.newInstance(
            placeId,
            fields
        )
        var placesClient = Places.createClient(itemView.context)
        placesClient.fetchPlace(placeRequest)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val place = response.place

                // Get the photo metadata.
                val metada = place.photoMetadatas
                if (metada == null || metada.isEmpty()) {
                    //   Log.w(TAG, "No photo metadata.")
                    return@addOnSuccessListener
                }
                val photoMetadata = metada.first()

                // Get the attribution text.
                val attributions = photoMetadata?.attributions

                // Create a FetchPhotoRequest.
                val photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxWidth(
                        itemView.resources.getDimension(R.dimen.img_item_size_order).toInt()
                    ) // Optional.
                    .setMaxHeight(
                        itemView.resources.getDimension(R.dimen.img_item_size_order).toInt()
                    ) // Optional.
                    .build()
                placesClient.fetchPhoto(photoRequest)
                    .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                        item.bitmap = fetchPhotoResponse.bitmap
                        img.setImageBitmap(item.bitmap)
                    }.addOnFailureListener { exception: Exception ->
                        /*  if (exception is ApiException) {
                              Log.e(TAG, "Place not found: " + exception.message)
                              val statusCode = exception.statusCode
                              TODO("Handle error with given status code.")
                          }*/
                    }
            }
    }

}