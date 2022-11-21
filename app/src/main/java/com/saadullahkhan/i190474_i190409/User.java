package com.saadullahkhan.i190474_i190409;

import android.graphics.Bitmap;

public class User {
    String name,number,password;
    Bitmap dp;

    public User(String name, String number, String password,Bitmap dp) {
        this.name = name;
        this.number = number;
        this.password = password;
        this.dp = dp;
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
