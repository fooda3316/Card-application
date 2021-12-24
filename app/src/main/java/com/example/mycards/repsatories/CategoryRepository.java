package com.example.mycards.repsatories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.example.mycards.models.Category;
import com.example.mycards.models.Results;
import com.example.mycards.network.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryRepository {
    public MutableLiveData<List<Category>> categoryLiveData;
    Observable<Results> observable;


    public CategoryRepository() {
        categoryLiveData  = new MutableLiveData<>();
        try {


        observable= RetrofitClient.getInstance()
                .getCategoryService().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());}
        catch (Exception ex){
            Log.e("Exception",ex.toString());
        }

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
}
