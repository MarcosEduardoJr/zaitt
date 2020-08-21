package com.zaitt.api;

import com.zaitt.model.Checkout;
import com.zaitt.model.CloseOrder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by marco on 17,Abril,2020
 */
public interface SaleManagerApi {
    @Headers("Accept: application/json")
    @POST("order/resume")
    Call<Checkout> resume(
            @Body Checkout checkout
    );

    @Headers("Accept: application/json")
    @POST("order/")
    Call<CloseOrder> closeOrder(
            @Body CloseOrder checkout
    );

}
