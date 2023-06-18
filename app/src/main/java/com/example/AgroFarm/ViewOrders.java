package com.example.AgroFarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewOrders extends AppCompatActivity {

    String email;
    int cat;
    ListView list;
    SQLiteDatabase db;
    TextView page_name;

    private ArrayList<String> order_id;
    private ArrayList<String> pro_name;
    private ArrayList<String> pro_price;
    private ArrayList<String> sup_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        cat = intent.getIntExtra("cat",0);

        initialize();
        showData();

    }

    public void initialize(){

        list = (ListView)findViewById(R.id.list_orders);
        page_name = (TextView)findViewById(R.id.tv_view_order);

        order_id = new ArrayList<String>();
        pro_name = new ArrayList<String>();
        pro_price = new ArrayList<String>();
        sup_email = new ArrayList<String>();
    }

    public void showData(){

        DBHandler dbHandler = new DBHandler(this);
        db = dbHandler.getReadableDatabase();
        ;


        if(cat==1){
            page_name.setText("Payments");
            Cursor cursor = db.rawQuery("SELECT Orders._id, Orders.Total_Price, Orders.Farmer_Email, HarvestStock.Name AS Name FROM  Orders,HarvestStock WHERE  Orders.Product_Code = HarvestStock.Code AND Buyer_Email= '"+ email +"'" ,null);

            order_id.clear();
            pro_name.clear();
            pro_price.clear();
            sup_email.clear();

            if (cursor.moveToFirst()) {
                do {
                    order_id.add(cursor.getString(cursor.getColumnIndex("_id")));
                    pro_name.add(cursor.getString(cursor.getColumnIndex("Name")));
                    pro_price.add(cursor.getString(cursor.getColumnIndex("Total_Price")));
                    sup_email.add(cursor.getString(cursor.getColumnIndex("Farmer_Email")));
                } while (cursor.moveToNext());
            }else{

                    Toast.makeText(getApplicationContext(), "No Payments to Show!", Toast.LENGTH_SHORT).show();

            }
            OrdersAdapter order_adapter = new OrdersAdapter(getApplicationContext(), order_id, pro_name, pro_price, sup_email);
            list.setAdapter(order_adapter);
            cursor.close();

        }else{

            page_name.setText("Orders");
            Cursor cursor = db.rawQuery("SELECT Orders._id, Orders.Total_Price, Orders.Buyer_Email, HarvestStock.Name AS Name FROM  Orders,HarvestStock WHERE  Orders.Product_Code = HarvestStock.Code AND Farmer_Email= '"+ email +"'" ,null);

            order_id.clear();
            pro_name.clear();
            pro_price.clear();
            sup_email.clear();

            if (cursor.moveToFirst()) {
                do {
                    order_id.add(cursor.getString(cursor.getColumnIndex("_id")));
                    pro_name.add(cursor.getString(cursor.getColumnIndex("Name")));
                    pro_price.add(cursor.getString(cursor.getColumnIndex("Total_Price")));
                    sup_email.add(cursor.getString(cursor.getColumnIndex("Buyer_Email")));
                } while (cursor.moveToNext());
            }else{

                Toast.makeText(getApplicationContext(), "No Orders to Show!", Toast.LENGTH_SHORT).show();

            }
            OrdersAdapter order_adapter = new OrdersAdapter(getApplicationContext(), order_id, pro_name, pro_price, sup_email);
            list.setAdapter(order_adapter);
            cursor.close();
        }
    }
}