package com.example.mycards.app;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycards.internet.CheckConnection;

public class BaseActivity extends AppCompatActivity {
    private CheckConnection connection;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        connection=new CheckConnection(this);
        Log.e("onCreate","super onCreate has invoked");
    }
}
