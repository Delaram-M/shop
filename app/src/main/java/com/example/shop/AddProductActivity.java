package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    Integer sellerID;
    Integer categoryID;
    Uri imageURI;
    EditText name;
    EditText price;

    Spinner spinner;
    ImageView imageView;
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Intent intent = getIntent();
        sellerID = intent.getIntExtra("sellerID", 0);


        name = findViewById(R.id.add_name);
        price = findViewById(R.id.add_price);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        spinner = findViewById(R.id.add_category);
        SimpleCursorAdapter simpleCursorAdapter = databaseHelper.getCategoriesAdapter();
        spinner.setAdapter(simpleCursorAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                categoryID = (int) id;
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        imageView = findViewById(R.id.add_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent= new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"), SELECT_PICTURE);
            }
        });



        addButton = findViewById(R.id.add_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check for errors
                String name = AddProductActivity.this.name.getText().toString().trim();
                Double price = Double.valueOf(AddProductActivity.this.price.getText().toString().trim());
                databaseHelper.addProduct(categoryID,sellerID,name,price,imageURI);
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
        Intent backIntent = new Intent(AddProductActivity.this, SellerActivity.class);
        backIntent.putExtra("sellerID", sellerID);
        startActivity(backIntent);
    }


}

