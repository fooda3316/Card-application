package com.example.mycards.social;

import android.util.Log;

import com.example.mycards.api.APIService;
import com.example.mycards.models.Result;
import com.example.mycards.models.Social;
import com.example.mycards.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Response;

public class SocialPresenter {
    private static APIService apiService;
    private static String facebook,twitter,youtube,inst,whatsApp;

    public SocialPresenter() {
        apiService= RetrofitClient.getInstance().getApiService();
        getFacebook();
        getTwitter();
        getWhatsApp();
        getInst();
        getYoutube();

    }

    public static String getFacebook() {
        if (facebook!=null){
            return facebook;
        }

        //Log.d("isAdimnAUTH","isAdimnAUTH");
        apiService.getFacebook().enqueue(new retrofit2.Callback<Social>() {
            @Override
            public void onResponse(Call<Social> call, Response<Social> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                facebook=response.body().getFacebook();
                Log.d("facebook",response.body().getFacebook());
            }

            @Override
            public void onFailure(Call<Social> call, Throwable t) {
                Log.e("getVerificatin error",t.toString());

            }
        });
        return facebook;
    }
    public static String getTwitter() {
if (twitter!=null){
    return twitter;
}
        //Log.d("isAdimnAUTH","isAdimnAUTH");
        apiService.getTwitter().enqueue(new retrofit2.Callback<Social>() {
            @Override
            public void onResponse(Call<Social> call, Response<Social> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                twitter=response.body().getTwitter();
            }

            @Override
            public void onFailure(Call<Social> call, Throwable t) {
                Log.e("getVerificatin error",t.toString());

            }
        });
        return twitter;
    }
    public static String getYoutube() {
        if (youtube!=null){
            return youtube;
        }
        //Log.d("isAdimnAUTH","isAdimnAUTH");
        apiService.getYoutube().enqueue(new retrofit2.Callback<Social>() {
            @Override
            public void onResponse(Call<Social> call, Response<Social> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                youtube=response.body().getYoutube();
            }

            @Override
            public void onFailure(Call<Social> call, Throwable t) {
                Log.e("getVerificatin error",t.toString());

            }
        });
        return youtube;
    }
    public static String getInst() {
        if (inst!=null){
            return inst;
        }
        //Log.d("isAdimnAUTH","isAdimnAUTH");
        apiService.getInst().enqueue(new retrofit2.Callback<Social>() {
            @Override
            public void onResponse(Call<Social> call, Response<Social> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                inst=response.body().getInst();
            }

            @Override
            public void onFailure(Call<Social> call, Throwable t) {
                Log.e("getVerificatin error",t.toString());

            }
        });
        return inst;
    }
    public static String getWhatsApp() {
        if (whatsApp!=null){
            return whatsApp;
        }
        //Log.d("isAdimnAUTH","isAdimnAUTH");
        apiService.getWhatsApp().enqueue(new retrofit2.Callback<Social>() {
            @Override
            public void onResponse(Call<Social> call, Response<Social> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                whatsApp=response.body().getWhatsApp();

            }

            @Override
            public void onFailure(Call<Social> call, Throwable t) {
                Log.e("getVerificatin error",t.toString());

            }
        });
        return whatsApp;
    }
}
