package com.saadullahkhan.i190474_i190409;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent=new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
            finish();
        },1000);
    }

}