package com.example.mycards.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Belal on 14/04/17.
 */

public class CardResult {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;


    public CardResult(Boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}
