package com.example.mycards.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mycards.models.Bank;
import com.example.mycards.models.Banks;
import com.example.mycards.repsatories.BanksRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BanksViewModel extends AndroidViewModel {
        private BanksRepository repository = null;
        public MutableLiveData<List<Bank>> liveData;
        private Observer<Banks> observer;
        public BanksViewModel(@NonNull Application application) {
                super(application);
                liveData = new MutableLiveData<>();

        }

    public MutableLiveData<List<Bank>> getCardsLiveData(){
        repository = new BanksRepository();
                try {


            observer = new Observer<Banks>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("cards onSubscribe",d.toString());

                    }

                    @Override
                    public void onNext(Banks value) {
//                            Log.e("onNext",value.getBanks().get(2).getBankName());
                      if (null!=value){
                          liveData.setValue(value.getBanks());}
                    }

                    @Override
                    public void onError(Throwable e) {
                            Log.e("onErrorObserver",e.toString());


                    }

                    @Override
                    public void onComplete() {
                     //   Log.e("onComplete","onComplete");
                    }
            };}
                catch (Exception e){
                        Log.e("Exception",e.getMessage());
                }
                try {
            repository.getObservable().subscribe(observer);
                        Log.e("is getObservable null", String.valueOf(repository.getObservable()==null));
                }
                 catch (Exception e){
                    Log.e("repository Exception",e.getMessage());
            }
            return liveData;
    }
}
