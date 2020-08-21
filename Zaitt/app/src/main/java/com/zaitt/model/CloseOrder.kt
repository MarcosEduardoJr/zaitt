package com.zaitt.model

import java.io.Serializable

/**
 * Created by marco on 19,August,2020
------  request
"store_latitude": 23.42,
"store_longitude": 23.12,
"user_latitude":23.42,
"user_longitude": 23.12,
"card_number":"1111111111111111",
"cvv":"789",
"expiry_date":"01/18",
"value":79.90

------ response
"message": "Obrigado por comprar na shipp :D",
"value":90.31
 */

class CloseOrder : Serializable {
    var store_latitude: Double = 0.0
    var store_longitude: Double = 0.0
    var user_latitude: Double = 0.0
    var user_longitude: Double = 0.0
    var card_number: String = ""
    var cvv: String = ""
    var expiry_date: String = ""
    var message: String = ""
    var value: Double = 0.0

}