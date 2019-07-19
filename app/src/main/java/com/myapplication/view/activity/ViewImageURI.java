package com.myapplication.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.myapplication.BuildConfig;
import com.myapplication.R;
import com.myapplication.utils.FileSaveUtil;
import com.myapplication.utils.PrefUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewImageURI extends AppCompatActivity {

    CircleImageView imgIV;
    TextView TV;

    String camPicPath="";
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int FROM_CAMERA = 1;
    private static final int FROM_GALLERY = 2;
    String picpath="";
    FileInputStream fis;
    File mCurrentPhotoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_uri);

        imgIV=findViewById(R.id.imgIV);
        TV=findViewById(R.id.TV);

        TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });

        String path=PrefUtils.getFromPrefs(ViewImageURI.this,"picpath","");
        Log.e("Sess",path);

        if(path.length()!=0){
            Glide.with(ViewImageURI.this).load(path).into(imgIV);
        }
    }

    public void showdialog(){

        Toast.makeText(getApplicationContext(),"Select Image to Upload",Toast.LENGTH_SHORT).show();
        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(ViewImageURI.this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
        bottomSheetDialog.show();

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                bottomSheetDialog.dismiss();
            }
        });
        LinearLayout camera = (LinearLayout) bottomSheetDialog.findViewById(R.id.camera);
        LinearLayout gallery = (LinearLayout) bottomSheetDialog.findViewById(R.id.gallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String state= Environment.getExternalStorageState();

                if(Environment.MEDIA_MOUNTED.equals(state)){
                    camPicPath=getSavePicPath();

                    if(!checkPermission()){
                        requestPermission();
                    }else{
                        openCamera(ViewImageURI.this, camPicPath);
                    }
                }
            }

        });

        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!checkPermission()){
                    requestPermission();
                }else {

                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                    } else {
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.putExtra("crop", "true");
                        intent.putExtra("scale", "true");
                        intent.putExtra("scaleUpIfNeeded", true);
                    }
                    intent.setType("image/*");
                    startActivityForResult(intent,
                            FROM_GALLERY);
                }
            }
        });
    }

    String getSavePicPath(){

        String directory= FileSaveUtil.SD_CARD_PATH+"image_a/";
        try {
            FileSaveUtil.createSDDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename=String.valueOf(System.currentTimeMillis()+".png");

        Log.e("getsave",directory+filename);

        return directory+filename;
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case FROM_CAMERA:

                try {
                    fis=new FileInputStream(camPicPath);
                    File camfile=new File(camPicPath);

                    if(camfile.exists()){
                        picpath=camPicPath;
                        PrefUtils.saveToPrefs(ViewImageURI.this,"picpath",picpath);
                        Glide.with(ViewImageURI.this).load(picpath).into(imgIV);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case  FROM_GALLERY:

                Uri uri = data.getData();
                String path = FileSaveUtil.getPath(getApplicationContext(), uri);
                mCurrentPhotoFile = new File(path);
                if (mCurrentPhotoFile.exists()) {
                    picpath = path;
                    PrefUtils.saveToPrefs(ViewImageURI.this,"picpath",picpath);
                    Glide.with(ViewImageURI.this).load(mCurrentPhotoFile).into(imgIV);
                } else {
                    Toast.makeText(ViewImageURI.this, "File Does Not Exist!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
        new AlertDialog.Builder(ViewImageURI.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private void openCamera(ViewImageURI viewImageURI, String camPicPath) {

        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = FileProvider.getUriForFile(ViewImageURI.this,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                new File(camPicPath));
        // Uri uri = Uri.fromFile(new File(camPicPath));
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(openCameraIntent,
                FROM_CAMERA);
    }
}
