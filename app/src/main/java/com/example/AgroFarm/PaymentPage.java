package com.example.AgroFarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentPage extends AppCompatActivity {

    TextView sup_email, pro_name, quantity, price;
    EditText holder_name, carrd_no, cvv, exp_date;
    Button pay_now,back;
    String email,code,name,stock,price_int,far_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        code = intent.getStringExtra("code");
        name = intent.getStringExtra("name");
        stock = intent.getStringExtra("stock");
        price_int = intent.getStringExtra("price");
        far_email = intent.getStringExtra("far_email");

        initialize();
        payButtonClick();
        backAct();

    }

    public void initialize(){

        sup_email = (TextView)findViewById(R.id.tv_sup_email);
        pro_name = (TextView)findViewById(R.id.tv_proname_pay);
        quantity= (TextView)findViewById(R.id.tv_prostock_pay);
        price = (TextView)findViewById(R.id.tv_proprice_pay);

        holder_name = (EditText) findViewById(R.id.et_holder_name);
        carrd_no = (EditText) findViewById(R.id.et_card_number);
        cvv = (EditText) findViewById(R.id.et_cvv_number);
        exp_date = (EditText) findViewById(R.id.et_exp_date);

        pay_now = (Button)findViewById(R.id.btn_pay);
        back = (Button)findViewById(R.id.btn_back_pay);



        sup_email.setText(far_email);
        pro_name.setText(name);
        quantity.setText(stock + " Kg");
        price.setText("Rs:  " +price_int);
    }

    public void payButtonClick(){

        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder_name.getText().toString().matches("") || carrd_no.getText().toString().matches("") || cvv.getText().toString().matches("") || exp_date.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill all Data!", Toast.LENGTH_SHORT).show();

                }else{

                    DBHandler dbHandler = new DBHandler(getApplicationContext());
                    long newID = dbHandler.ordersData(Integer.parseInt(code), Integer.parseInt(stock), Double.parseDouble(price_int), far_email, email);

                    if(newID != 0){
                        Toast.makeText(getApplicationContext(), "Payment Done. ", Toast.LENGTH_SHORT).show();
                        holder_name.setText(null);
                        carrd_no.setText(null);
                        cvv.setText(null);
                        exp_date.setText(null);

                        sup_email.setText(null);
                        pro_name.setText(null);
                        quantity.setText(null);
                        price.setText(null);
                    }else{
                        Toast.makeText(getApplicationContext(), "Failed! ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void backAct(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuyerAct.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}