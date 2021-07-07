package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

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

    private SearchView searchView;
    private Button sortAscending;
    private Button sortDescending;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        Intent intent = getIntent();
        buyerID = intent.getIntExtra("buyerID", 0);

        recyclerView = findViewById(R.id.buyer_list);
        sortDescending = findViewById(R.id.buyer_highest_to_lowest);
        sortAscending = findViewById(R.id.buyer_lowest_to_highest);

        databaseHelper = new DatabaseHelper(this);
        productsCursor = databaseHelper.getAllProducts();

        productIDs = new ArrayList<>();
        productImageURIs = new ArrayList<>();
        productNames = new ArrayList<>();
        productCategories =new ArrayList<>();
        productPrices = new ArrayList<>();
        productSellerUsernames = new ArrayList<>();

        setUpRecyclerView();
        setUpSearch();

        sortDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsCursor = databaseHelper.getAllProductsDescending();
                setUpRecyclerView();
            }
        });

        sortAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsCursor = databaseHelper.getAllProductsAscending();
                setUpRecyclerView();
            }
        });



    }


    private void setUpRecyclerView(){
        productIDs.clear();
        productImageURIs.clear();
        productNames.clear();
        productCategories.clear();
        productPrices.clear();
        productSellerUsernames.clear();
        storeProductsData();
        buyerAdapter = new BuyerAdapter(BuyerActivity.this, productIDs, productImageURIs, productNames,
                productCategories, productPrices,productSellerUsernames);
        recyclerView.setAdapter(buyerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((BuyerActivity.this)));
    }



    public void storeProductsData(){
        if(productsCursor.getCount() != 0){
            productsCursor.moveToFirst();
                do {
                    productIDs.add(productsCursor.getInt(0));
                    productImageURIs.add(Uri.parse(productsCursor.getString(5)));
                    productNames.add(productsCursor.getString(3));
                    productCategories.add(databaseHelper.getCategoryName(productsCursor.getInt(1)));
                    productPrices.add(productsCursor.getDouble(4));
                    productSellerUsernames.add(databaseHelper.getSellerUsername(productsCursor.getInt(2)));
                } while (productsCursor.moveToNext());
        }
    }


    private void setUpSearch(){
        searchView = findViewById(R.id.buyer_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProductsData(newText);
                BuyerAdapter filteredAdapter = new BuyerAdapter(BuyerActivity.this,
                        productIDs, productImageURIs, productNames, productCategories,
                        productPrices,productSellerUsernames);
                recyclerView.setAdapter(filteredAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager((BuyerActivity.this)));
                return false;
            }
        });


    }



    private void filterProductsData(String searchTerm){
        productIDs.clear();
        productImageURIs.clear();
        productNames.clear();
        productCategories.clear();
        productPrices.clear();
        productSellerUsernames.clear();
        if(productsCursor.getCount() != 0){
            productsCursor.moveToFirst();
                do {
                    if (productsCursor.getString(3).toLowerCase().trim().contains(searchTerm.toLowerCase().trim())) {
                        productIDs.add(productsCursor.getInt(0));
                        productImageURIs.add(Uri.parse(productsCursor.getString(5)));
                        productNames.add(productsCursor.getString(3));
                        productCategories.add(databaseHelper.getCategoryName(productsCursor.getInt(1)));
                        productPrices.add(productsCursor.getDouble(4));
                        productSellerUsernames.add(databaseHelper.getSellerUsername(productsCursor.getInt(2)));
                    }
                } while (productsCursor.moveToNext());

        }

    }


    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(BuyerActivity.this, MainActivity.class);
        startActivity(backIntent);
    }










}