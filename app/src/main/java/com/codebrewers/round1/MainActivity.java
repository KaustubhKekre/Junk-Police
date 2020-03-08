package com.codebrewers.round1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SharedPreferences spref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        if(spref.getString("token","1234").equals("1234")){
            Intent intent = new Intent(MainActivity.this, LogInSignUp.class);
            startActivity(intent);
            finish();
        }
        else
        {
            startActivity(new Intent(MainActivity.this, BottomNavigationMain.class));
            finish();
        }

    }
}
