package com.zaitt.request;

import com.zaitt.BuildConfig;
import com.zaitt.api.SaleManagerApi;
import com.zaitt.model.Checkout;
import com.zaitt.model.CloseOrder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marco on 30,Janeiro,2020
 */
public class SaleManagerRequest {


    private static final SaleManagerRequest INSTANCE = new SaleManagerRequest();

    private SaleManagerRequest() {
    }

    public static SaleManagerRequest getInstance() {
        return INSTANCE;
    }

    private Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private SaleManagerApi api = retrofit.create(SaleManagerApi.class);

    public Call<Checkout> resume(
            Checkout checkout) {
        return api.resume(checkout);
    }

    public Call<CloseOrder> closeOrder(
            CloseOrder closeOrder) {
        return api.closeOrder(closeOrder);
    }


}
