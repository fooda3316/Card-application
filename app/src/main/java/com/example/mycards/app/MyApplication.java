package com.example.mycards.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
//import com.facebook.drawee.backends.pipeline.Fresco;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.util.Locale;

import static com.example.mycards.utilits.Constants.LANG_KEY;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private int q = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        instantiateManagers();
    }

    /**
     * Method to instantiate all the managers in this app
     */
    private void instantiateManagers() {
//        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
//            @Override
//            public void onInitialized() {
//                if(AccessToken.getCurrentAccessToken() == null){
//                    System.out.println("not logged in yet");
//                } else {
//                    System.out.println("Logged in");
//                }
//            }
//        });
        FacebookSdk.sdkInitialize(this);
        //Fresco.initialize(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}