package com.saadullahkhan.i190474_i190409;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SpecificChat extends AppCompatActivity {
    TextView id,id1;
    SystemDataBase systemDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chat);
        id = findViewById(R.id.text);
        id1 = findViewById(R.id.text1);
        systemDataBase = new SystemDataBase(SpecificChat.this);
        id.setText(getIntent().getStringExtra("id"));
        id1.setText(systemDataBase.getId());

    }
}