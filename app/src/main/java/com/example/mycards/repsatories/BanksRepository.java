package com.example.mycards.repsatories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mycards.models.Bank;
import com.example.mycards.models.Banks;
import com.example.mycards.models.Page;
import com.example.mycards.models.Pages;
import com.example.mycards.network.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BanksRepository {
    public MutableLiveData<List<Bank>> cardsLiveData;
    Observable<Banks> observable;

    public BanksRepository() {
        cardsLiveData  = new MutableLiveData<>();
        try {


        observable= RetrofitClient.getInstance()
                .getApiService().getAllbanks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        }
        catch (Exception ex){
            Log.e("observableException",ex.toString());
        }

    }


    public Observable<Banks> getObservable() {
      //  Log.d("is null", String.valueOf(observable==null));
        if (observable==null)
            return new Observable<Banks>() {
                @Override
                protected void subscribeActual(Observer<? super Banks> observer) {

                }
            };
        return observable;
    }
}
