package com.example.acer.mypda;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ToDoImageViewer extends AppCompatActivity {

    ImageView imageViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_image_viewer);

        imageViewer = (ImageView) findViewById(R.id.imageViewer);

        Bitmap imagePath = getIntent().getParcelableExtra("imageFile");

        imageViewer.setImageBitmap(imagePath);
    }
}
