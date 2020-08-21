package com.zaitt.model

import java.io.Serializable

/**
 * Created by marco on 19,August,2020
------  request
"store_latitude": 23.42,
"store_longitude": 23.12,
"user_latitude":23.42,
"user_longitude": 23.12,
"value": 79.00
------ response
"product_value": 79.00,
"distance": 5.6566,
"total_value": 90.31,
"fee_value": 11.31
 */

class Checkout : Serializable {
    var store_latitude: Double = 0.0
    var store_longitude: Double = 0.0
    var user_latitude: Double = 0.0
    var user_longitude: Double = 0.0
    var value: Double = 0.0

    var product_value: Double = 0.0
    var distance: Double = 0.0
    var total_value: Double = 0.0
    var fee_value: Double = 0.0

}