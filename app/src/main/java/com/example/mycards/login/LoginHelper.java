package com.example.mycards.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mycards.dialog.DialogHelper;
import com.fooda.mycards.R;
import com.example.mycards.activities.HomeActivity;
import com.example.mycards.api.APIService;
import com.example.mycards.models.Result;
import com.example.mycards.models.UserMeta;
import com.example.mycards.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mycards.utilits.Constants.IS_USER_FACEBOOK_REGISTERED;
import static com.example.mycards.utilits.Constants.IS_USER_REGISTERED;
import static com.example.mycards.utilits.Constants.USER_REGISTER_ID;
import static com.example.mycards.utilits.Constants.USER_REGISTER_IMAGE;

public class LoginHelper {
    private UserMeta userMeta;
    private Activity activity;
    private APIService apiService;
    private MaterialDialog mDialog;
    private DialogHelper dialogHelper;
    public LoginHelper(UserMeta userMeta, Activity activity) {
        Log.e("user info"," userName"+ userMeta.userName+" Email "+ userMeta.userEmail);
        this.userMeta = userMeta;
        this.activity = activity;
        apiService= RetrofitClient.getInstance().getApiService();
        dialogHelper=new DialogHelper(activity);
    }

    private Call<Result> call;
    public void login(){
        //showLoadingDialog();
     call=apiService.createUser(userMeta.userName,userMeta.userEmail,userMeta.profilePic);
     call.enqueue(new Callback<Result>() {
         @Override
         public void onResponse(Call<Result> call, Response<Result> response) {
            // Toast.makeText(activity, "نأسف حدث خطأ اثناء ", Toast.LENGTH_LONG).show();

             dismissLoadingDialog();
             if (!response.isSuccessful()) {
                 Toast.makeText(activity, "نأسف حدث خطأ اثناء التسجيل يرجى المحاولة من جديد", Toast.LENGTH_LONG).show();
                 dismissLoadingDialog();
                 Log.e("error code","Code: " + response.code());
                 return;
             }
             assert response.body() != null;
             if (!response.body().getError()) {
                 saveUserId(response.body().getUser().getId());
                 Toast.makeText(activity, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();

                 saveLogin();
                 saveFacebookLogin();
                 saveImageUri(userMeta.profilePic);
                 activity.startActivity(new Intent(activity, HomeActivity.class));
               dismissLoadingDialog();


             }
             else {
                 Toast.makeText(activity, "نأسف حدث خطأ اثناء التسجيل يرجى المحاولة من جديد", Toast.LENGTH_LONG).show();

             }
             }

         @Override
         public void onFailure(Call<Result> call, Throwable t) {
             dismissLoadingDialog();
             Log.e("onFailure",t.toString());
             Toast.makeText(activity, " نأسف حدث خطأ اثناء التسجيل يرجى المحاولة من جديد"+t.toString(), Toast.LENGTH_LONG).show();
         }
     });
    }
    private void saveLogin() {
        SharedPreferences sharedPreferences=activity.getSharedPreferences("registration",activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_USER_REGISTERED,true);
        editor.apply();
    }
    private void saveFacebookLogin() {
        SharedPreferences sharedPreferences=activity.getSharedPreferences("registration",activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_USER_FACEBOOK_REGISTERED,true);
        editor.apply();
    }
    private void saveUserId(int userId) {
        SharedPreferences sharedPreferences=activity.getSharedPreferences("registration",activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(USER_REGISTER_ID,userId);
        editor.apply();
    }
        private void saveImageUri(String uri) {
        SharedPreferences sharedPreferences=activity.getSharedPreferences("registration",activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_REGISTER_IMAGE,uri);
        editor.apply();
    }
    public void showLoadingDialog() {
        dialogHelper.showLoadingDialog();
    }
    public void dismissLoadingDialog() {
        dialogHelper.dismissLoadingDialog();
    }
}
