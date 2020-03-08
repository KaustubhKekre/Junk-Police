package com.codebrewers.round1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    TextView name,phone,email,logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences spref;

        final SharedPreferences.Editor edit;

        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        spref = v.getContext().getSharedPreferences("user", MODE_PRIVATE);
        edit = spref.edit();
        name=v.findViewById(R.id.name);
        phone=v.findViewById(R.id.phone);
        email=v.findViewById(R.id.email);
        logout=v.findViewById(R.id.logout);

        name.setText(spref.getString("name","name"));
        phone.setText(spref.getString("phone","phone"));
        email.setText(spref.getString("email","email"));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.clear();
                edit.commit();
                startActivity(new Intent(v.getContext(),MainActivity.class));
                getActivity().finish();

            }
        });
        return v;
    }


}
