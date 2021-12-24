package com.example.mycards.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.mycards.anim.IntroVideoView;
import com.example.mycards.intro.IntroActivity;
import com.example.mycards.themes.SetThemes;
import com.example.mycards.utilits.DialogPresenter;
import com.example.mycards.utilits.LanguageUtilits;
import com.fooda.mycards.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;

import static com.example.mycards.utilits.Constants.TYPE;
import static com.example.mycards.utilits.Constants.VERIFICATION;
import static com.example.mycards.utilits.Constants.VERIFICATION_KEY;


public class SplashActivity extends AppCompatActivity {
    private SharedPreferences themShard;
    TextView textView;
    private SetThemes setThemes;
    private DialogPresenter dialogPresenter;
    private IntroVideoView introVideoView;
    private ImageView imageView;
    private static final String TAG = "SplashActivity";



    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Observable<Long> cold=Observable.intervalRange(0,5,0,1, TimeUnit.SECONDS);
        cold.subscribe(i-> Log.e(TAG, "onCreate: "+i ));
          //  SetThemes.setThemes(this);

        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.txtSplash);
        introVideoView=findViewById(R.id.pathView);
        imageView=findViewById(R.id.image_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            introVideoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }
//        getSupportActionBar().hide();
        dialogPresenter=new DialogPresenter(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFirstLunch()){
                    createShortCut();
                    startActivity(new Intent(SplashActivity.this,LanguageActivity.class));

                }
                else {
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));

                  /*  if (isVerificated()){
                        startActivity(new Intent(SplashActivity.this,HomeActivity.class));

                    }
                    else {
                        startActivity(new Intent(SplashActivity.this,VerificationActivity.class));

                    }*/

                }

//                Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                SplashActivity.this.startActivity(i);
               // checkIfUserSigned(SplashActivity.this);
                SplashActivity.this.finish();
            }
        }, 3000);

    }

    private boolean isVerificated() {
        SharedPreferences sharedPreferences=getSharedPreferences(VERIFICATION, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(VERIFICATION_KEY,false);
    }


    public void createShortCut(){
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.app_logo);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), SplashActivity.class));
        sendBroadcast(shortcutintent);
        Toast.makeText(this, "تم انشاء اختصار للتطبيق", Toast.LENGTH_SHORT).show();
        confirmSigning(true);
    }
    private Boolean isFirstLunch(){
        SharedPreferences sharedPreferences=getSharedPreferences("FirstLunch", Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean("isFirstLunch",false);
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void confirmSigning(Boolean confirm){
        SharedPreferences sharedPreferences=getSharedPreferences("FirstLunch",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isFirstLunch",confirm);
        editor.apply();

    }

}
