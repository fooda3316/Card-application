package com.example.mycards.models;

public class History {
    int id;
    private String mDate;
    private String name;
    private String serialNumber;
    private String value;

    public History(int id, String mDate, String name, String serialNumber, String value) {
        this.mDate = mDate;
        this.name = name;
        this.serialNumber = serialNumber;
        this.value = value;
        this.id=id;
    }

    public String getmDate() {
        return mDate;
    }

    public String getName() {
        return name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getValue() {
        return value;
    }
}
