package com.example.mycards.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mycards.dialog.DialogHelper;
import com.fooda.mycards.R;
import com.example.mycards.api.APIService;
import com.example.mycards.email.GMailSender;
import com.example.mycards.models.Result;
import com.example.mycards.network.RetrofitClient;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mycards.utilits.Constants.IS_USER_REGISTERED;
import static com.example.mycards.utilits.Constants.USER_REGISTER_ID;
import static com.example.mycards.utilits.Constants.VERIFICATION;
import static com.example.mycards.utilits.Constants.VERIFICATION_VAL_KEY;

public class PasswordActivity extends AppCompatActivity {

    private EditText email,code,newPass;
    private Button ok,pContinue,changePass;
    private TextView textView;
    private Call <Result> getEmail,changePassword;
    private APIService apiService;
    private MaterialDialog mDialog;
    private String randomVal;
    private DialogHelper dialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        email=findViewById(R.id.txt_input_email);
        code=findViewById(R.id.txt_input_code);
        newPass=findViewById(R.id.txt_input_nwe_pass);
        ok=findViewById(R.id.btn_ok);
        pContinue=findViewById(R.id.btn_continue);
        textView=findViewById(R.id.txt_massage);
        changePass=findViewById(R.id.btn_change_pass);
        dialogHelper=new DialogHelper(PasswordActivity.this);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        apiService= RetrofitClient.getInstance().getApiService();
        testApdate();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (email.getText().toString().isEmpty()){
               email.setError("لا يمكن ترك هذا الحقل فارغاً ");
              }
              else {
                  showLoadingDialog();
                  getEmail =apiService.isUserExist(email.getText().toString().trim());
                  getEmail.enqueue(new Callback<Result>() {
                      @Override
                      public void onResponse(Call<Result> call, Response<Result> response) {

                          if (!response.isSuccessful()) {
                              Log.e("error code","Code: " + response.code());
                              return;
                          }
                          if (response.body().getError()) {
                              dismissLoadingDialog();
                            textView.setVisibility(View.VISIBLE);
                            email.setVisibility(View.GONE);
                            ok.setVisibility(View.GONE);
                          }
                          else {
                              final int min = 1111;
                              final int max = 9999;
                              final int random = new Random().nextInt((max - min) + 1) + min;
                               randomVal= String.valueOf(random);
                              Log.e("randomVal","randomVal is "+randomVal);

                              new GMailSender(PasswordActivity.this).
                               sendMessage(response.body().getUser().getEmail(),"كود التحفف من سودا كارد","\n مرحبا : "+response.body().getUser().getName()+"كود التحقق الخاص بك هو : "+randomVal);
                             saveVerificationVal(randomVal);
                              textView.setVisibility(View.VISIBLE);
                              textView.setText("نرجو ادخال رمز التحقق الذي تم ارسالة الى الايميل الخاص بك");
                              email.setVisibility(View.GONE);
                              code.setVisibility(View.VISIBLE);
                              ok.setVisibility(View.GONE);
                              pContinue.setVisibility(View.VISIBLE);
                              saveUserId(response.body().getUser().getId());
                              dismissLoadingDialog();
                          }
                        //  Log.e("mesg",response.body().getMessage());

                      }

                      @Override
                      public void onFailure(Call<Result> call, Throwable t) {
                          dismissLoadingDialog();
                      }
                  });
                  Log.e("getCode","code is "+getCode());
                  pContinue.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if (code.getText().toString().isEmpty()){
                              code.setError("لا يمكن ترك هذا الحقل فارغاً");
                          }
                          else {
                              Log.e("intered code",code.getText().toString().trim());
                              if  (code.getText().toString().trim().equals(randomVal)){
                                  textView.setVisibility(View.VISIBLE);
                                  textView.setText("أدخل كلمة السر الجديدة");
                                  newPass.setVisibility(View.VISIBLE);
                                  changePass.setVisibility(View.VISIBLE);
                                  code.setVisibility(View.GONE);
                                  pContinue.setVisibility(View.GONE);
                              }
                              else {
                                  code.setError("عفواً الكود الذي ادخلتة غير صحيح");
                              }
                          }
                      }
                  }); int id=getUserID();
                  String pass=newPass.getText().toString().trim();
                  changePass.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          if (changePass.getText().toString().isEmpty()){
                              changePass.setError("لا يمكن ترك هذا الحقل فارغاً");
                          }

                          else {
                              showLoadingDialog();
                              Log.e("info","id is "+getUserID()+" pass is "+newPass.getText().toString().trim());
                              apiService.updatePassword(id,newPass.getText().toString().trim()).enqueue(new Callback<Result>() {
                                  @Override
                                  public void onResponse(Call<Result> call, Response<Result> response) {
                                      dismissLoadingDialog();
                                      if (!response.isSuccessful()) {
                                          Log.e("error code","Code: " + response.code());
                                          textView.setText("حدث خطأ في النظام");
                                          return;
                                      }
                                      if (response.body().getError()) {
                                          textView.setText("حدث خطأ نرجو المحاولة من جديد");
                                          Log.e("getError",response.body().getMessage());
                                      }
                                      else {
                                          saveLogin();
                                          finish();
                                          startActivity(new Intent(PasswordActivity.this,HomeActivity.class));

                                      }
                                  }

                                  @Override
                                  public void onFailure(Call<Result> call, Throwable t) {
                                      dismissLoadingDialog();
                                      textView.setText("حدث خطأ نرجو المحاولة من جديد");

                                  }
                              });

                          }

                      }
                  });
              }

            }
        });


    }

    private void testApdate() {
       apiService.updatePassword(17,"1234").enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {
                   // textView.setText("حدث خطأ نرجو المحاولة من جديد");
                    Log.e("getError",response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    private int getUserID() {
        SharedPreferences sharedPreferences=getSharedPreferences("registration",MODE_PRIVATE);
        return sharedPreferences.getInt(USER_REGISTER_ID,-1);
    }
    private void saveLogin() {
        SharedPreferences sharedPreferences=getSharedPreferences("registration",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_USER_REGISTERED,true);
        editor.apply();
    }
    private void saveVerificationVal(String val) {
        SharedPreferences sharedPreferences=getSharedPreferences(VERIFICATION,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(VERIFICATION_VAL_KEY,val);
        editor.apply();
    }
    private void saveUserId(int userId) {
        SharedPreferences sharedPreferences=getSharedPreferences("registration",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(USER_REGISTER_ID,userId);
        editor.apply();
    }
    private String getCode (){
        SharedPreferences sharedPreferences=getSharedPreferences(VERIFICATION,MODE_PRIVATE);
        return   sharedPreferences.getString(VERIFICATION_VAL_KEY,"");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void showLoadingDialog() {
        dialogHelper.showLoadingDialog();
    }
    public void dismissLoadingDialog() {
        dialogHelper.dismissLoadingDialog();
    }
}