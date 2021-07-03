package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewProductActivity extends AppCompatActivity {


    Integer productID;
    Integer sellerID;
    Integer categoryID;
    Uri imageURI;
    String productName;
    Double productPrice;

    DatabaseHelper databaseHelper;

    ImageView imageView;
    TextView name;
    TextView category;
    TextView price;
    TextView sellerUsername;
    Button sellerPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        Intent intent = getIntent();
        databaseHelper = new DatabaseHelper(this);

        productID = intent.getIntExtra("productID",0);
        imageURI = Uri.parse(intent.getStringExtra("imageURIString"));
        sellerID = databaseHelper.getSellerID(intent.getStringExtra("username"));
        categoryID = databaseHelper.getCategoryID(intent.getStringExtra("category"));
        productName = intent.getStringExtra("name");
        productPrice = intent.getDoubleExtra("price", 0);

        imageView = findViewById(R.id.product_view_image);
        name = findViewById(R.id.product_view_name);
        category = findViewById(R.id.product_view_category);
        price = findViewById(R.id.product_view_price);
        sellerUsername = findViewById(R.id.product_view_seller_username);
        sellerPhoneNumber = findViewById(R.id.product_view_phone_number);

        //TODO image access
        //imageView.setImageURI(imageURI);
        name.setText(productName);
        category.setText(databaseHelper.getCategoryName(categoryID));
        price.setText(String.valueOf(productPrice));
        sellerUsername.setText(databaseHelper.getSellerUsername(sellerID));
        sellerPhoneNumber.setText(String.valueOf(databaseHelper.getSellerPhoneNumber(sellerID)));


        //permission request
        sellerPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + databaseHelper.getSellerPhoneNumber(sellerID).toString()));
                startActivity(callIntent);
            }
        });








    }

















}