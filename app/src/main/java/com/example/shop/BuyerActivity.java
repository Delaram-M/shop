package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class BuyerActivity extends AppCompatActivity {

    private Integer buyerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        Intent intent = getIntent();
        buyerID = intent.getIntExtra("buyerID", 0);

    }
}