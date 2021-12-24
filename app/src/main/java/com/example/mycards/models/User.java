package com.example.mycards.models;

/**
 * Created by Belal on 14/04/17.
 */

public class User {

    private int id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String image;
    private String phone;
    private int balance;

    public User(String name, String email, String image, String phone, int balance) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.phone = phone;
        this.balance = balance;
    }

    public User(String name, String lastName, String email, String password, String image, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.image = image;
        this.phone = phone;
    }

    public User(int id, String name, String lastName, String email, String password, String image, String phone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.image = image;
        this.phone = phone;
    }

    public int getBalance() {
        return balance;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }


}
