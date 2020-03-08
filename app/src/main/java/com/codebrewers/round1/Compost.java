package com.codebrewers.round1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Compost extends AppCompatActivity {
TextView login,available;
AutoCompleteTextView amount;
ProgressBar progressBar;
SharedPreferences spref;
SharedPreferences.Editor edit;
String user;
CompostModel compostModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compost);
        user = getIntent().getStringExtra("user");
        spref = getApplicationContext().getSharedPreferences("compost", MODE_PRIVATE);
        edit = spref.edit();
        login=findViewById(R.id.login);
        available=findViewById(R.id.available);
        available.setText(spref.getString("total","0")+" kg");
        amount=findViewById(R.id.amount);
        progressBar=findViewById(R.id.progress_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.48:8000/api/").addConverterFactory(
                        GsonConverterFactory.create()).build();
                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
                if(user.equals("farmer")){
                    compostModel=new CompostModel(amount.getText().toString(),"-");
                    float a=Float.parseFloat(spref.getString("total","0"));
                    a=a-Float.parseFloat(amount.getText().toString());
                    edit.putString("total",""+a);
                    edit.commit();
                }
                else{
                    compostModel=new CompostModel(amount.getText().toString(),"+");
                    float a= (float) (0.7*Float.parseFloat(spref.getString("total","0")));
                    a=a+Float.parseFloat(amount.getText().toString());
                    edit.putString("total",""+a);
                    edit.commit();
                }
                Call<CompostModel>compostModelCall=retrofitInterface.compost(compostModel);
                compostModelCall.enqueue(new Callback<CompostModel>() {
                    @Override
                    public void onResponse(Call<CompostModel> call, Response<CompostModel> response) {
                        Toast.makeText(Compost.this,"Request raised",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Compost.this,BottomNavigationMain.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<CompostModel> call, Throwable t) {
                        Toast.makeText(Compost.this,""+t.getMessage(),Toast.LENGTH_LONG);
                        login.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
