package com.example.mycards.repsatories;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.mycards.models.Results;
import com.example.mycards.network.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.mycards.utilits.Constants.USER_REGISTER_ID;


public class WalletInfoREPO {
    Observable<Results> observable;
    private Context context;
    public WalletInfoREPO(Context context) {
        this.context=context;
        observable= RetrofitClient.getInstance()
                .getApiService().getWalletInfo(getUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
      //  Log.d("user id ",getUserID()+"");
    }


    public Observable<Results> getObservable() {
      //  Log.d("is null", String.valueOf(observable==null));
        if (observable==null)
            return new Observable<Results>() {
                @Override
                protected void subscribeActual(Observer<? super Results> observer) {

                }
            };
        return observable;
    }
    private int getUserID() {
        SharedPreferences sharedPreferences=context.getSharedPreferences("registration",context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_REGISTER_ID,-1);
      //  return 43;
    }

}
