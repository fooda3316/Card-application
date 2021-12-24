package com.example.mycards.api;


import com.example.mycards.models.CardResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Belal on 14/04/17.
 */

public interface CardAPIService {


    @FormUrlEncoded
    @POST("buyCard")
    Call<CardResult> buyCard(
                             @Field("subName")String subName,
                             @Field("branch") String branch,
                             @Field("cardName") String cardName,
                             @Field("userId") int userId,
                             @Field("date") String date,
                             @Field("cardId") int cardId

    );

}
