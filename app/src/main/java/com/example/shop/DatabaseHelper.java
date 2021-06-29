package com.example.shop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "shop.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "";

        query = "CREATE TABLE buyer ("
                +"buyer_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"username TEXT, "
                +"password TEXT, "
                +"phone_number INTEGER);";
        database.execSQL(query);

        query = "CREATE TABLE seller ("
                +"seller_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"username TEXT, "
                +"password TEXT, "
                +"phone_number INTEGER);";
        database.execSQL(query);

        query = "CREATE TABLE product ("
                +"product_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"category_ID INTEGER, "
                +"seller_ID INTEGER, "
                +"name TEXT, "
                +"price DOUBLE, "
                +"image_URI TEXT);";
        database.execSQL(query);

        query = "CREATE TABLE category ("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"name TEXT);";
        database.execSQL(query);



        query = "INSERT INTO category (name) "
                + "VALUES ('Electronics');";
        database.execSQL(query);
        query = "INSERT INTO category (name) "
                + "VALUES ('Home & Kitchen');";
        database.execSQL(query);
        query = "INSERT INTO category (name) "
                + "VALUES ('Books');";
        database.execSQL(query);
        query = "INSERT INTO category (name) "
                + "VALUES ('Fashion & Beauty');";
        database.execSQL(query);
        query = "INSERT INTO category (name) "
                + "VALUES ('Arts');";
        database.execSQL(query);
        query = "INSERT INTO category (name) "
                + "VALUES ('Games');";
        database.execSQL(query);
        query = "INSERT INTO category (name) "
                + "VALUES ('Industrial & Scientific');";
        database.execSQL(query);query = "INSERT INTO category (name) "
                + "VALUES ('Others');";
        database.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS buyer");
        database.execSQL("DROP TABLE IF EXISTS seller");
        database.execSQL("DROP TABLE IF EXISTS product");
        database.execSQL("DROP TABLE IF EXISTS category");
        onCreate(database);
    }



    public void addBuyer(String username, String password, Integer phoneNumber){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("phone_number", phoneNumber);
        long result = database.insert("buyer", null, contentValues);
        if(result == -1)
            Toast.makeText(context, "failed to register buyer", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "buyer registered successfully", Toast.LENGTH_LONG).show();
    }

    public void addSeller(String username, String password, Integer phoneNumber){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("phone_number", phoneNumber);
        long result = database.insert("seller", null, contentValues);
        if(result == -1)
            Toast.makeText(context, "failed to register seller", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "seller registered successfully", Toast.LENGTH_LONG).show();
    }

    public void addProduct(Integer categoryID, Integer sellerID, String name, Double price, Uri imageURI){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_ID", categoryID);
        contentValues.put("seller_ID", sellerID);
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("image_URI", imageURI.toString());
        long result = database.insert("product", null, contentValues);
        if(result == -1)
            Toast.makeText(context, "failed to add product", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "product added successfully", Toast.LENGTH_LONG).show();
    }

    public void updateProduct(Integer productID, Integer categoryID, Integer sellerID, String name, Double price, Uri imageURI){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_ID", categoryID);
        contentValues.put("seller_ID", sellerID);
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("image_URI", imageURI.toString());
        long result = database.update("product", contentValues,
                "product_ID =" + productID , null);
        if(result == -1)
            Toast.makeText(context, "failed to update product", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "product updated successfully", Toast.LENGTH_LONG).show();
    }

    public void deleteProduct(Integer productID){
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete("product", "product_ID =" + productID, null);
        if(result == -1)
            Toast.makeText(context, "failed to delete product", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "product deleted successfully", Toast.LENGTH_LONG).show();
    }



    public void addCategory(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        long result = database.insert("category", null, contentValues);
        if(result == -1)
            Toast.makeText(context, "failed to add category", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "category added successfully", Toast.LENGTH_LONG).show();

    }

    public Integer getSellerID(String username){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM seller WHERE username = " + "'" + username + "';";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        Integer sellerID = cursor.getInt(0);
        cursor.close();
        return sellerID;
    }

    public Integer getBuyerID(String username){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM buyer WHERE username = " + "'" + username + "';";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        Integer buyerID = cursor.getInt(0);
        cursor.close();
        return buyerID;
    }

    public String getSellerUsername(Integer sellerID){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM seller WHERE seller_ID = " + "'" + sellerID + "';";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String sellerUsername = cursor.getString(1);
        cursor.close();
        return sellerUsername;
    }

    public String getCategoryName(Integer categoryID){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM category WHERE _id = " + "'" + categoryID + "';";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String categoryName = cursor.getString(1);
        cursor.close();
        return categoryName;
    }

    public Integer getCategoryID(String categoryName){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM category WHERE name = " + "'" + categoryName + "';";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        Integer categoryID = cursor.getInt(0);
        cursor.close();
        return categoryID;
    }


    public SimpleCursorAdapter getCategoriesAdapter(){
        SimpleCursorAdapter simpleCursorAdapter;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM category", null);
        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        simpleCursorAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_spinner_dropdown_item,cursor, from, to,0);
        simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return simpleCursorAdapter;
    }

    public Cursor getSellerProducts(Integer sellerID){
        Cursor cursor;
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM product WHERE seller_ID =" + "'" + String.valueOf(sellerID) + "'";
        cursor = database.rawQuery(query, null);
        return cursor;
    }


    public Cursor getAllProducts(){
        Cursor cursor;
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM product" ;
        cursor = database.rawQuery(query, null);
        return cursor;
    }








}
