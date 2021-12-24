package com.example.mycards.utilits;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.fooda.mycards.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public  class SharingConnection extends Intent {
    public Context context;
    private static final String WHATS_APP = "http://project-travel-mate.github.io/Travel-Mate/";
    private static final String INST = "http://project-travel-mate.github.io/Travel-Mate/";
    private static final String FACEBOOK = "https://www.facebook.com/Suda-Card-866291140395378/";
    private static final String TWITTER = "http://project-travel-mate.github.io/Travel-Mate/";
    private static final String YOUTUBE = "http://project-travel-mate.github.io/Travel-Mate/";

    private static final String WEBSITE = "http://project-travel-mate.github.io/Travel-Mate/";
    public SharingConnection(Context context) {
        this.context = context;
    }


    public void MoreApp() {
        Intent morapp = new Intent(Intent.ACTION_VIEW);
        morapp.setData(Uri.parse("https://play.google.com/store/apps/developer?id=youraddress"));
        context.startActivity(morapp);

    }

    public void RateApp() {
        Intent Rate_App = new Intent(Intent.ACTION_VIEW);
        Rate_App.setData(Uri.parse("market://details?id="+context.getPackageName()));
        context.startActivity(Rate_App);
    }

    public  void ShareApp() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, context.getString( R.string.app_name) + "\n" + "https://play.google.com/store/apps/details?id="+context.getPackageName());
        Intent.createChooser(share, "مشاركة التطبيق");
        context.startActivity(share);

    }



    public  void Share_TXT(String textTOshare) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT,textTOshare );
        Intent.createChooser(share, "مشاركة نص");
        context.startActivity(share);
      }



    public void showAppInfo() {

        try {
            InputStream inputStream = context.getAssets().open("help.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader BR = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder msg = new StringBuilder();
            while ((line = BR.readLine()) != null) {
                msg.append(line + "\n");
            }
            AlertDialog.Builder build = new AlertDialog.Builder(context);
            build.setTitle("مساعدة");
            build.setIcon(R.mipmap.ic_launcher);
            build.setMessage(Html.fromHtml(msg + ""));
            build.setNegativeButton("إغلاق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //Negative
                }
            }).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void update() {
    }

    public void twitter(String twitter){
        Intent morapp = new Intent(Intent.ACTION_VIEW);
        morapp.setData(Uri.parse(twitter));

        try {
            context.startActivity(morapp);
        }
        catch (Exception e){
            Toast.makeText(context, "عفواً حدث خطأ اثناء فتح الصفحة", Toast.LENGTH_SHORT).show();
        }
    }
    public void inst(String inst){
        Intent morapp = new Intent(Intent.ACTION_VIEW);
        morapp.setData(Uri.parse(inst));
        try {
            context.startActivity(morapp);
        }
        catch (Exception e){
            Toast.makeText(context, "عفواً حدث خطأ اثناء فتح الصفحة", Toast.LENGTH_SHORT).show();
        }

    }
    public void goTo(String uri) {
        Log.e("facebook in",uri);
        Intent goToIntent = new Intent(Intent.ACTION_VIEW);
        goToIntent.setData(Uri.parse(uri));
     try {
         context.startActivity(goToIntent);
     }
catch (Exception e){
    Toast.makeText(context, "عفواً حدث خطأ اثناء فتح الصفحة", Toast.LENGTH_SHORT).show();
}
    }
    public void youtube(String youtube){
        Intent morapp = new Intent(Intent.ACTION_VIEW);
        morapp.setData(Uri.parse(youtube));
        try {
            context.startActivity(morapp);
        }
        catch (Exception e){
            Toast.makeText(context, "عفواً حدث خطأ اثناء فتح الصفحة", Toast.LENGTH_SHORT).show();
        }

    }

    public void github() {
        Intent viewIntent =
                new Intent(Intent.ACTION_VIEW, Uri.parse(WEBSITE));
        context.startActivity(viewIntent);
    }
    public void  whatsApp(String whatsApp) {
        Intent viewIntent =
                new Intent(Intent.ACTION_VIEW, Uri.parse(whatsApp));
        try {
            context.startActivity(viewIntent);
        }
        catch (Exception e){
            Toast.makeText(context, "عفواً حدث خطأ اثناء فتح الصفحة", Toast.LENGTH_SHORT).show();
        }

    }
}
