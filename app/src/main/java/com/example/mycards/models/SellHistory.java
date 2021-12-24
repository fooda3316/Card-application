package com.example.mycards.models;

import com.google.gson.annotations.SerializedName;

public class SellHistory {

    @SerializedName("mDate")
    private String date;
    @SerializedName("card_name")
    private String name;
    @SerializedName("card_number")
    private String serialNumber;
    @SerializedName("card_value")
    private int value;

    public SellHistory(String date, String name, String serialNumber, int value) {
        this.date = date;
        this.name = name;
        this.serialNumber = serialNumber;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getValue() {
        return value;
    }
}
