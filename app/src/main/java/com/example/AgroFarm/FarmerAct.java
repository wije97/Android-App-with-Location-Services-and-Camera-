package com.example.AgroFarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FarmerAct extends AppCompatActivity {

    Button add, view, orders, back;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        initialize();
        buttonClickAct();

    }

    public  void initialize(){
        add = (Button)findViewById(R.id.btn_add);
        view = (Button)findViewById(R.id.btn_view);
        orders = (Button)findViewById(R.id.btn_orders_farmer);
        back = (Button)findViewById(R.id.btn_back_farmer);

    }

    public void buttonClickAct(){

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddStocks.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewStockFarmer.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewOrders.class);
                intent.putExtra("email",email);
                intent.putExtra("cat", 0);
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