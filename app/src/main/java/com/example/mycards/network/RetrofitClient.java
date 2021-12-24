package com.example.mycards.network;


import com.example.mycards.api.APIService;
import com.example.mycards.api.CardAPIService;
import com.example.mycards.api.CategoryAPI;
import com.example.mycards.api.PageAPI;
import com.fooda.mycards.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.example.mycards.api.APIUrl.BASE_URL;


public class RetrofitClient {

    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    Gson gson = new GsonBuilder().serializeNulls().create();


    /* Gson gson = new GsonBuilder()
            .setLenient()
            .create();*/
    private RetrofitClient() {


        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_UR)
                .client(RestClient.getClient())
                .addConverterFactory(LenientGsonConverterFactory.create(gson))
                //.addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public APIService getApiService() {
        return retrofit.create(APIService.class);
    }
    public PageAPI getPageService() {
        return retrofit.create(PageAPI.class);
    }
    public CategoryAPI getCategoryService() {
        return retrofit.create(CategoryAPI.class);
    }
    public CardAPIService getCardAPIService(){ return retrofit.create(CardAPIService.class);}



}

