package com.example.mycards.themes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fooda.mycards.R;


public class SetThemes {
    private static SharedPreferences themShard;
    private static int myStyle;


    public static void setThemes(AppCompatActivity activity){
       themShard = activity.getSharedPreferences("Setting", Context.MODE_PRIVATE);
       myStyle = themShard.getInt("myStyle", 0);
       switch (myStyle) {
           case 0:
               activity.setTheme(R.style.AppTheme);
               break;
           case 1:
               activity.setTheme(R.style.AppTheme3);
               break;
           case 2:
               activity.setTheme(R.style.AppTheme);
               break;
           case 3:
               activity.setTheme(R.style.AppTheme1);
               break;
           case 4:
               activity.setTheme(R.style.AppTheme4);
               break;
           case 5:
               activity.setTheme(R.style.AppTheme2);
               break;
       }
    }
    public static void setLayoutBackGround(LinearLayout linearLayout) {
        switch (myStyle){
            case 0:
                linearLayout.setBackgroundColor(Color.YELLOW);
                break;
            case 1:
                linearLayout.setBackgroundColor(Color.YELLOW);

                break;
            case 2:
                linearLayout.setBackgroundColor(Color.BLUE);;
                break;
            case 3:
                linearLayout.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                linearLayout.setBackgroundColor(Color.DKGRAY);
                break;
            case 5:
                linearLayout.setBackgroundColor(Color.YELLOW);
                break;

        }
    }
    public static void setImageBackGround(ImageView imageView) {
        switch (myStyle){
            case 0:

                break;
            case 1:
                imageView.setBackgroundColor(Color.YELLOW);

                break;
            case 2:
                imageView.setBackgroundColor(Color.BLUE);;
                break;
            case 3:
                imageView.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                imageView.setBackgroundColor(Color.DKGRAY);
                break;
            case 5:
                imageView.setBackgroundColor(Color.YELLOW);
                break;

        }
    }
    @SuppressLint("ResourceAsColor")
    public static void ChangeToolbarColor(Toolbar toolbar, AppCompatActivity activity) {
        themShard = activity.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        myStyle = themShard.getInt("myStyle", 0);
        switch (myStyle) {
            case 0:
                toolbar.setBackgroundColor((Color.parseColor("#3700B3")));
                break;
            case 1:
                toolbar.setBackgroundColor((Color.parseColor("#090909")));

                break;
            case 2:
                toolbar.setBackgroundColor((Color.parseColor("#6200EE")));


                break;
            case 3:
                toolbar.setBackgroundColor((Color.parseColor("#009311")));

                break;
            case 4:
                toolbar.setBackgroundColor((Color.parseColor("#64015F")));

                break;
            case 5:
                toolbar.setBackgroundColor((Color.parseColor("#F8F402")));

                break;
        }
    }
    public static void ChangeColor(TextView textView, AppCompatActivity activity) {
        switch (myStyle) {
            case 0:
                textView.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 1:
                textView.setTextColor(activity.getResources().getColor(R.color.blue_color));
                break;
            case 2:
                textView.setTextColor(activity.getResources().getColor(R.color.color_1_1));
                break;
            case 3:
                textView.setTextColor(activity.getResources().getColor(R.color.color_5_1));
                break;
            case 4:
                textView.setTextColor(activity.getResources().getColor(R.color.purple_color));
                break;
            case 5:
                textView.setTextColor(activity.getResources().getColor(R.color.color_2_1));
                break;
        }
    }
}
