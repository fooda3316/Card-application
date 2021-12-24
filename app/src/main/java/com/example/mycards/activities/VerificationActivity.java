package com.example.mycards.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fooda.mycards.R;
import static com.example.mycards.utilits.Constants.IS_USER_REGISTERED;
import static com.example.mycards.utilits.Constants.USER_REGISTER_ID;
import static com.example.mycards.utilits.Constants.VERIFICATION;
import static com.example.mycards.utilits.Constants.VERIFICATION_KEY;
import static com.example.mycards.utilits.Constants.VERIFICATION_VAL_KEY;

public class VerificationActivity extends AppCompatActivity {
    private Button check,skip,tryAgain;
    private EditText editText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        check=findViewById(R.id.btn_continue);
        skip=findViewById(R.id.btn_skip);
        tryAgain=findViewById(R.id.btn_try_again);
        editText=findViewById(R.id.txt_input);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(VerificationActivity.this,HomeActivity.class));
            }
        });
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(VerificationActivity.this,LogInActivity.class));
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=editText.getText().toString().trim();
                if (code.equals(getCode())){
                    finish();
                    startActivity(new Intent(VerificationActivity.this,HomeActivity.class));
                   // saveVerification();
                    saveLogin();
                }
                else {
                    editText.setError("عفواً الكود الذي ادخلتة غير صحيح الرجاء المحاولة من جديد");
                }

            }
        });
    }

    private void saveVerification() {
        SharedPreferences sharedPreferences=getSharedPreferences(VERIFICATION,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(VERIFICATION_KEY,true);
        editor.apply();
    }

    private String getCode (){
        SharedPreferences sharedPreferences=getSharedPreferences(VERIFICATION,MODE_PRIVATE);
      return   sharedPreferences.getString(VERIFICATION_VAL_KEY,"");
    }
    private void saveLogin() {
        SharedPreferences sharedPreferences=getSharedPreferences("registration",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_USER_REGISTERED,true);
        editor.apply();
    }
}
