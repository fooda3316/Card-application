package com.example.mycards.api;



import com.example.mycards.models.Banks;
import com.example.mycards.models.CardResult;
import com.example.mycards.models.Cards;
import com.example.mycards.models.Pages;
import com.example.mycards.models.Result;
import com.example.mycards.models.Results;
import com.example.mycards.models.Social;
import com.example.mycards.models.Users;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Belal on 14/04/17.
 */

public interface APIService {
    @GET("getAppVersion")
    Call<Result> getAppVersion();

    @FormUrlEncoded
    @POST("requestCard")
    Call<Result> requestCard(
            @Field("name") String title,
            @Field("subName") String subName,
            @Field("branch") String branch,
            @Field("date") String date);
    @GET("facebook")
    Call<Social> getFacebook();
    @GET("twitter")
    Call<Social> getTwitter();
    @GET("youtube")
    Call<Social> getYoutube();
    @GET("inst")
    Call<Social> getInst();
    @GET("whatsApp")
    Call<Social> getWhatsApp();
    @GET("banks")
    Observable<Banks> getAllbanks();
    @FormUrlEncoded
    @POST("registerUser")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("image") String image);
    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("isUserExist")
    Call<Result> isUserExist(
            @Field("email") String email
    );
    @FormUrlEncoded
    @POST("saveChargeRequest")
    Call<Result> saveChargeRequest(
            @Field("userId") int userId,
            @Field("image") String image
    );

    @GET("getSellHistory/{userId}")
    Observable<Results> getSellHistory(
            @Path("userId") int userId
    );
    @GET("getWalletInfo/{userId}")
    Observable<Results> getWalletInfo(
            @Path("userId") int userId
    );
    @GET("getUnfinishedRequests")
    Observable<Results> getUnfinishedRequests(

    );
    @GET("getBranches/{name}")
    Call<Results> getBranch(
            @Path("name") String name
    );
    @GET("getVerificationNumber")
    Call<Result> getVerificationNumber();
    @FormUrlEncoded
    @PUT("updatePassword")
    Call<Result> updatePassword(
            @Field("id") int id,
            @Field("password") String password

    );
    @Multipart
    @POST("uploadImage.php")
    Call<Result> uploadImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
                             @Part("desc") RequestBody desc,
                             @Part("type") RequestBody type);

    @Multipart
    @POST("uploadImageText.php")
    Call<Result> uploadPicture(
            @Part("userPhoto\"; filename=\"myfile.jpg\" ") RequestBody file,
            @Part("userId") RequestBody userId,
            @Part("operation") RequestBody operation,
            @Part("imageName") RequestBody imageName);
    @Multipart
    @POST("registeration")
    Call<Result> registerUser(
            @Part("userPhoto\"; filename=\"myfile.jpg\" ") RequestBody file,
            @Part("name") RequestBody name,
            @Part("last") RequestBody last,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("image") RequestBody image,
            @Part("phone") RequestBody phone);


    @GET("ParticularUser/{id}")
    Call<Users> getParticularUser(@Path("id") int id);
    @FormUrlEncoded
    @POST("subCards")
    Observable<Cards> getSubCards(@Field("name") String name,
                                  @Field("branch") String branch
    );
    @GET("ParticularCards/{categoryId}")
    Observable<Cards> getParticularCards(@Path("categoryId") int categoryId);

    @FormUrlEncoded
    @POST("buyCard")
    Call<CardResult> buyCard(
            @Query("subName")String subName,
            @Query("branch")String branch,
            @Field("cardName") String cardName,
            // @Query("userId")int userId
            @Field("userId") int userId,
            @Field("date") String date,
            @Field("image") String image



    );
    @GET("social")
    Observable<Social> getSocials();
  @GET("categories")
  Observable<Results> getCategories();
    @Multipart
    @POST("upload/upload.php")
    Call<String> uploadedFile(@Part MultipartBody.Part file);
}
