package com.example.AgroFarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

public class EditStocks extends AppCompatActivity {

    String email,code, type;
    EditText name, status, stock, price;
    RadioButton veg, fruit;
    Button update, delete, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stocks);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        code = intent.getStringExtra("code");

        initialize();
        loadDataFromDB();
        backAct();
        updateData();
        deleteAct();
    }

    public void initialize(){

        name =  (EditText)findViewById(R.id.et_product_name_edit);
        status  =  (EditText)findViewById(R.id.et_product_status_edit);
        stock =  (EditText)findViewById(R.id.et_product_stock_edit);
        price =  (EditText)findViewById(R.id.et_product_price_edit);
        update =   findViewById(R.id.btn_edit_stock);
        delete =   findViewById(R.id.btn_delete_stock);
        back = (Button)findViewById(R.id.btn_back_edit_stock);

        veg  = (RadioButton)findViewById(R.id.rbt_veg_edit);
        fruit = (RadioButton)findViewById(R.id.rbt_fruit_edit);

    }

    public  void loadDataFromDB(){
        DBHandler dbHandler = new DBHandler(getApplicationContext());
        List stockItem = dbHandler.searchStockFromDB(code);

        if (stockItem.isEmpty()){
            Toast.makeText(getApplicationContext(), "Loading Error... Please try again!", Toast.LENGTH_SHORT).show();
        }
        else {

            name.setText(stockItem.get(1).toString());
            status.setText(stockItem.get(2).toString());
            stock.setText(stockItem.get(3).toString());
            price.setText(stockItem.get(4).toString());
            type=stockItem.get(5).toString();

            if(type.equals("Vegetable")){
                veg.setChecked(true);
            }else {
                fruit.setChecked(true);
            }
        }
    }
    public void updateData(){

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().matches("") || status.getText().toString().matches("") || stock.getText().toString().matches("") || price.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill all Data!", Toast.LENGTH_SHORT).show();

                }else{
                    DBHandler dbHandler = new DBHandler(getApplicationContext());

                    Boolean bool_status = dbHandler.updateStock(code, name.getText().toString(),status.getText().toString(), Integer.parseInt(stock.getText().toString()), Integer.parseInt(price.getText().toString()),type);
                    if (bool_status){
                        Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_SHORT).show();

                        name.setText(null);
                        status.setText(null);
                        stock.setText(null);
                        price.setText(null);
                        veg.setChecked(true);
                        fruit.setChecked(false);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Product Update Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void deleteAct(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().matches("") || status.getText().toString().matches("") || stock.getText().toString().matches("") || price.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill all Data!", Toast.LENGTH_SHORT).show();

                }else{
                    DBHandler dbHandler = new DBHandler(getApplicationContext());
                    dbHandler.deleteProduct(code);

                    Toast.makeText(getApplicationContext(), "Product Deleted", Toast.LENGTH_SHORT).show();
                    name.setText(null);
                    status.setText(null);
                    stock.setText(null);
                    price.setText(null);
                    veg.setChecked(true);
                    fruit.setChecked(false);
                }
            }
        });
    }

    public void backAct(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FarmerAct.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

}