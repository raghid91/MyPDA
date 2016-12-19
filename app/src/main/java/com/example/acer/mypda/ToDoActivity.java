package com.example.acer.mypda;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class ToDoActivity extends MenuActivity {

    protected ToDoDBHelper toDoHelper;
    private List<ToDoItem> todoList;
    private MyAdapter dataAdapter;

    private byte[] imageFile =null;
    private static final int CAMERA_REQUEST = 1;
    ImageButton imageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        todoList = new ArrayList<>();

        toDoHelper = new ToDoDBHelper(this);
        imageIcon = (ImageButton) findViewById(R.id.toDoImageButton);

    }

    @Override
    protected void onResume() {
        super.onResume();
        todoList = toDoHelper.getAllTasks();
        dataAdapter = new MyAdapter(this, R.layout.todoitem, todoList);
        ListView listTask = (ListView) findViewById(R.id.todoList);
        listTask.setAdapter(dataAdapter);
    }

    public void doAdd(View view) {
        final EditText userInput = new EditText(this);
        System.out.println("Done A");
        userInput.setHint("Enter your Task");
        AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Add Task")
                .setView(userInput)
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Add Picture", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callCamera();
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = userInput.getText().toString();
                        System.out.println("Done B");
                        if (input.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "A TODO task must be entered.", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("Done 1");
                            //BUILD A NEW TASK ITEM AND ADD IT TO THE DATABASE
                            ToDoItem task = new ToDoItem(input, imageFile, 0);
                            System.out.println("Done 2");
                            toDoHelper.addToDoItem(task);
                            System.out.println("Done 3");

                            // ADD THE TASK AND SET A NOTIFICATION OF CHANGES
                            dataAdapter.add(task);
                            System.out.println("Done 4");
                            dataAdapter.notifyDataSetChanged();
                            System.out.println("Done 5");
                        }

                    }
                });
        adb.show();
    }

    public void presentImage(Bitmap imageBitmap){

        try {
            if (imageBitmap != null) {
                Intent intent = new Intent(ToDoActivity.this, ToDoImageViewer.class);
                intent.putExtra("imageFile", imageBitmap);
                startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void doDelete(View view) {
        toDoHelper.deleteToDoItem();
        onResume();
    }

    //******************* ADAPTER ******************************
    private class MyAdapter extends ArrayAdapter<ToDoItem> {
        Context context;
        List<ToDoItem> taskList = new ArrayList<ToDoItem>();

        public MyAdapter(Context c, int rId, List<ToDoItem> objects) {
            super(c, rId, objects);
            taskList = objects;
            context = c;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                CheckBox isDoneChBx = null;
                TextView isDoneTxtBx = null;
                ImageButton isImgBx = null;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.todoitem, parent, false);
                    isDoneChBx = (CheckBox) convertView.findViewById(R.id.toDochkStatus);
                    isDoneTxtBx = (TextView) convertView.findViewById(R.id.toDochkText);
                    isImgBx = (ImageButton) convertView.findViewById(R.id.toDoImageButton);
                    convertView.setTag(isDoneChBx);
                    isDoneChBx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CheckBox cb = (CheckBox) view;
                            ToDoItem changeTask = (ToDoItem) cb.getTag();
                            changeTask.setIs_done(cb.isChecked() == true ? 1 : 0);
                            toDoHelper.updateTask(changeTask);
                        }
                    });
                    isImgBx.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            ImageButton cb = (ImageButton) view;
                            ToDoItem changeTask = (ToDoItem) cb.getTag();
                            Bitmap imgbtmp = changeTask.getImageBitmap();
                            presentImage(imgbtmp);
                        }
                    });
                } else {
                    isDoneChBx = (CheckBox) convertView.getTag();
                }
                ToDoItem current = taskList.get(position);
                isDoneTxtBx.setText(current.getDescription());
                isImgBx.setTag(current.getImageByte());
/*
                byte[] outImage = current.getImageFile();
                ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                //isImgBx.setTag(theImage);
*/
                isDoneChBx.setChecked(current.getIs_done() == 1 ? true : false);
                isDoneChBx.setTag(current);
                isImgBx.setTag(current);

            }catch(Exception e){
                e.printStackTrace();
            }
            return convertView;
        }
    }

    private void callCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case CAMERA_REQUEST:

                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageFile = stream.toByteArray();

                    /*Intent i = new Intent(ToDoActivity.this,ToDoActivity.class);
                    startActivity(i);
                    finish();*/
                }
                else imageFile=null;
                break;
            default: imageFile=null;
                break;
        }
    }

}


