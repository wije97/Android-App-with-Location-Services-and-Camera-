package com.example.AgroFarm;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;


    private GoogleMap mMap;
    SQLiteDatabase  db;
    String email;

    double lat_current, long_current;
    private ArrayList<String> name;
    private ArrayList<String> contact;
    private ArrayList<Double> lat;
    private ArrayList<Double> longt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        initialize();
        getLastLocation();

    }

    public void initialize(){

        name = new ArrayList<String>();
        contact = new ArrayList<String>();
        lat = new ArrayList<Double>();
        longt = new ArrayList<Double>();
    }

    public void showData(){

        DBHandler dbHandler = new DBHandler(this);

        db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name, Mobile, Latitude, Longitude FROM  Farmers " ,null);

        name.clear();
        contact.clear();
        lat.clear();
        lat.clear();

        if (cursor.moveToFirst()) {
            do {
                name.add(cursor.getString(cursor.getColumnIndex("Name")));
                contact.add(cursor.getString(cursor.getColumnIndex("Mobile")));
                lat.add(cursor.getDouble(cursor.getColumnIndex("Latitude")));
                longt.add(cursor.getDouble(cursor.getColumnIndex("Longitude")));
            } while (cursor.moveToNext());
        }else{
            Toast.makeText(getApplicationContext(), "No Data!", Toast.LENGTH_SHORT).show();
        }
        cursor.close();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(lat.size()>0){
            final MarkerOptions markerOpt = new MarkerOptions();
            LatLng Sloat = null;


            for(int i = 0; i< lat.size(); i++){
                Sloat = new LatLng(lat.get(i), longt.get(i));
                mMap.addMarker(new MarkerOptions().position(Sloat).title(name.get(i)).snippet(contact.get(i)));

            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Sloat,18.0f));
        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                showData();

            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();

            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }
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
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}