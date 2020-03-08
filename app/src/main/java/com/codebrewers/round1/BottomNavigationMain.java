package com.codebrewers.round1;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class BottomNavigationMain extends AppCompatActivity {

    Handler mHandler=new Handler();
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompostFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        notificationManager = NotificationManagerCompat.from(this);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment tempFragment = null;

                switch(item.getItemId()){
                    case R.id.profile:
                        tempFragment = new ProfileFragment();
                        break;
                    case R.id.landing:
                        tempFragment = new LandingFragment();
                        break;

                    case R.id.chat:
                        tempFragment = new ChatFragment();
                        break;

                    case R.id.upload:
                        tempFragment = new UploadFragment();
                        break;
                    case R.id.compost:
                        tempFragment = new CompostFragment();
                        break;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tempFragment).commit();
                return true;
            }
        });
    }
}
