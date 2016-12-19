package com.example.acer.mypda;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    private File imageFile;
    private ImageView imageView;
    private Uri tempuri;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = (ImageView) findViewById(R.id.imageViewArea);
    }

    public void takePicture(View view){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = new File(getExternalFilesDir(null),"test.jpg");
        tempuri = Uri.fromFile(imageFile);
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
        takePicture.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(takePicture,0);
    }

    public void usePicture(View view){
        Intent goBack = new Intent(CameraActivity.this, ToDoActivity.class);
        goBack.putExtra("photo",imageFile);
        startActivity(goBack);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0){
            switch(resultCode){
                case Activity.RESULT_OK:
                    if(imageFile.exists()){
                        Toast.makeText(this, "The file was saved at " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        System.out.println(imageFile);
                        imageView.setImageURI(tempuri);
                    }
                    else{
                        Toast.makeText(this, "No file was saved.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "No file was saved.", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }


}
