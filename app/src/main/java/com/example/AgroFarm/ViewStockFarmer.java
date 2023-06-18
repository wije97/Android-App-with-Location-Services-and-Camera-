package com.example.AgroFarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewStockFarmer extends AppCompatActivity {

    String email;
    ListView list;
    SQLiteDatabase db;

    private ArrayList<String> product_name;
    private ArrayList<String> product_quantity;
    private ArrayList<String> product_price;
    private ArrayList<String> product_code;
    private ArrayList<String> f_email;
    private ArrayList<byte[]> product_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stock_farmer);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        initialize();
        showData();
        listItemClick();

    }

    public void initialize(){

        list = (ListView)findViewById(R.id.list_farmer);

        product_name = new ArrayList<String>();
        product_quantity = new ArrayList<String>();
        product_price = new ArrayList<String>();
        product_code = new ArrayList<String>();
        f_email = new ArrayList<String>();
        product_image = new ArrayList<byte[]>();
    }

    public void showData(){

        DBHandler dbHandler = new DBHandler(this);

        db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  HarvestStock WHERE User_Email= '"+ email +"'" ,null);

        product_name.clear();
        product_quantity.clear();
        product_price.clear();
        product_code.clear();
        f_email.clear();
        product_image.clear();

        if (cursor.moveToFirst()) {
            do {
                product_name.add(cursor.getString(cursor.getColumnIndex("Name")));
                product_quantity.add(cursor.getString(cursor.getColumnIndex("Stock")));
                product_price.add(cursor.getString(cursor.getColumnIndex("Price")));
                product_code.add(cursor.getString(cursor.getColumnIndex("Code")));
                f_email.add(cursor.getString(cursor.getColumnIndex("User_Email")));
                product_image.add(cursor.getBlob(cursor.getColumnIndex("Image")));
            } while (cursor.moveToNext());
        }else{
            Toast.makeText(getApplicationContext(), "No Data!", Toast.LENGTH_SHORT).show();
        }
        StockListAdapter stock_adapter = new StockListAdapter(getApplicationContext(), product_name, product_quantity, product_price, product_code, f_email, product_image);
        list.setAdapter(stock_adapter);
        cursor.close();
    }

    public void listItemClick(){

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String code = ((TextView) view.findViewById(R.id.et_procode)).getText().toString();

                Intent intent = new Intent(getApplicationContext(), EditStocks.class);
                intent.putExtra("email",email);
                intent.putExtra("code",code);
                startActivity(intent);
            }
        });
    }
}