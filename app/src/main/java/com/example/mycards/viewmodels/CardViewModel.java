package com.example.mycards.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.mycards.models.Card;
import com.example.mycards.models.Cards;
import com.example.mycards.repsatories.CardRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CardViewModel extends AndroidViewModel {
        private CardRepository repository = null;
        public MutableLiveData<List<Card>> usersLiveData;
        private Observer<Cards> observer;
        public CardViewModel(@NonNull Application application) {
                super(application);
                usersLiveData  = new MutableLiveData<>();

        }

    public MutableLiveData<List<Card>> getCardsLiveData(int id){
        repository = new CardRepository(id );
                try {


            observer = new Observer<Cards>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("cards onSubscribe",d.toString());

                    }

                    @Override
                    public void onNext(Cards value) {
                        //    Log.e("onNext",value.getCards().get(2).getTitle().toString());
                       usersLiveData.setValue(value.getCards());
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
            return usersLiveData;
    }
}
