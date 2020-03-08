package com.codebrewers.round1;



import android.support.v7.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("auth/registration/")
    Call<RegistrationModel> registerUser(@Body RegistrationModel registrationModel);
    @POST("auth/token/login/")
    Call<LogInResponse> logIn(@Body LogInRequest logInRequest);
    @Multipart
    @POST("post/")
    Call<ResponseBody>uploadImage(@Header("Authorization")String header,@Part MultipartBody.Part part,
                                  @Part("description")RequestBody description,
                                  @Part("latitude") RequestBody latitude,
                                  @Part("longitude")RequestBody longitude);
    @POST("compost/")
    Call<CompostModel> compost(@Body CompostModel compostModel);

    @GET("compost-list/")
    Call<ArrayList<CompostModel>> compost_list();

    @GET("get-data/")
    Call<AQIModel>getAQI();

    @GET("notify")
    Call<notify> getNotif();
}
