package com.codebrewers.round1;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.48:8000/api/").addConverterFactory(
                GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<AQIModel>aqiModelCall=retrofitInterface.getAQI();

        aqiModelCall.enqueue(new Callback<AQIModel>() {
            @Override
            public void onResponse(Call<AQIModel> call, Response<AQIModel> response) {
                LatLng aqi = new LatLng(Double.parseDouble(response.body().getLat()), Double.parseDouble(response.body().getLon()));
                mMap.addMarker(new MarkerOptions().position(aqi).title("AQI: "+response.body().getAqi()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).snippet("CO: "+response.body().getCo()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(aqi));
            }

            @Override
            public void onFailure(Call<AQIModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),""+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
