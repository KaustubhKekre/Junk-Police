package com.codebrewers.round1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class SplashScreen extends AppCompatActivity {
    String lats, longitudes;
    private LocationManager lms;
    static SharedPreferences spref;
    SharedPreferences.Editor edit;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setContentView(R.layout.activity_splash_screen);
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        edit = spref.edit();
        lms = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getlocation();
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    public void getlocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        Location locations = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (locations != null) {
            lats = ""+locations.getLatitude();
            longitudes =""+ locations.getLongitude();
            edit.putString("lat",lats);
            edit.putString("lon",longitudes);
            edit.commit();
        }
    }
}
