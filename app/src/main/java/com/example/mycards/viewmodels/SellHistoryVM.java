package com.example.mycards.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.mycards.models.History;
import com.example.mycards.models.Results;
import com.example.mycards.repsatories.SellHistoryRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SellHistoryVM extends AndroidViewModel {
        private SellHistoryRepository repository = null;
        public MutableLiveData<List<History>> usersLiveData;
        private Observer<Results> observer;
        public SellHistoryVM(@NonNull Application application) {
                super(application);
                usersLiveData  = new MutableLiveData<>();

        }

    public MutableLiveData<List<History>> getCardsLiveData(Context context){
        repository = new SellHistoryRepository(context);
            observer = new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("cards onSubscribe",d.toString());

                    }

                    @Override
                    public void onNext(Results value) {
                        Log.e("error", String.valueOf(value==null));
                        assert value != null;
//                        Log.e("onNext SN", String.valueOf(value.getHistories().get(0).getSerialNumber()));
                      usersLiveData.setValue(value.getHistories());
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
