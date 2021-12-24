package com.example.mycards.models;


import java.util.ArrayList;
import java.util.List;

public class Results {
    private ArrayList<SellHistory> sellHistories;
    private ArrayList<Category> categories;
    private ArrayList<History> histories;
    private ArrayList<WalletInfo> walletInfos;
    private List<Branch> branches;



    public Results() {

    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<History> getHistories() {
        return histories;
    }

    public ArrayList<SellHistory> getSellHistories() {
        return sellHistories;
    }

    public ArrayList<WalletInfo> getWalletInfos() {
        return walletInfos;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
