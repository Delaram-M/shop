package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProductActivity extends AppCompatActivity {


    private Integer productID;
    private Integer sellerID;
    private Integer categoryID;
    private Uri imageURI;
    private String productName;
    private Double productPrice;

    private DatabaseHelper databaseHelper;

    private ImageView imageView;
    private TextView name;
    private TextView category;
    private TextView price;
    private TextView sellerUsername;
    private Button sellerPhoneNumber;

    private final int CALL_PERMISSION_CODE = 25;


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

        imageView.setImageURI(imageURI);
        name.setText(productName);
        category.setText(databaseHelper.getCategoryName(categoryID));
        price.setText(String.valueOf(productPrice));
        sellerUsername.setText(databaseHelper.getSellerUsername(sellerID));
        sellerPhoneNumber.setText(String.valueOf(databaseHelper.getSellerPhoneNumber(sellerID)));


        //permission request
        sellerPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ViewProductActivity.this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + databaseHelper.getSellerPhoneNumber(sellerID).toString()));
                    startActivity(callIntent);
                }
                else{
                    requestPhonePermission();
                }

            }
        });





    }


    private void requestPhonePermission () {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed").setMessage("This permission is needed to call the seller.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ViewProductActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},CALL_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},CALL_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
        }
    }










}