package com.myapplication.utils;

import android.content.Context;

import androidx.room.Room;

public class DatabasClient {

    private Context context;
    private AppDatabase databasClient;
    private static DatabasClient mInstance;

    public DatabasClient(Context context){
        this.context=context;
        this.databasClient= Room.databaseBuilder(context,AppDatabase.class,"MyTodO").build();
    }

    public static synchronized DatabasClient getmInstance(Context context){

        if(mInstance==null){
            mInstance=new DatabasClient(context);
        }

        return mInstance;
    }

    public AppDatabase getdatabase(){
        return  databasClient;
    }

}
