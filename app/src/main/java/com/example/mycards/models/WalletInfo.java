package com.example.mycards.models;

public class WalletInfo {
    int id;
    private String mDate;
    private int balance;

    public WalletInfo(int id, String mDate, int balance) {
        this.id = id;
        this.mDate = mDate;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getmDate() {
        return mDate;
    }

    public int getBalance() {
        return balance;
    }
}
