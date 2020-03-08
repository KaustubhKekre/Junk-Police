package com.codebrewers.round1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class admin extends AppCompatActivity {
    String[] amt;
    String[] sign;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView available;
    SharedPreferences spref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        spref=getApplicationContext().getSharedPreferences("compost", MODE_PRIVATE);
        recyclerView=findViewById(R.id.recycler);
        available=findViewById(R.id.available);
        available.setText(spref.getString("total","0")+" kg");
        progressBar=findViewById(R.id.progressbar);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.17:8000/api/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<ArrayList<CompostModel>> com=retrofitInterface.compost_list();
        com.enqueue(new Callback<ArrayList<CompostModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CompostModel>> call, Response<ArrayList<CompostModel>> response) {
                if(response.body()!=null){
                    amt=new String[response.body().size()];
                    sign=new String[response.body().size()];
                    progressBar.setVisibility(View.GONE);
                    for(int i=0;i<response.body().size();i++){
                       amt[i]=response.body().get(i).getAmt();
                       sign[i]=response.body().get(i).getSign();
                    }


                    recyclerView.setLayoutManager(new LinearLayoutManager(admin.this));

                    recyclerView.setAdapter(new CompostAdapter(amt,sign));

                }
                else{
                    Toast.makeText(admin.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CompostModel>> call, Throwable t) {
                Toast.makeText(admin.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
