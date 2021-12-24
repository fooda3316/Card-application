package com.example.mycards.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.loader.content.CursorLoader;


import com.fooda.mycards.R;
import com.example.mycards.activities.HomeActivity;
import com.example.mycards.activities.VerificationActivity;
import com.example.mycards.api.APIService;
import com.example.mycards.email.GMailSender;
import com.example.mycards.models.Result;
import com.example.mycards.network.RetrofitClient;
import com.example.mycards.network.RetrofitLoinClient;
import com.example.mycards.utilits.DialogPresenter;

import java.io.File;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mycards.utilits.Constants.IS_USER_REGISTERED;
import static com.example.mycards.utilits.Constants.USER_REGISTER_ID;
import static com.example.mycards.utilits.Constants.VERIFICATION;
import static com.example.mycards.utilits.Constants.VERIFICATION_KEY;
import static com.example.mycards.utilits.Constants.VERIFICATION_VAL_KEY;


public class LoginPresenter {
    private LoginView mView;
    private APIService apiService;
    private Context context;
    private Activity activity;
    private DialogPresenter dialogPresenter;

    public LoginPresenter(Context context, Activity activity) {
        this.context = context;
        this.activity=activity;
        dialogPresenter =new DialogPresenter(context);
    }

    public void bind(LoginView view) {
        this.mView = view;
    }

    public void unbind() {
        mView = null;
    }




    /**
     * Calls Signup API
     *
     * @param first_name user's first name
     * @param last_name  user's last name
     * @param email     user's email id
     * @param pass      password user entered
     * @param image  image
     */
    public void ok_signUp(Uri imageUri, Context context, final String first_name, final String last_name, final String email,
                          String pass, String image, String phone) {

        mView.showLoadingDialog();
            File file = new File(getRealPathFromURI(imageUri,context));
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), first_name);
            RequestBody lastBody = RequestBody.create(MediaType.parse("text/plain"), last_name);
            RequestBody emailDBody = RequestBody.create(MediaType.parse("text/plain"),email);
            RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), pass);
            RequestBody imageBody = RequestBody.create(MediaType.parse("text/plain"), image);
            RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), phone);
            RequestBody requestFile = RequestBody.create(MediaType.parse(context.getContentResolver().getType(imageUri)), file);
            apiService= RetrofitLoinClient.getInstance().getApiService();
            Call<Result> call=apiService.registerUser(requestFile,nameBody,lastBody,emailDBody,passwordBody,imageBody,phoneBody);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (!response.isSuccessful()) {
                        mView.dismissLoadingDialog();
                        Log.e("error code","Code: " + response.code());
                        return;
                    }
                    assert response.body() != null;
                    if (!response.body().getError()){
                      //  Log.d("Success Message",response.body().getUser().getMessage());
                        //Log.e("ID is",response.body().getUser().getId()+"");

                        mView.dismissLoadingDialog();
                        saveLogin(context);
                        saveUserId(response.body().getUser().getId());
                        context.startActivity(new Intent(context, HomeActivity.class));
                    }
                    mView.dismissLoadingDialog();
                    Log.e("Message",response.body().getMessage());
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.e("onFailure",t.toString());
                    mView.dismissLoadingDialog();
                }
            });
        }




    private void saveLogin(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("registration",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_USER_REGISTERED,true);
                editor.apply();
    }
    private void saveUserId(int userId) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("registration",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(USER_REGISTER_ID,userId);
        editor.apply();
    }
    private void saveVerificationVal(String val) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(VERIFICATION,context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(VERIFICATION_VAL_KEY,val);
        editor.apply();
    }


    /**
     * Calls Login API and checks for validity of credentials
     * If yes, transfer to MainActivity
     *
     * @param email user's email id
     * @param password  password user entered
     */
    public void ok_login(String email, String password) {

        mView.showLoadingDialog();

        apiService= RetrofitClient.getInstance().getApiService();


        Call<Result> call = apiService.userLogin(email, password);

    call.enqueue(new Callback<Result>() {
        @Override
        public void onResponse(Call<Result> call, Response<Result> response) {
            if (mView != null) {
                mView.dismissLoadingDialog();
            }

            //displaying the message from the response as toast
            if (!response.body().getError()) {
               //Toast.makeText(context, context.getResources().getString(R.string.login_fialure_success), Toast.LENGTH_LONG).show();
                String email=response.body().getUser().getEmail();
                final int min = 1111;
                final int max = 9999;
                final int random = new Random().nextInt((max - min) + 1) + min;
                String randomVal= String.valueOf(random);
                new GMailSender(activity).sendMessage(email,"Verification code from Sudan mart soft","  رمز التحقق هو "+randomVal);
                saveVerificationVal(randomVal);
                activity.finish();
                //saveLogin(context);
                saveUserId(response.body().getUser().getId());
                context.startActivity(new Intent(context, VerificationActivity.class));
            } else {
                Toast.makeText(context, R.string.login_fialure_message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Result> call, Throwable t) {
            Log.e("error", t.toString());
            if (mView != null) {

                mView.dismissLoadingDialog();
                activity.finish();
            }
        }
    });
}
    private String getRealPathFromURI(Uri contentUri, Context context) {
        Log.e("getRealPath","getRealPath");
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    }

