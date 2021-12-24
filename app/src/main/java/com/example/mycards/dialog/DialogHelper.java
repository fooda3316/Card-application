package com.example.mycards.dialog;

import android.content.Context;

import com.fooda.mycards.R;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

public class DialogHelper {
    private LoadingDialog loadingDialog;
    private Context context;

    public DialogHelper(Context context) {
        this.context = context;
        loadingDialog= new LoadingDialog(context);
    }

    public void showLoadingDialog() {


        loadingDialog.setLoadingText(context.getResources().getString(R.string.progress_wait));
        loadingDialog.show();

    }


    public void dismissLoadingDialog() {
        loadingDialog.close();
    }
}
