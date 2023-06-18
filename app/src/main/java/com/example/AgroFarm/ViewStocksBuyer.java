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

public class ViewStocksBuyer extends AppCompatActivity {

    String email, category;
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
        setContentView(R.layout.activity_view_stocks_buyer);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        category = intent.getStringExtra("category");

        initialize();
        showData();
        listItemClick();

    }

    public void initialize(){
        list = (ListView)findViewById(R.id.list_buyer);

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
        Cursor cursor = db.rawQuery("SELECT * FROM  HarvestStock WHERE Type= '"+ category +"'" ,null);

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
            Toast.makeText(getApplicationContext(), "No Harvest to Sell!", Toast.LENGTH_SHORT).show();
        }
        StockListAdapter stock_adapter = new StockListAdapter(getApplicationContext(), product_name, product_quantity, product_price, product_code, f_email, product_image);
        list.setAdapter(stock_adapter);
        //code to set adapter to populate list
        cursor.close();

    }
    public void listItemClick(){

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String code = ((TextView) view.findViewById(R.id.et_procode)).getText().toString();
                String name = ((TextView) view.findViewById(R.id.et_proname)).getText().toString();
                String stock = ((TextView) view.findViewById(R.id.et_prostock)).getText().toString();
                String price = ((TextView) view.findViewById(R.id.et_proprice)).getText().toString();
                String farmer_mail = ((TextView) view.findViewById(R.id.et_proemail)).getText().toString();

                Intent intent = new Intent(getApplicationContext(), PaymentPage.class);
                intent.putExtra("email",email);
                intent.putExtra("code",code);
                intent.putExtra("name",name);
                intent.putExtra("stock",stock);
                intent.putExtra("price",price);
                intent.putExtra("far_email",farmer_mail);
                startActivity(intent);
            }
        });
    }
}