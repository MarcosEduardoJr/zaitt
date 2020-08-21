package com.zaitt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.zaitt.model.Order
import com.zaitt.model.Store

class MainActivity : AppCompatActivity() {
    var order: Order? = null
    var store: Store? = null
      var userLatlng: LatLng = LatLng(0.0,0.0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}