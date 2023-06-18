package com.example.AgroFarm;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class AddStocks extends Activity {

    String email;
    EditText code, name, status, stock, price;
    RadioButton veg, fruit;
    Button add, back;
    ImageView imageView;
    ImageButton addImage;

    Uri mImageUri;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stocks);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        initialize();
        getImage();
        addAct();
        backAct();

    }

    public void initialize(){
        code = (EditText)findViewById(R.id.et_product_code);
        name =  (EditText)findViewById(R.id.et_product_name);
        status  =  (EditText)findViewById(R.id.et_product_status);
        stock =  (EditText)findViewById(R.id.et_product_stock);
        price =  (EditText)findViewById(R.id.et_product_price);
        add =   findViewById(R.id.btn_add_stock);
        back = (Button)findViewById(R.id.btn_back_add_stock);
        imageView = (ImageView)findViewById(R.id.iv_picture);
        addImage = (ImageButton)findViewById(R.id.ibtn_image);

        veg  = (RadioButton)findViewById(R.id.rbt_veg);
        fruit = (RadioButton)findViewById(R.id.rbt_fruit);


    }

    public void addAct(){

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(code.getText().toString().matches("") || name.getText().toString().matches("") || status.getText().toString().matches("") || stock.getText().toString().matches("") || price.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill all Data!", Toast.LENGTH_SHORT).show();

                }else{
                    String type;
                    if (veg.isChecked()){
                        type = "Vegetable";
                    }else{
                        type = "Fruit";
                    }

                    imageView.setDrawingCacheEnabled(true);

                    imageView.buildDrawingCache();

                    Bitmap bm = imageView.getDrawingCache();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bytesImage = stream.toByteArray();

                    DBHandler dbHandler = new DBHandler(getApplicationContext());
                    long newID = dbHandler.harvestData(Integer.parseInt(code.getText().toString()), name.getText().toString(), status.getText().toString(), Integer.parseInt(stock.getText().toString()), Integer.parseInt(price.getText().toString()), type, email, bytesImage);

                    if(newID != 0){
                        Toast.makeText(getApplicationContext(), "Stock Added. ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),AddStocks.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Failed! ", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

    }

    public void getImage(){
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChoosedFile(view);
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

    public  void onChoosedFile(View v){

        CropImage.activity().start(AddStocks.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode==RESULT_OK){
                mImageUri = result.getUri();
                imageView.setImageURI(mImageUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
                Toast.makeText(getApplicationContext(), "Error:" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
