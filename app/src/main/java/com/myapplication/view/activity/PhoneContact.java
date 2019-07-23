package com.myapplication.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.R;
import com.myapplication.utils.MyApplication;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

public class PhoneContact extends AppCompatActivity implements InternetConnectivityListener {

    EditText contactET;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int PICK_CONTACT = 1;
    String sNumber,sName;
    TextView nameTV;
    TextView internetconnectionTV;
    private InternetAvailabilityChecker mInternetAvailabilityChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contact);

        InternetAvailabilityChecker.init(this);

        contactET=findViewById(R.id.contactET);
        nameTV=findViewById(R.id.nameTV);
        internetconnectionTV=findViewById(R.id.internetconnectionTV);

        mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);

        contactET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!checkPermission()){
                    requestPermission();
                }else{
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                }

            }
        });

    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PhoneContact.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {

            case (PICK_CONTACT):

                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);

                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);

                            phones.moveToFirst();
                            sNumber = phones.getString(phones.getColumnIndex("data1"));
                            System.out.println("number is:" + sNumber);
                        }
                        sName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        nameTV.setText(sName);
                        contactET.setText(sNumber);
                     }
                }
        }
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

        if (isConnected) {
            internetconnectionTV.setText("connected");
        } else {
            internetconnectionTV.setText("not connected");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInternetAvailabilityChecker.removeInternetConnectivityChangeListener(this);
    }
}
