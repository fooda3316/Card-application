package com.example.mycards.api;



import com.example.mycards.models.Pages;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Belal on 14/04/17.
 */

public interface PageAPI {


  @GET("pages")
  Observable<Pages> getAllPages();

}
