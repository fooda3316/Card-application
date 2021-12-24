package com.example.mycards.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Social {
    @SerializedName("facebook")
    private String facebook;
    @SerializedName("twitter")
    private String twitter;
    @SerializedName("youtube")
    private String youtube;
    @SerializedName("inst")
    private String inst;
    @SerializedName("whatsApp")
    private String whatsApp;
    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getInst() {
        return inst;
    }

    public String getWhatsApp() {
        return whatsApp;
    }
}
