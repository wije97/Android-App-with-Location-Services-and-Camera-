package com.example.AgroFarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BuyerAct extends AppCompatActivity {

    Button veg, fruit, payments, back, map;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        initialize();
        buttonClickAct();

    }

    public  void initialize(){
        veg = (Button)findViewById(R.id.btn_veg);
        fruit = (Button)findViewById(R.id.btn_fruit);
        payments= (Button)findViewById(R.id.btn_payments);
        back = (Button)findViewById(R.id.btn_back_buyer);
        map = (Button)findViewById(R.id.btn_map);

    }

    public void buttonClickAct(){

        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewStocksBuyer.class);
                intent.putExtra("email",email);
                intent.putExtra("category","Vegetable");
                startActivity(intent);
            }
        });

        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewStocksBuyer.class);
                intent.putExtra("email",email);
                intent.putExtra("category","Fruit");
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Map.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewOrders.class);
                intent.putExtra("email",email);
                intent.putExtra("cat", 1);

                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogInPage.class);
                startActivity(intent);
            }
        });

    }
}