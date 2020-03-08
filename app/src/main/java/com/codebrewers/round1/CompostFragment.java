package com.codebrewers.round1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompostFragment extends Fragment {


    public CompostFragment() {
        // Required empty public constructor
    }
CardView admin,hotel,farmer,location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_compost, container, false);
        admin=v.findViewById(R.id.admin);
        hotel=v.findViewById(R.id.hotel);
        farmer=v.findViewById(R.id.farmer);
        location=v.findViewById(R.id.maps);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),admin.class));
            }
        });
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Compost.class);
                intent.putExtra("user","hotel");
                startActivity(intent);
            }
        });
        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Compost.class);
                intent.putExtra("user","farmer");
                startActivity(intent);
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getActivity(),MapsActivity.class));
            }
        });
        return v;
    }

}
