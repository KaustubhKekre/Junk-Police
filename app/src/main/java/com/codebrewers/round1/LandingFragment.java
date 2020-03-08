package com.codebrewers.round1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingFragment extends Fragment implements OnMapReadyCallback {


    Button save;
    public LandingFragment() {
        // Required empty public constructor
    }
//todo- Replace with actual landing page

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ListView grow,notGrow;
        final ProgressBar pbar;
        final TextView grown,notGrown;
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_landing, container, false);
        startActivity(new Intent(v.getContext(),MapsActivity2.class));
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
