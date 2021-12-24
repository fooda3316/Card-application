package com.example.mycards.models;

public class AdminCard {
    private String title;
    private String subName;
    private String branch;
    private int value;
    private String date;


    public AdminCard(String title, String subName, String branch,  String date) {
        this.title = title;
        this.subName = subName;
        this.branch = branch;

        this.date = date;
    }

    public String getSubName() {
        return subName;
    }

    public String getBranch() {
        return branch;
    }

    public String getTitle() {
        return title;
    }

    public int getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }
}
