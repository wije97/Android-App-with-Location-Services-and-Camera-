package com.example.AgroFarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SignUpPage extends AppCompatActivity {

    Button signUp, locate, back;
    EditText ful_name, address, email, mobile, password;
    RadioButton farmer, buyer;
    RadioGroup radioGroup;
    LinearLayout layout;

    FusedLocationProviderClient mFusedLocationClient;

    TextView latitude, longitude;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        initialize();
        signUPAct();
        radioBtnCheck();
        backAct();
        getLocationClick();

    }

    public void initialize(){

        back = (Button)findViewById(R.id.btn_back);
        locate = (Button)findViewById(R.id.btn_set_location);
        signUp = (Button)findViewById(R.id.btn_create_acc);
        ful_name =(EditText)findViewById(R.id.et_name);
        address =(EditText)findViewById(R.id.et_address);
        mobile =(EditText)findViewById(R.id.et_mobile);
        email =(EditText)findViewById(R.id.et_email_sign);
        password =(EditText)findViewById(R.id.et_password_sign);
        buyer = (RadioButton)findViewById(R.id.rbt_buyer_sign);
        farmer = (RadioButton)findViewById(R.id.rbt_farmer_sign);
        radioGroup = (RadioGroup) findViewById(R.id.rbt_grp_type);
        layout = (LinearLayout)findViewById(R.id.lyt_set_location);

        latitude = (TextView)findViewById(R.id.tv_lat);
        longitude = (TextView)findViewById(R.id.tv_longt);

        latitude.setText("-");
        longitude.setText("-");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }
    public void radioBtnCheck(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (buyer.isChecked()) {
                    layout.setVisibility(View.GONE);
                } else if (farmer.isChecked()) {
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    public void getLocationClick(){
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });
    }

    public void signUPAct(){

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ful_name.getText().toString().trim().matches("") || address.getText().toString().trim().matches("") || mobile.getText().toString().trim().matches("") || email.getText().toString().trim().matches("") || password.getText().toString().trim().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill all data" , Toast.LENGTH_SHORT).show();
                }
                else{

                    if(buyer.isChecked()){

                        DBHandler dbHandler = new DBHandler(getApplicationContext());
                        String character = "Buyer";
                        long newID = dbHandler.buyerData(ful_name.getText().toString(), address.getText().toString(), mobile.getText().toString(), character,  email.getText().toString(), password.getText().toString());

                        if(newID !=0){
                            Toast.makeText(getApplicationContext(), "Buyer Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), BuyerAct.class);
                            intent.putExtra("email",email.getText().toString());
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }



                    }else{

                        DBHandler dbHandler = new DBHandler(getApplicationContext());
                        String character = "Farmer";

                        double longt, lat;

                        lat = Double.parseDouble(latitude.getText().toString());
                        longt = Double.parseDouble(longitude.getText().toString());

                        long newID = dbHandler.farmerData(ful_name.getText().toString(), address.getText().toString(), mobile.getText().toString(), character,  email.getText().toString(), password.getText().toString(), lat, longt);

                        if(newID !=0){
                            Toast.makeText(getApplicationContext(), "Farmer Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), FarmerAct.class);
                            intent.putExtra("email",email.getText().toString());
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
    }

    public void backAct(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogInPage.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude.setText(String.valueOf(location.getLatitude()));
                            longitude.setText(String.valueOf(location.getLongitude()));
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();

            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            latitude.setText(String.valueOf(mLastLocation.getLatitude()));
            longitude.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}