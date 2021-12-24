package com.example.mycards.models;

public class Home {

    private String title;
    private Boolean haveBranch=true;
    int color;

    public Home(String title, Boolean haveBranch, int color) {
        this.title = title;
        this.haveBranch = haveBranch;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public Boolean getHaveBranch() {
        return haveBranch;
    }

    public String getTitle() {
        return title;
    }
}
