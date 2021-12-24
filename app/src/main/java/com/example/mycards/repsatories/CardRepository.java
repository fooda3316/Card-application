package com.example.mycards.repsatories;




import com.example.mycards.models.Cards;
import com.example.mycards.network.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardRepository {
    Observable<Cards> observable;

    public CardRepository(int id) {
int cardID=id+1;
        observable= RetrofitClient.getInstance()
                .getApiService().getParticularCards(cardID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }


    public Observable<Cards> getObservable() {
      //  Log.d("is null", String.valueOf(observable==null));
        if (observable==null)
            return new Observable<Cards>() {
                @Override
                protected void subscribeActual(Observer<? super Cards> observer) {

                }
            };
        return observable;
    }
}
