package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class SellerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;

    private Integer sellerID;
    private DatabaseHelper databaseHelper;
    private Cursor sellerProductsCursor;
    private CustomAdapter customAdapter;
    private ArrayList<Integer> productIDs;
    private ArrayList<Uri> productImageURIs;
    private ArrayList<String> productNames;
    private ArrayList<String> productCategories;
    private ArrayList<Double> productPrices;
    private ArrayList<String> productSellerUsernames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        Intent intent = getIntent();
        sellerID = intent.getIntExtra("sellerID",0);

        recyclerView = findViewById(R.id.seller_list);
        addButton = findViewById(R.id.seller_add);

        databaseHelper = new DatabaseHelper(this);
        sellerProductsCursor = databaseHelper.getSellerProducts(sellerID);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(SellerActivity.this, AddProductActivity.class);
                addIntent.putExtra("sellerID", sellerID);
                startActivity(addIntent);
            }
        });

        productIDs = new ArrayList<>();
        productImageURIs = new ArrayList<>();
        productNames = new ArrayList<>();
        productCategories =new ArrayList<>();
        productPrices = new ArrayList<>();
        productSellerUsernames = new ArrayList<>();
        storeSellerProductsData();
        customAdapter = new CustomAdapter(SellerActivity.this, productIDs, productImageURIs, productNames,
                productCategories, productPrices,productSellerUsernames);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((SellerActivity.this)));








    }






    public void storeSellerProductsData(){
        if(sellerProductsCursor.getCount() != 0){
            while (sellerProductsCursor.moveToNext()){
                productIDs.add(sellerProductsCursor.getInt(0));
                productImageURIs.add(Uri.parse(sellerProductsCursor.getString(5)));
                productNames.add(sellerProductsCursor.getString(3));
                productCategories.add(databaseHelper.getCategoryName(sellerProductsCursor.getInt(1)));
                productPrices.add(sellerProductsCursor.getDouble(4));
                productSellerUsernames.add(databaseHelper.getSellerUsername(sellerProductsCursor.getInt(2)));
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(SellerActivity.this, MainActivity.class);
        startActivity(backIntent);
    }
}