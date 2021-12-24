package com.example.mycards.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.mycards.models.Results;
import com.example.mycards.models.WalletInfo;
import com.example.mycards.repsatories.WalletInfoREPO;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WalletInfoVM extends AndroidViewModel {
        private WalletInfoREPO repository = null;
        public MutableLiveData<List<WalletInfo>> usersLiveData;
        private Observer<Results> observer;
        public WalletInfoVM(@NonNull Application application) {
                super(application);
                usersLiveData  = new MutableLiveData<>();

        }

    public MutableLiveData<List<WalletInfo>> getCardsLiveData(Context context){
        repository = new WalletInfoREPO(context);
            observer = new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("cards onSubscribe",d.toString());

                    }

                    @Override
                    public void onNext(Results value) {
                        Log.e("wallet error", String.valueOf(value==null));
                        assert value != null;
                        //Log.e("onNext SN", value.toString());
                    //    Log.e("wallet onNext", String.valueOf(value.getWalletInfos().get(0).getBalance()));
                      usersLiveData.setValue(value.getWalletInfos());
                    }

                    @Override
                    public void onError(Throwable e) {
                            Log.e("onError sell H",e.toString());


                    }

                    @Override
                    public void onComplete() {
                     //   Log.e("onComplete","onComplete");
                    }
            };
                try {
            repository.getObservable().subscribe(observer);
                        Log.e("is getObservable null", String.valueOf(repository.getObservable()==null));
                }
                 catch (Exception e){
                    Log.e("repository Exception",e.getMessage());
            }
            return usersLiveData;
    }
}
