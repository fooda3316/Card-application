package com.example.mycards.login;

import android.app.Activity;

import com.example.mycards.email.GMailSender;

public class LoginVerification {
    private GMailSender gMailSender;
    private Activity activity;

    public LoginVerification(Activity activity) {
        this.activity = activity;
        gMailSender=new GMailSender(activity);
    }
    public void verificationLogin(){

    }
}
