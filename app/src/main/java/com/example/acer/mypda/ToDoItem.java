package com.example.acer.mypda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Acer on 11/27/2016.
 */

public class ToDoItem {

    //MEMBER ATTRIBUTES
    private int _id;
    private String description;
    private Bitmap imageFile = null;
    private int is_done;

    public ToDoItem() {
    }

    public ToDoItem(String desc, byte[] imageFile, int done) {
        System.out.println("Done a");
        setDescription(desc);
        System.out.println("Done b");
        setImageByte(imageFile);
        System.out.println("Done c");
        setIs_done(done);
    }

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
    }

    public String getDescription () {
        return description;
    }
    public void setDescription (String desc) {
        description = desc;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int done) {
        is_done = done;
    }

    public String toString(){
        return "ID: " + getId() + ", Description: " + getDescription() + ", Done Status: " + (getIs_done()==1? "Done" : "Not Done");
    }


    public Bitmap getImageBitmap() {
        return imageFile;
    }

    public byte[] getImageByte(){
        byte[] byteArray =null;
        Bitmap imageBitmap = imageFile;
        if(imageBitmap==null) byteArray = null;
        else{
             ByteArrayOutputStream stream = new ByteArrayOutputStream();
             imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
             byteArray = stream.toByteArray();
        }
        return byteArray;
    }

    public void setImageBitmap(Bitmap imageFile) {
        this.imageFile = imageFile;
    }

    public void setImageByte(byte[] imageByte){
        if(imageByte==null){
            System.out.println("Done c2");
            setImageBitmap(null);
        }
        else {
            System.out.println("Done d");
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByte);
            System.out.println("Done e");
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            System.out.println("Done f");
            setImageBitmap(theImage);
        }
    }
}
