package com.example.mycards.api;


import com.example.mycards.models.Results;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Belal on 14/04/17.
 */

public interface CategoryAPI {


  @GET("categories")
  Observable<Results> getCategories();
}
