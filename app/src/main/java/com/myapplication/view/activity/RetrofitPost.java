package com.myapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.myapplication.R;
import com.myapplication.model.LoginRequest;
import com.myapplication.model.LoginResponse;
import com.myapplication.utils.APIClient;
import com.myapplication.utils.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitPost extends AppCompatActivity {

    EditText edt,edt1;
    Button btn;
    String phone="",firebaseauth="";
    RelativeLayout rl;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_post);

        edt=findViewById(R.id.edt);
        edt1=findViewById(R.id.edt1);
        btn=findViewById(R.id.btn);
        rl=findViewById(R.id.rl);

        apiInterface= APIClient.getclient().create(APIInterface.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone=edt.getText().toString();
                if(phone.trim().length()!=10){
                    Snackbar.make(rl,"Enter Valid Phone Number!!",Snackbar.LENGTH_SHORT).show();
                }else{

                    Call<LoginResponse> call=apiInterface.loginresponse(new LoginRequest(phone,firebaseauth));

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Log.e("Response", String.valueOf(response.body()));

                            LoginResponse loginResponse=response.body();

                            String status=loginResponse.status;
                            int id=loginResponse.id;
                            String name=loginResponse.name;
                            String primary_mobile=loginResponse.primary_mobile;

                            Toast.makeText(getApplicationContext(),"Status : "+status+"\n Id : "+id+"\n Name : "+name+"\n Mobile : "+primary_mobile,Toast.LENGTH_SHORT).show();
                            //Snackbar.make(rl,"Status : "+status+"\n Id : "+id+"\n Name : "+name+"\n Mobile : "+primary_mobile,Snackbar.LENGTH_LONG).show();

                            if(status.equalsIgnoreCase("fail")){

                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {

                            Log.e("Error", call.toString());

                        }
                    });
                }

            }
        });
    }
}
