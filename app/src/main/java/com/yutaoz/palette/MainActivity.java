package com.yutaoz.palette;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity<Create> extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int READ_REQUEST = 1889;
    private static final int WRITE_REQUEST = 1890;
    Uri u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button takePicButton = findViewById(R.id.button4); //picture button setup
        takePicButton.setOnClickListener(new View.OnClickListener() { //creating listener
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

    }

    private void openCamera () {

        // request read permissions so that we can take image from gallery
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_REQUEST);
        }
        // request write permissions so that we can store images to gallery
        else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_REQUEST);
        }
        else {

            //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            Intent imgIntent = new Intent(Intent.ACTION_PICK, // open gallery intent
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(imgIntent, 0);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {

            // storing image as bitmap and starting imageview activity
            Uri targetUri = data.getData();

            Intent imgAct = new Intent(MainActivity.this, ImageActivity.class);
            imgAct.putExtra("image", targetUri);
            MainActivity.this.startActivity(imgAct);





        }

    }









}