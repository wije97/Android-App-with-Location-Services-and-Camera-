package com.example.AgroFarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LogInPage extends AppCompatActivity {

    Button login, signUp;
    EditText email, passWord;
    RadioButton farmer, buyer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        initialize();
        loginAct();
        signUpAct();
    }

    public void initialize(){

        login = (Button)findViewById(R.id.btn_login);
        signUp = (Button)findViewById(R.id.btn_signup);
        email =(EditText)findViewById(R.id.et_email);
        passWord =(EditText)findViewById(R.id.et_pword);
        buyer = (RadioButton)findViewById(R.id.rbt_buyer);
        farmer = (RadioButton)findViewById(R.id.rbt_farmer);
    }

    public void loginAct(){

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().matches("")){
                    email.setError("Please Enter Email");
                    email.requestFocus();
                }
                else if(passWord.getText().toString().trim().matches("")){
                    passWord.setError("Please Enter Password");
                    passWord.requestFocus();
                }else{

                    if(buyer.isChecked()){
                        DBHandler dbHandler = new DBHandler(getApplicationContext());
                        if(dbHandler.buyerLoginCheck(email.getText().toString(),passWord.getText().toString())){

                            Toast.makeText(getApplicationContext(), "Login Success !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), BuyerAct.class);
                            intent.putExtra("email",email.getText().toString());
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Please check Email and Password  !", Toast.LENGTH_SHORT).show();

                        }

                    }else if(farmer.isChecked()){

                        DBHandler dbHandler = new DBHandler(getApplicationContext());
                        if(dbHandler.farmerLoginCheck(email.getText().toString(),passWord.getText().toString())){

                            Toast.makeText(getApplicationContext(), "Login Success !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), FarmerAct.class);
                            intent.putExtra("email",email.getText().toString());
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Please check Email and Password !", Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            }
        });

    }
    public void signUpAct(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(intent);
            }
        });
    }
}