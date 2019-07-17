package com.myapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.myapplication.R;
import com.myapplication.model.MultipleResource;
import com.myapplication.utils.APIClient;
import com.myapplication.utils.APIInterface;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitGetResponse extends AppCompatActivity {

    public TextView tv;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_get_response);

        tv=findViewById(R.id.tv);
        apiInterface= APIClient.getclient().create(APIInterface.class);

        Call<MultipleResource> call=apiInterface.doGetListResource();

        call.enqueue(new Callback<MultipleResource>() {

            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {
                Log.e("Response", String.valueOf(response.body()));
                Toast.makeText(getApplicationContext(),"Sucess"+String.valueOf(response.body()),Toast.LENGTH_SHORT).show();

                MultipleResource resource=response.body();

                String displayresponse="";

                int page=resource.page;
                int total=resource.total;
                int totalpages=resource.total_pages;
                List<MultipleResource.Dataum> dataumList=resource.data;

                displayresponse+="Page : "+page+"\n Total : "+total+"\n Total Pages : "+totalpages;

                for(MultipleResource.Dataum dataum:dataumList ){
                    displayresponse+="Id : "+dataum.id+"\n Name : "+dataum.name+"\n PantoneValue : "+dataum.pantone_value+"\n Year : "+dataum.year;
                }

                tv.setText(""+displayresponse);
            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                Log.e("Error",call.toString());
                call.cancel();
            }
        });
    }
}
