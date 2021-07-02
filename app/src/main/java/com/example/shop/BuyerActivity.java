package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

public class BuyerActivity extends AppCompatActivity {

    private Integer buyerID;

    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private Cursor productsCursor;
    private BuyerAdapter buyerAdapter;
    private ArrayList<Integer> productIDs;
    private ArrayList<Uri> productImageURIs;
    private ArrayList<String> productNames;
    private ArrayList<String> productCategories;
    private ArrayList<Double> productPrices;
    private ArrayList<String> productSellerUsernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        Intent intent = getIntent();
        buyerID = intent.getIntExtra("buyerID", 0);

        recyclerView = findViewById(R.id.buyer_list);

        databaseHelper = new DatabaseHelper(this);
        productsCursor = databaseHelper.getAllProducts();

        productIDs = new ArrayList<>();
        productImageURIs = new ArrayList<>();
        productNames = new ArrayList<>();
        productCategories =new ArrayList<>();
        productPrices = new ArrayList<>();
        productSellerUsernames = new ArrayList<>();

        storeProductsData();

        buyerAdapter = new BuyerAdapter(BuyerActivity.this, productIDs, productImageURIs, productNames,
                productCategories, productPrices,productSellerUsernames);
        recyclerView.setAdapter(buyerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((BuyerActivity.this)));



    }







    public void storeProductsData(){
        if(productsCursor.getCount() != 0){
            while (productsCursor.moveToNext()){
                productIDs.add(productsCursor.getInt(0));
                productImageURIs.add(Uri.parse(productsCursor.getString(5)));
                productNames.add(productsCursor.getString(3));
                productCategories.add(databaseHelper.getCategoryName(productsCursor.getInt(1)));
                productPrices.add(productsCursor.getDouble(4));
                productSellerUsernames.add(databaseHelper.getSellerUsername(productsCursor.getInt(2)));
            }
        }
    }









}