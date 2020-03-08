package com.codebrewers.round1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInSignUp extends AppCompatActivity {

    TextView userDetail,password,signup,login;
    ProgressBar progress_login;
    String user,pwd;
    SharedPreferences spref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_sign_up);
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        userDetail=findViewById(R.id.userDetail);
        password=findViewById(R.id.pwd);
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        edit = spref.edit();
        progress_login=findViewById(R.id.progress_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=userDetail.getText().toString();
                pwd=password.getText().toString();

                login.setVisibility(View.GONE);
                progress_login.setVisibility(View.VISIBLE);
                LogInRequest logInRequest=new LogInRequest(user,pwd);
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.17:8000/api/").addConverterFactory(
                        GsonConverterFactory.create()).build();
                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
                Call<LogInResponse> logInResponseCall=retrofitInterface.logIn(logInRequest);
                logInResponseCall.enqueue(new Callback<LogInResponse>() {
                    @Override
                    public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                        LogInResponse logInResponse=response.body();
                        if(logInResponse!=null)
                        {
                            edit.putString("token", "Token "+logInResponse.getAuth_token());
                            edit.commit();
                            Toast.makeText(LogInSignUp.this,"Welcome!!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LogInSignUp.this,BottomNavigationMain.class));
                            finish();}
                        else
                        {
                            Toast.makeText(LogInSignUp.this,""+response.errorBody(),Toast.LENGTH_LONG).show();
                            login.setVisibility(View.VISIBLE);
                            progress_login.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<LogInResponse> call, Throwable t) {
                        Toast.makeText(LogInSignUp.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
                        login.setVisibility(View.VISIBLE);
                        progress_login.setVisibility(View.GONE);

                    }
                });
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LogInSignUp.this,RegistrationForm.class));
                finish();

            }
        });

    }
}
