package com.example.AgroFarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BUYER);
        db.execSQL(FARMER);
        db.execSQL(HARVEST);
        db.execSQL(ORDERS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DELETE_BUYER);
        db.execSQL(DELETE_FARMER);
        db.execSQL(DELETE_HARVEST);
        db.execSQL(DELETE_ORDERS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



    //buyer table
    private static final String BUYER =
            "CREATE TABLE " + Buyer.BuyerInfo.TABLE_NAME + " (" +
                    Buyer.BuyerInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Buyer.BuyerInfo.COL_1 + " TEXT NOT NULL," +
                    Buyer.BuyerInfo.COL_2 + " TEXT NOT NULL," +
                    Buyer.BuyerInfo.COL_3 + " TEXT NOT NULL," +
                    Buyer.BuyerInfo.COL_4 + " TEXT NOT NULL," +
                    Buyer.BuyerInfo.COL_5 + " TEXT NOT NULL," +
                    Buyer.BuyerInfo.COL_6 + " TEXT NOT NULL)";

    private static final String DELETE_BUYER =
            "DROP TABLE IF EXISTS " + Buyer.BuyerInfo.TABLE_NAME;


    //add buyer to database
    public long buyerData(String name, String address, String mobile, String character, String email, String passWord){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Buyer.BuyerInfo.COL_1, name);
        values.put(Buyer.BuyerInfo.COL_2, address);
        values.put(Buyer.BuyerInfo.COL_3, mobile);
        values.put(Buyer.BuyerInfo.COL_4, character);
        values.put(Buyer.BuyerInfo.COL_5, email);
        values.put(Buyer.BuyerInfo.COL_6, passWord);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Buyer.BuyerInfo.TABLE_NAME, null, values);
        return  newRowId;
    }


    // buyer login check
    public Boolean buyerLoginCheck(String email,String password){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                Buyer.BuyerInfo.COL_5,
                Buyer.BuyerInfo.COL_6
        };
        String selection =  Buyer.BuyerInfo.COL_5 + " = ? AND "+ Buyer.BuyerInfo.COL_6 +"= ?";
        String[] selectionArgs = { email,password};

        String sortOrder =
                Buyer.BuyerInfo.COL_5 + " ASC";

        Cursor cursor = db.query(
                Buyer.BuyerInfo.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        String validUser = null;
        while(cursor.moveToNext()){
            validUser = cursor.getString(cursor.getColumnIndexOrThrow( Buyer.BuyerInfo.COL_5));

        }
        cursor.close();
        if(validUser==null){
            return false;
        }
        else{
            return true;
        }

    }



    //farmer table
    private static final String FARMER =
            "CREATE TABLE " + Farmer.FarmerInfo.TABLE_NAME + " (" +
                    Farmer.FarmerInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Farmer.FarmerInfo.COL_1 + " TEXT NOT NULL," +
                    Farmer.FarmerInfo.COL_2 + " TEXT NOT NULL," +
                    Farmer.FarmerInfo.COL_3 + " TEXT NOT NULL," +
                    Farmer.FarmerInfo.COL_4 + " TEXT NOT NULL," +
                    Farmer.FarmerInfo.COL_5 + " TEXT NOT NULL," +
                    Farmer.FarmerInfo.COL_6 + " TEXT NOT NULL," +
                    Farmer.FarmerInfo.COL_7 + " DOUBLE NOT NULL," +
                    Farmer.FarmerInfo.COL_8 + " DOUBLE NOT NULL)";


    private static final String DELETE_FARMER =
            "DROP TABLE IF EXISTS " + Farmer.FarmerInfo.TABLE_NAME;

    //add farmer to database
    public long farmerData(String name, String address, String mobile, String character, String email, String passWord, double lat, double longt){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Farmer.FarmerInfo.COL_1, name);
        values.put(Farmer.FarmerInfo.COL_2, address);
        values.put(Farmer.FarmerInfo.COL_3, mobile);
        values.put(Farmer.FarmerInfo.COL_4, character);
        values.put(Farmer.FarmerInfo.COL_5, email);
        values.put(Farmer.FarmerInfo.COL_6, passWord);
        values.put(Farmer.FarmerInfo.COL_7, lat);
        values.put(Farmer.FarmerInfo.COL_8, longt);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Farmer.FarmerInfo.TABLE_NAME, null, values);
        return  newRowId;
    }


    //farmer login check
    public Boolean farmerLoginCheck(String email,String password){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                Farmer.FarmerInfo.COL_5,
                Farmer.FarmerInfo.COL_6
        };
        String selection =  Farmer.FarmerInfo.COL_5 + " = ? AND "+ Farmer.FarmerInfo.COL_6 +"= ?";
        String[] selectionArgs = { email,password};

        String sortOrder =
                Farmer.FarmerInfo.COL_5 + " ASC";

        Cursor cursor = db.query(
                Farmer.FarmerInfo.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        String validUser = null;
        while(cursor.moveToNext()){
            validUser = cursor.getString(cursor.getColumnIndexOrThrow( Farmer.FarmerInfo.COL_5));

        }
        cursor.close();
        if(validUser==null){
            return false;
        }
        else{
            return true;
        }

    }




    //harvest table
    private static final String HARVEST =
            "CREATE TABLE " + Harvest.HarvestInfo.TABLE_NAME + " (" +
                    Harvest.HarvestInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Harvest.HarvestInfo.COL_1 + " TEXT NOT NULL," +
                    Harvest.HarvestInfo.COL_2 + " TEXT NOT NULL," +
                    Harvest.HarvestInfo.COL_3 + " TEXT NOT NULL," +
                    Harvest.HarvestInfo.COL_4 + " TEXT NOT NULL," +
                    Harvest.HarvestInfo.COL_5 + " TEXT NOT NULL," +
                    Harvest.HarvestInfo.COL_6 + " TEXT NOT NULL," +
                    Harvest.HarvestInfo.COL_7 + " TEXT NOT NULL," +
                    Harvest.HarvestInfo.COL_8 + " BLOB NOT NULL)";

    private static final String DELETE_HARVEST =
            "DROP TABLE IF EXISTS " + Harvest.HarvestInfo.TABLE_NAME;

    //add harvest to database
    public long harvestData(int code, String name, String status, int stock, int price, String type, String email, byte[] img){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Harvest.HarvestInfo.COL_1, code);
        values.put(Harvest.HarvestInfo.COL_2, name);
        values.put(Harvest.HarvestInfo.COL_3, status);
        values.put(Harvest.HarvestInfo.COL_4, stock);
        values.put(Harvest.HarvestInfo.COL_5, price);
        values.put(Harvest.HarvestInfo.COL_6, type);
        values.put(Harvest.HarvestInfo.COL_7, email);
        values.put(Harvest.HarvestInfo.COL_8, img);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Harvest.HarvestInfo.TABLE_NAME, null, values);
        return  newRowId;
    }



    // select product form db (search )
    public List searchStockFromDB (String pCode){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                Harvest.HarvestInfo.COL_1,
                Harvest.HarvestInfo.COL_2,
                Harvest.HarvestInfo.COL_3,
                Harvest.HarvestInfo.COL_4,
                Harvest.HarvestInfo.COL_5,
                Harvest.HarvestInfo.COL_6,


        };

        String selection = Harvest.HarvestInfo.COL_1 + " LIKE ?";
        String[] selectionArgs = { pCode };

        String sortOrder =
                Harvest.HarvestInfo.COL_1 + " ASC";

        Cursor cursor = db.query(
                Harvest.HarvestInfo.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List HarvestStock = new ArrayList<>();

        while(cursor.moveToNext()) {
            String productCode = cursor.getString(cursor.getColumnIndexOrThrow(Harvest.HarvestInfo.COL_1));
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(Harvest.HarvestInfo.COL_2));
            String productDesc = cursor.getString(cursor.getColumnIndexOrThrow(Harvest.HarvestInfo.COL_3));
            String quantity = cursor.getString(cursor.getColumnIndexOrThrow(Harvest.HarvestInfo.COL_4));
            String price = cursor.getString(cursor.getColumnIndexOrThrow(Harvest.HarvestInfo.COL_5));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(Harvest.HarvestInfo.COL_6));

            HarvestStock.add(productCode);
            HarvestStock.add(productName);
            HarvestStock.add(productDesc);
            HarvestStock.add(quantity);
            HarvestStock.add(price);
            HarvestStock.add(type);

        }
        cursor.close();
        return HarvestStock;
    }

    // update product
    public Boolean updateStock (String code, String name, String status, int stock, int price, String type){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Harvest.HarvestInfo.COL_2, name);
        values.put(Harvest.HarvestInfo.COL_3,status);
        values.put(Harvest.HarvestInfo.COL_4, stock);
        values.put(Harvest.HarvestInfo.COL_5, price);
        values.put(Harvest.HarvestInfo.COL_6, type);


        String selection = Harvest.HarvestInfo.COL_1 + " LIKE ?";
        String[] selectionArgs = { code };

        int count = db.update(
                Harvest.HarvestInfo.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count >=1 ) {
            return true;
        }
        else {
            return false;
        }

    }

    //delete product
    public void deleteProduct(String pCode){

        SQLiteDatabase db = getWritableDatabase();

        String selection = Harvest.HarvestInfo.COL_1 + " LIKE ?";
        String[] selectionArgs = { pCode };
        int deletedRows = db.delete(Harvest.HarvestInfo.TABLE_NAME, selection, selectionArgs);

    }



    //order table
    private static final String ORDERS =
            "CREATE TABLE " + Orders.OrderInfo.TABLE_NAME + " (" +
                    Orders.OrderInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Orders.OrderInfo.COL_1 + " TEXT NOT NULL," +
                    Orders.OrderInfo.COL_2 + " TEXT NOT NULL," +
                    Orders.OrderInfo.COL_3 + " TEXT NOT NULL," +
                    Orders.OrderInfo.COL_4 + " TEXT NOT NULL," +
                    Orders.OrderInfo.COL_5 + " TEXT NOT NULL)";

    private static final String DELETE_ORDERS =
            "DROP TABLE IF EXISTS " + Harvest.HarvestInfo.TABLE_NAME;



    // add order to database
    public long ordersData(int code, int stock, double total, String f_email, String b_email){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Orders.OrderInfo.COL_1, code);
        values.put(Orders.OrderInfo.COL_2, stock);
        values.put(Orders.OrderInfo.COL_3, total);
        values.put(Orders.OrderInfo.COL_4, f_email);
        values.put(Orders.OrderInfo.COL_5, b_email);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Orders.OrderInfo.TABLE_NAME, null, values);
        return  newRowId;
    }
}
