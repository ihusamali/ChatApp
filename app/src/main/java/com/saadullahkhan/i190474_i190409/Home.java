package com.saadullahkhan.i190474_i190409;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    BottomNavigationView bottomNav;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNav = findViewById(R.id.bottomNavigationChat);
        id = getIntent().getStringExtra("id");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerChat,new ContactFragment(),"Contacts").commit();
        bottomNav.getMenu().findItem(R.id.bcContact).setChecked(true);
    }
}