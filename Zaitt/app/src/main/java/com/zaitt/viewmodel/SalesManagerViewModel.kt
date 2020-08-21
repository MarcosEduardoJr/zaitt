package com.zaitt.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaitt.model.Checkout
import com.zaitt.model.CloseOrder
import com.zaitt.request.SaleManagerRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by marco on 19,Abril,2020
 */
class SalesManagerViewModel : ViewModel() {
    var checkout = MutableLiveData<Checkout>()
    var closeOrder = MutableLiveData<CloseOrder>()


    fun resume(pCheckout: Checkout) {
        SaleManagerRequest.getInstance().resume(pCheckout)
            .enqueue(object : Callback<Checkout?> {
                override fun onResponse(
                    call: Call<Checkout?>,
                    response: Response<Checkout?>
                ) {
                    if (response.isSuccessful) {
                        checkout.value = response.body()
                    } else {
                        checkout.value = null
                    }
                }

                override fun onFailure(
                    call: Call<Checkout?>,
                    t: Throwable
                ) {
                    checkout.value = null
                }
            })
    }

    fun closeOrder(pCloseOrder: CloseOrder) {
        SaleManagerRequest.getInstance().closeOrder(pCloseOrder)
            .enqueue(object : Callback<CloseOrder?> {
                override fun onResponse(
                    call: Call<CloseOrder?>,
                    response: Response<CloseOrder?>
                ) {
                    if (response.isSuccessful) {
                        closeOrder.value = response.body()
                    } else {
                        closeOrder.value = null
                    }
                }

                override fun onFailure(
                    call: Call<CloseOrder?>,
                    t: Throwable
                ) {
                    closeOrder.value = null
                }
            })
    }
}