package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class DeleteUpdateProductActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    Integer productID;
    Integer sellerID;
    Integer categoryID;
    Uri imageURI;
    String productName;
    Double productPrice;

    EditText name;
    EditText price;

    Spinner spinner;
    ImageView imageView;
    Button updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_update_product);
        Intent intent = getIntent();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        productID = intent.getIntExtra("productID",0);
        imageURI = Uri.parse(intent.getStringExtra("imageURIString"));
        sellerID = databaseHelper.getSellerID(intent.getStringExtra("username"));
        categoryID = databaseHelper.getCategoryID(intent.getStringExtra("category"));
        productName = intent.getStringExtra("name");
        productPrice = intent.getDoubleExtra("price", 0);


        name = findViewById(R.id.update_delete_name);
        price = findViewById(R.id.update_delete_price);

        name.setText(productName);
        price.setText(String.valueOf(productPrice));


        spinner = findViewById(R.id.update_delete_category);
        SimpleCursorAdapter simpleCursorAdapter = databaseHelper.getCategoriesAdapter();
        spinner.setAdapter(simpleCursorAdapter);
        spinner.setSelection(categoryID - 1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                categoryID = (int) id;
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        imageView = findViewById(R.id.update_delete_image);
        imageView.setImageURI(imageURI);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent= new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"), SELECT_PICTURE);
            }
        });



        updateButton = findViewById(R.id.update_delete_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check for errors

            }
        });


    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                this.imageURI = selectedImageUri;
                if (null != selectedImageUri) {
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(DeleteUpdateProductActivity.this, SellerActivity.class);
        backIntent.putExtra("sellerID", sellerID);
        startActivity(backIntent);
    }
}