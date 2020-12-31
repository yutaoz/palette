package com.yutaoz.palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        this.imageView = (ImageView)this.findViewById(R.id.imageView);
        Intent intent = getIntent();

        Uri targetUri = intent.getParcelableExtra("image");
        Bitmap photo = null;
        try {
            photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Drawable d = new BitmapDrawable(getResources(), photo);
        imageView.setImageBitmap(photo);

        
    }

}