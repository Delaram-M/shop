package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class BuyerActivity extends AppCompatActivity {

    private Integer buyerID;

    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private Cursor productsCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        Intent intent = getIntent();
        buyerID = intent.getIntExtra("buyerID", 0);

        recyclerView = findViewById(R.id.seller_list);

        databaseHelper = new DatabaseHelper(this);
        productsCursor = databaseHelper.getAllProducts();



    }

















}