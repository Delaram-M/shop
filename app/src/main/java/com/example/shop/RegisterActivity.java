package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText phoneNumber;
    private Switch roleSwitch;
    private TextView error;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        phoneNumber = findViewById(R.id.register_phone_number);
        roleSwitch = findViewById(R.id.register_role);
        error = findViewById(R.id.register_error);
        registerButton = findViewById(R.id.register_regsiter);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RegisterActivity.this.phoneNumber.getText().length() == 0)
                    error.setText(R.string.enter_phone_number);
                else {
                    String username = RegisterActivity.this.username.getText().toString().trim();
                    String password = RegisterActivity.this.password.getText().toString().trim();
                    Integer phoneNumber = Integer.parseInt(RegisterActivity.this.phoneNumber.getText().toString());
                    if (username.equals(""))
                        error.setText(R.string.enter_username);
                    else if (password.equals(""))
                        error.setText(R.string.enter_password);
                    else if(databaseHelper.isSeller(username) || databaseHelper.isBuyer(username))
                        error.setText(R.string.username_exists);
                    else {
                        if (!roleSwitch.isChecked()) {
                            databaseHelper.addBuyer(username, password, phoneNumber);
                            Intent intent = new Intent(RegisterActivity.this, BuyerActivity.class);
                            intent.putExtra("buyerID", databaseHelper.getBuyerID(username));
                            startActivity(intent);
                        } else {
                            databaseHelper.addSeller(username, password, phoneNumber);
                            Intent intent = new Intent(RegisterActivity.this, SellerActivity.class);
                            intent.putExtra("sellerID", databaseHelper.getSellerID(username));
                            startActivity(intent);
                        }
                    }
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(backIntent);
    }

}