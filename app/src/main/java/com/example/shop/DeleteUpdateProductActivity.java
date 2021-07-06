package com.example.shop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class DeleteUpdateProductActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    private Integer productID;
    private Integer sellerID;
    private Integer categoryID;
    private Uri imageURI;
    private String productName;
    private Double productPrice;

    private EditText name;
    private EditText price;
    private TextView error;

    private DatabaseHelper databaseHelper;
    private Spinner spinner;
    private ImageView imageView;
    private Button updateButton;
    private Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_update_product);
        Intent intent = getIntent();
        databaseHelper = new DatabaseHelper(this);

        productID = intent.getIntExtra("productID",0);
        imageURI = Uri.parse(intent.getStringExtra("imageURIString"));
        sellerID = databaseHelper.getSellerID(intent.getStringExtra("username"));
        categoryID = databaseHelper.getCategoryID(intent.getStringExtra("category"));
        productName = intent.getStringExtra("name");
        productPrice = intent.getDoubleExtra("price", 0);


        name = findViewById(R.id.update_delete_name);
        price = findViewById(R.id.update_delete_price);
        error = findViewById(R.id.update_delete_error);

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
        //TODO uri
        //imageView.setImageURI(imageURI);
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
                if(DeleteUpdateProductActivity.this.price.getText().length() == 0)
                    error.setText(R.string.enter_price);
                else {
                    String name = DeleteUpdateProductActivity.this.name.getText().toString().trim();
                    Double price = Double.valueOf(DeleteUpdateProductActivity.this.price.getText().toString().trim());
                    if(name.equals(""))
                        error.setText(R.string.enter_name);
                    else if(imageURI == null)
                        error.setText(R.string.add_image);
                    else {
                        databaseHelper.updateProduct(productID, categoryID, sellerID, name, price, imageURI);
                        error.setText("");
                    }
                }
            }
        });

        deleteButton = findViewById(R.id.update_delete_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogue();
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


    private void confirmDialogue(){
        productName = name.getText().toString().trim();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Product");
        builder.setMessage("Are you sure you want to delete " + productName + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deleteProduct(productID);
                onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }



    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(DeleteUpdateProductActivity.this, SellerActivity.class);
        backIntent.putExtra("sellerID", sellerID);
        startActivity(backIntent);
    }
}