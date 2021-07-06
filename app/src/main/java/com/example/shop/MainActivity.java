package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button logInButton;
    TextView error;
    Button registerButton;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        registerButton = findViewById(R.id.login_register);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        error = findViewById(R.id.login_error);
        logInButton = findViewById(R.id.login_login);

        //TODO avoid any hardcode
        //TODO work on back functionalities

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check for errors
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();
                if(!databaseHelper.isSeller(enteredUsername) && !databaseHelper.isBuyer(enteredUsername))
                    error.setText(R.string.no_user);
                else if(databaseHelper.isSeller(enteredUsername)){
                    Integer sellerID;
                    sellerID = databaseHelper.logInSeller(enteredUsername, enteredPassword);
                    if(sellerID == 0)
                        error.setText(R.string.incorrect_password);
                    else{
                        Intent sellerIntent = new Intent(MainActivity.this, SellerActivity.class);
                        sellerIntent.putExtra("sellerID", sellerID);
                        startActivity(sellerIntent);
                    }
                }
                else if(databaseHelper.isBuyer(enteredUsername)){
                    Integer buyerID;
                    buyerID = databaseHelper.logInBuyer(enteredUsername, enteredPassword);
                    if(buyerID == 0)
                        error.setText(R.string.incorrect_password);
                    else{
                        Intent buyerIntent = new Intent(MainActivity.this, BuyerActivity.class);
                        buyerIntent.putExtra("buyerID", buyerID);
                        startActivity(buyerIntent);
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }




}