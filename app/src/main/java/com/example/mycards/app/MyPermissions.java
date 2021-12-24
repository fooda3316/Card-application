package com.example.mycards.app;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.example.mycards.utilits.Constants.PERMISSION_REQUEST_CODE;


public class MyPermissions {
    private Activity activity;

    public MyPermissions(Activity activity) {
        this.activity=activity;
    }
    public boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(activity,permission )
                != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        return true;
    }
    public void requestPermission(String permission) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permission},
                PERMISSION_REQUEST_CODE);
    }
}
