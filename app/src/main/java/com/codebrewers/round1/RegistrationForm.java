package com.codebrewers.round1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.RegisterListenerMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationForm extends AppCompatActivity {
    AutoCompleteTextView username,password,phone_number,email;
    TextView save,login2;
    ProgressBar pbar;
    SharedPreferences spref;
    SharedPreferences.Editor edit;
    ImageView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        username=findViewById(R.id.username);
        phone_number=findViewById(R.id.phone_number);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        save=findViewById(R.id.save);
        pbar=findViewById(R.id.pbar);
        login=findViewById(R.id.login);
        login2=findViewById(R.id.login2);
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        edit = spref.edit();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationForm.this,LogInSignUp.class));
                finish();
            }
        });
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationForm.this,LogInSignUp.class));
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setVisibility(View.GONE);
                pbar.setVisibility(View.VISIBLE);
                RegistrationModel registrationModel=new RegistrationModel(email.getText().toString(),
                        username.getText().toString(),phone_number.getText().toString(),
                        password.getText().toString());
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.48:8000/api/").addConverterFactory(
                        GsonConverterFactory.create()).build();
                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
                Call<RegistrationModel> registrationModelCall = retrofitInterface.registerUser(registrationModel);
                registrationModelCall.enqueue(new Callback<RegistrationModel>() {
                    @Override
                    public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {if(response.body()!=null){

                        edit.putString("name",username.getText().toString());
                        edit.putString("email",email.getText().toString());
                        edit.putString("phone",phone_number.getText().toString());
                        edit.commit();
                        Toast.makeText(RegistrationForm.this, "Registration Successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistrationForm.this,LogInSignUp.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(RegistrationForm.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        save.setVisibility(View.VISIBLE);
                        pbar.setVisibility(View.GONE);

                    }
                    }

                    @Override
                    public void onFailure(Call<RegistrationModel> call, Throwable t) {
                        Toast.makeText(RegistrationForm.this, ""+t.getMessage(), Toast.LENGTH_LONG).show();
                        save.setVisibility(View.VISIBLE);
                        pbar.setVisibility(View.GONE);

                    }
                });

            }
        });

    }
}
