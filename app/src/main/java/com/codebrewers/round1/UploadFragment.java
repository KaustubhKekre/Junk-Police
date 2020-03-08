package com.codebrewers.round1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class UploadFragment extends Fragment {


    public UploadFragment() {
        // Required empty public constructor
    }
    AutoCompleteTextView description;
    ImageView image;
    TextView upload,take;
    String pathToFile;
    ProgressBar progress_upload;
    SharedPreferences spref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_upload, container, false);
        spref = v.getContext().getSharedPreferences("user", MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        description=v.findViewById(R.id.description);
        progress_upload=v.findViewById(R.id.progress_upload);
        image = v.findViewById(R.id.image);
        upload = v.findViewById(R.id.upload);
        take=v.findViewById(R.id.take);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChoose();
            }
        });
return v;
    }

    private void imageChoose(){
        Intent takePic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getActivity().getPackageManager())!=null){
            File photoFile=null;

            photoFile=createPhotoFile();
            if(photoFile!=null){
                pathToFile=photoFile.getAbsolutePath();
                final Uri photoURI= FileProvider.getUriForFile(getActivity(),"com.codebrewers.round1.fileprovider",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePic,1);
                upload.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);

                final File finalPhotoFile = photoFile;
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upload.setVisibility(View.GONE);
                        progress_upload.setVisibility(View.VISIBLE);
                        File file= new File(pathToFile);
                        RequestBody requestFile =
                                RequestBody.create(MediaType.parse("image/png"), file);
                        MultipartBody.Part fileData =
                                MultipartBody.Part.createFormData("file", "image.png", requestFile);

                        RequestBody descriptionPart = RequestBody.create(
                                MediaType.parse("text/plain"),
                                description.getText().toString());
                        RequestBody latPart = RequestBody.create(
                                MediaType.parse("text/plain"),
                                spref.getString("lat","latitude"));
                        RequestBody lonPart = RequestBody.create(
                                MediaType.parse("text/plain"),
                                spref.getString("lon","longitude"));

                        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.48:8000/api/").addConverterFactory(
                                GsonConverterFactory.create()).build();
                        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
                        Call<ResponseBody> call = retrofitInterface.uploadImage(spref.getString("token", null),fileData,descriptionPart, latPart,lonPart);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                upload.setVisibility(View.GONE);
                                progress_upload.setVisibility(View.GONE);
                                description.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),"Upload successful. Thank you for notifying",Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getActivity(),"Error-"+t.getCause(),Toast.LENGTH_LONG).show();
                            }
                        });



                    }
                });
            }
            else
            {
                Toast.makeText(getActivity(),"Something went wrong. Please try again",Toast.LENGTH_LONG).show();
                upload.setVisibility(View.GONE);
                description.setVisibility(View.GONE);

            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1){
                Bitmap bitmap= BitmapFactory.decodeFile(pathToFile);
                image.setImageBitmap(bitmap);
            }
        }
    }

    private File createPhotoFile() {
        String name=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir=getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image=null;

        try {
            image=File.createTempFile(name,".jpg",storageDir);

        } catch (IOException e) {
            Log.d("myLog", "error "+e.toString());
        }
        return image;
    }

}
