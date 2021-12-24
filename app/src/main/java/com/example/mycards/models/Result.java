package com.example.mycards.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Belal on 14/04/17.
 */

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;
    @SerializedName("pass")
    private String pass;

    @SerializedName("version")
    private String version;
    private int id;

    public String getVersion() {
        return version;
    }

    public String getPass() {
        return pass;
    }

    public int getId() {
        return id;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
