package com.example.mycards.utilits;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.mycards.activities.WalletInfoActivity;
import com.fooda.mycards.R;


public class DialogPresenter {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public DialogPresenter(Context context) {
        DialogPresenter.context = context;
    }

    public static void showAlert(String message) {
        AlertDialog.Builder alert= new AlertDialog.Builder(context);
        alert.setMessage(message)
                .setIcon(R.drawable.logo)
                .setTitle(context.getResources().getString(R.string.alert_title))
                .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                       // context.startActivity(new Intent(context, WalletInfoActivity.class));
                    }
                }).show();
    }

    public static void showSuccessAlert(String message) {
        AlertDialog.Builder alert= new AlertDialog.Builder(context);
        alert.setMessage(message)
                .setIcon(android.R.drawable.stat_notify_error)
                .setTitle(context.getResources().getString(R.string.alert_title))
                .setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                         context.startActivity(new Intent(context, WalletInfoActivity.class));


                    }
                }).show();
    }
}
