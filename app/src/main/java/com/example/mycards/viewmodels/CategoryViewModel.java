package com.example.mycards.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.mycards.models.Category;
import com.example.mycards.models.Results;
import com.example.mycards.repsatories.CategoryRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CategoryViewModel extends AndroidViewModel {
        private CategoryRepository repository = null;
        public MutableLiveData<List<Category>> categoryLiveData;
        private Observer<Results> observer;
        public CategoryViewModel(@NonNull Application application) {
                super(application);
            categoryLiveData  = new MutableLiveData<>();

        }

    public MutableLiveData<List<Category>> CategoriesLiveData(){
        repository = new CategoryRepository( );
                try {
            observer = new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("onSubscribe",d.toString());
                    }

                    @Override
                    public void onNext(Results value) {
                   //         Log.e("onNext",value.getCategories().get(2).getTitle().toString());
                       categoryLiveData.setValue(value.getCategories());
                    }

                    @Override
                    public void onError(Throwable e) {
                            Log.e("categoryOnError",e.toString());


                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete","onComplete");

                    }
            };}

                catch (Exception e){
                        Log.e("Exception",e.getMessage());
                }
        Log.e("is Observer null", String.valueOf(observer==null));
                try {

            repository.getObservable().subscribe(observer);
                        Log.e("is getObservable null", String.valueOf(repository.getObservable()==null));
                }
                 catch (Exception e){
                    Log.e("repository Exception",e.getMessage());
            }
            return categoryLiveData;
    }
}
