package com.example.mycards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mycards.intro.IntroActivity;
import com.example.mycards.utilits.LanguageUtilits;
import com.fooda.mycards.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.mycards.utilits.Constants.TYPE;

public class LanguageActivity extends AppCompatActivity {
    private LanguageUtilits utilits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        utilits=new LanguageUtilits(this,this);
        sweetDialog();

    }
    private void sweetDialog(){

        new SweetAlertDialog(LanguageActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setCustomImage(R.drawable.logo)
                .setTitleText(getResources().getString(R.string.language))
                .setContentText(getResources().getString(R.string.select_language))
                .setConfirmText(getResources().getString(R.string.Arabic))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        utilits.setLocale("ar");
                        goToHome();
                    }
                })
                .setCancelButton(getResources().getString(R.string.English), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        utilits.setLocale("en");
                        goToHome();
                    }
                })
                .show();




    }
    private void goToHome(){
        startActivity(new Intent(LanguageActivity.this, IntroActivity.class).putExtra(TYPE,"intro"));
        finish();
    }
}