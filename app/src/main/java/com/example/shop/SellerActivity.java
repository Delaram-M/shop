package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class SellerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;

    Integer sellerID;
    DatabaseHelper databaseHelper;
    Cursor sellerProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        Intent intent = getIntent();
        sellerID = intent.getIntExtra("sellerID",0);

        recyclerView = findViewById(R.id.seller_list);
        addButton = findViewById(R.id.seller_add);

        databaseHelper = new DatabaseHelper(this);
        sellerProducts = databaseHelper.getSellerProducts(sellerID);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(SellerActivity.this, AddProductActivity.class);
                addIntent.putExtra("sellerID", sellerID);
                startActivity(addIntent);
            }
        });



    }





}