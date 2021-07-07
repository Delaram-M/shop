package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class AddProductActivity extends AppCompatActivity {

    private int SELECT_PICTURE = 200;

    private Integer sellerID;
    private Integer categoryID;
    private Uri imageURI;
    private EditText name;
    private EditText price;
    private TextView error;

    private Spinner spinner;
    private ImageView imageView;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Intent intent = getIntent();
        sellerID = intent.getIntExtra("sellerID", 0);


        name = findViewById(R.id.add_name);
        price = findViewById(R.id.add_price);
        error = findViewById(R.id.add_error);


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
                Intent imageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
                imageIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"),
                        SELECT_PICTURE);
            }
        });



        addButton = findViewById(R.id.add_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddProductActivity.this.price.getText().length() == 0)
                    error.setText(R.string.enter_price);
                else {
                    String name = AddProductActivity.this.name.getText().toString().trim();
                    Double price = Double.valueOf(AddProductActivity.this.price.getText().toString().trim());
                    if(name.equals(""))
                        error.setText(R.string.enter_name);
                    else if(imageURI == null)
                        error.setText(R.string.add_image);
                    else {
                        databaseHelper.addProduct(categoryID, sellerID, name, price, imageURI);
                        error.setText("");
                    }
                }
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

