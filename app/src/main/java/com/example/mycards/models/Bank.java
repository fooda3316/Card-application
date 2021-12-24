package com.example.mycards.models;

public class Bank {

    private String image;
    private String bankName;
    private String name;
    private String account;

    public Bank(String image, String bankName, String name, String account) {
        this.image=image;
        this.bankName = bankName;
        this.name = name;
        this.account = account;
    }

    public String getImage() {
        return image;
    }

    public String getBankName() {
        return bankName;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }
}
