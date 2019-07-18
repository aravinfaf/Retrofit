package com.myapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.R;
import com.myapplication.model.book;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmCRUD extends AppCompatActivity implements View.OnClickListener {

    Realm realm;
    EditText rollnoET,nameET;
    Button addBT,viewBT,updateBT,deleteBT,viewsingleBT;
    String ronllno="",name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_crud);

        Realm.init(getApplicationContext());
        realm=Realm.getDefaultInstance();

        rollnoET=findViewById(R.id.rollnoET);
        nameET=findViewById(R.id.nameET);
        addBT=findViewById(R.id.addBT);
        viewBT=findViewById(R.id.viewBT);
        updateBT=findViewById(R.id.updateBT);
        deleteBT=findViewById(R.id.deleteBT);
        viewsingleBT=findViewById(R.id.viewsingleBT);

        addBT.setOnClickListener(this);
        viewBT.setOnClickListener(this);
        updateBT.setOnClickListener(this);
        deleteBT.setOnClickListener(this);
        viewsingleBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view==addBT){
            insertdata();
        }else if(view==viewBT){
            viewall();
        }else if(view==updateBT){
            update();
        }else if(view==deleteBT){
            delete();
        }else if(view==viewsingleBT){
            viewsingle();
        }
    }

    void insertdata() {

        ronllno = rollnoET.getText().toString();
        name = nameET.getText().toString();

        realm.beginTransaction();

//        RealmResults<book> realmResults = realm.where(book.class).equalTo("roll_no", Integer.parseInt(ronllno)).findAll();
//        if (realmResults.size() > 0) {
//            for (book b : realmResults) {
//
//                if (b.getRoll_no() == Integer.parseInt(ronllno)) {
//                    Toast.makeText(getApplicationContext(), "Duplicate Entry!!!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } else{
            book b = realm.createObject(book.class);
        b.setRoll_no(Integer.parseInt(ronllno));
        b.setName(name);

        realm.commitTransaction();
        Toast.makeText(getApplicationContext(), "Inserted!!!", Toast.LENGTH_SHORT).show();
   // }
        cleartext();

    }

    void viewall(){

        ronllno=rollnoET.getText().toString();
        name=nameET.getText().toString();

        RealmResults<book> realmResults=realm.where(book.class).findAll();

        for(book b: realmResults){
            Toast.makeText(getApplicationContext(),"View : "+b.getName(),Toast.LENGTH_SHORT).show();
        }
    }

    void update(){

        ronllno=rollnoET.getText().toString();
        name=nameET.getText().toString();

        realm.beginTransaction();
        RealmResults<book> realmResults=realm.where(book.class).equalTo("roll_no",Integer.parseInt(ronllno)).findAll();

        for(book b:realmResults){
            b.setName(name);
        }
        realm.commitTransaction();
        Toast.makeText(getApplicationContext(),"Updated...",Toast.LENGTH_SHORT).show();
        cleartext();
    }

    void  delete(){

        ronllno=rollnoET.getText().toString();
        name=nameET.getText().toString();

        realm.beginTransaction();
        RealmResults<book> realmResults=realm.where(book.class).equalTo("roll_no",Integer.parseInt(ronllno)).findAll();

        realmResults.deleteAllFromRealm();
        realm.commitTransaction();
        Toast.makeText(getApplicationContext(),"Deleted...",Toast.LENGTH_SHORT).show();
        cleartext();
    }

    void viewsingle(){

        ronllno=rollnoET.getText().toString();
        name=nameET.getText().toString();

        realm.beginTransaction();
        RealmResults<book> realmResults=realm.where(book.class).equalTo("roll_no",Integer.parseInt(ronllno)).findAll();

        Toast.makeText(getApplicationContext(),"Detail..."+realmResults.get(0).getName(),Toast.LENGTH_SHORT).show();
    }

    void cleartext(){
        rollnoET.setText("");
        nameET.setText("");
    }
}
