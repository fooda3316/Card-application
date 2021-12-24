package com.example.mycards.network;


import com.example.mycards.api.APIService;
import com.fooda.mycards.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.example.mycards.api.APIUrl.LOGIN_URL;


public class RetrofitLoinClient {

    private static RetrofitLoinClient mInstance;
    private Retrofit retrofit;

    Gson gson = new GsonBuilder().serializeNulls().create();


    /* Gson gson = new GsonBuilder()
            .setLenient()
            .create();*/
    private RetrofitLoinClient() {


        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.LOGIN_URL)
                .client(RestClient.getClient())
                .addConverterFactory(LenientGsonConverterFactory.create(gson))
                //.addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    public static synchronized RetrofitLoinClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitLoinClient();
        }
        return mInstance;
    }

    public APIService getApiService() {
        return retrofit.create(APIService.class);
    }


}

