package com.example.androidauthmongodbnodejs.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getInstance(){
        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl("http://200.75.12.171:3000/")//In emulator, localhost will be changed to 10.0.2.2
                    .addConverterFactory(ScalarsConverterFactory.create())/* add ScalarsConverterFactory to get json string as response */
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    /*addCallAdapterFactory for RX Recyclerviews A call adapter which uses RxJava 2 for creating observables.
                    Adding this class to Retrofit allows you to return an Observable, Flowable, Single, Completable or Maybe from
                    service methods.*/
                    .build();
        return instance;
    }
}
