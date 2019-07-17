package com.myapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.codemybrainsout.placesearch.PlaceSearchDialog;
import com.myapplication.R;

public class LocationSearch extends AppCompatActivity {

    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);

        edt=findViewById(R.id.edt);

        edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    showPlacePickerDialog();
                }
                return false;
            }
        });
    }

    private void showPlacePickerDialog() {

        PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog.Builder(this)
                //.setHeaderImage(R.drawable.dialog_header)
                .setLocationNameListener(new PlaceSearchDialog.LocationNameListener() {
                    @Override
                    public void locationName(String locationName) {
                        edt.setText(locationName);
                    }
                })
                .build();
        placeSearchDialog.show();
    }
}
