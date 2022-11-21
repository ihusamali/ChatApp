package com.saadullahkhan.i190474_i190409;

import android.graphics.Bitmap;

public class User {
    String id,name,number,password;
    Bitmap dp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getDp() {
        return dp;
    }

    public void setDp(Bitmap dp) {
        this.dp = dp;
    }

    public User(String name, String number, String password, Bitmap dp, String id) {
        this.name = name;
        this.number = number;
        this.password = password;
        this.dp = dp;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
