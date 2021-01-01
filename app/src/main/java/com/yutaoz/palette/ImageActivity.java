package com.yutaoz.palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView;
    Bitmap photo = null;
    ConstraintLayout imageLayout;
    TextView colorCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        this.imageView = (ImageView)this.findViewById(R.id.imageView);
        Intent intent = getIntent();
        imageLayout = this.findViewById(R.id.imageLayout);

        Uri targetUri = intent.getParcelableExtra("image");
        colorCode = (TextView)findViewById(R.id.ccode);

        try {
            photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Drawable d = new BitmapDrawable(getResources(), photo);
        imageView.setImageDrawable(d);

        imageView.setOnTouchListener((view, event) -> {

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    // take x and y coords
                    int x = (int)event.getX();
                    int y = (int)event.getY();

                    imageView.setDrawingCacheEnabled(true);
                    Bitmap bmap = imageView.getDrawingCache(); //create a new bitmap that is imageview drawable
                    // check that coordinates are not outside bitmap
                    if (x < 0) {
                        x = 0;
                    }
                    if (x > bmap.getWidth()) {
                        x = bmap.getWidth();
                    }
                    if (y < 0) {
                        y = 0;
                    }
                    if (y > bmap.getHeight() - 1) {
                        y = bmap.getHeight() - 1;
                    }

                    Log.i("COORD", x + " " + y + " size: " + bmap.getHeight() );

                    int pixel = bmap.getPixel(x, y); // find pixel at coordinate
                    String hex = String.format("#%02x%02x%02x", Color.red(pixel), Color.green(pixel), Color.blue(pixel)); // convert to color hex
                    // set both background color and imageview if leftover
                    imageLayout.setBackgroundColor(Color.parseColor(hex));
                    imageView.setBackgroundColor(Color.parseColor(hex));
                    colorCode.setText(hex);


                    break;
            }


            return true;
        });


    }

}


