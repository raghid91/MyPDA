package com.example.acer.mypda;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.SoundPool;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class ExerciseLogActivity extends MenuActivity {

    private ExerciseDBHelper exerciseLogHelper;
    private List<ExerciseDetails> exerciseLogList;
    private MyAdapter exerciseLogAdapter;
    SoundPool soundPool;

    LocationManager locationManager;
    LocationListener locationListener;
    String locationProvider = null;
    String gpslocation = null;

    ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log);

        exerciseLogHelper = new ExerciseDBHelper(this);

        addButton = (ImageButton) findViewById(R.id.addButton);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProvider = locationManager.GPS_PROVIDER;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                gpslocation = "Latitude: " + location.getLatitude() + " \nLongitude: " + location.getLongitude();
                Toast.makeText(getBaseContext(), gpslocation, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getBaseContext(), "GPS turned on ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getBaseContext(), "GPS turned off ", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET},10);
                return;
            }
        }
        locationManager.requestLocationUpdates(locationProvider,5000,0,locationListener);

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .build();
        soundPool.load(this, R.raw.cha_ching, 1);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error" + Thread.currentThread().getStackTrace()[2], paramThrowable.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        exerciseLogList = exerciseLogHelper.getAllExerciseLogItems();
        exerciseLogAdapter = new MyAdapter(this, R.layout.listitem, exerciseLogList);
        ListView listTask = (ListView) findViewById(R.id.exerciseListView);
        listTask.setAdapter(exerciseLogAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }

    public void addExercise(View view) {

        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exercise Sheet");
            LayoutInflater inflater = getLayoutInflater();
            view = inflater.inflate(R.layout.dialog_exercises, null);
            builder.setView(view);

            final EditText nameInput = (EditText) view.findViewById(R.id.exerciseNameInput);
            final EditText dateInput = (EditText) view.findViewById(R.id.exerciseDateInput);
            final EditText statisticInput = (EditText) view.findViewById(R.id.exerciseStatisticInput);

            builder.setNegativeButton("Cancel", null);
            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String exerciseName = nameInput.getText().toString();
                    String exerciseDate = dateInput.getText().toString();
                    String exerciseStatistic = statisticInput.getText().toString();
                    Integer exStat = Integer.parseInt(exerciseStatistic);
                    //Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                    //locationListener.onLocationChanged(lastKnownLocation);

                    if (exerciseName.isEmpty() || exerciseDate.isEmpty() || exerciseStatistic.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Input values into all fields.", Toast.LENGTH_SHORT).show();
                    } else {

                        //BUILD A NEW TASK ITEM AND ADD IT TO THE DATABASE
                        ExerciseDetails exerciseLogItem = new ExerciseDetails(exerciseName, exerciseDate, exStat, gpslocation, 0);
                        exerciseLogHelper.addExerciseLogItem(exerciseLogItem);

                        // ADD THE TASK AND SET A NOTIFICATION OF CHANGES
                        //exerciseLogAdapter.add(exerciseLogItem);
                        //exerciseLogAdapter.notifyDataSetChanged();
                        onResume();

                        soundPool.play(1,1,1,1,0,1.0f);
                    }
                }
            });
            builder.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteExercise(View view){
        exerciseLogHelper.deleteExerciseLogItem();
        onResume();
    }

    public void reportExercise(View view){

        final EditText reportInput = new EditText(this);

        AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setTitle("Enter Exercise Name")
                .setView(reportInput)
                .setCancelable(false)
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{

                            String report = reportInput.getText().toString();
                            String date;
                            ArrayList<Integer> statistics = new ArrayList<>();
                            ArrayList<String> dates = new ArrayList<>();
                            int exerciseRepCount = 0;

                            int listCount = exerciseLogHelper.getExerciseLogItemCount();

                            if(report.isEmpty()) dialog.dismiss();
                            else for(int i=0; i<listCount; i++){
                                ExerciseDetails exerciseItem = exerciseLogList.get(i);
                                if(report.equalsIgnoreCase(exerciseItem.getName())){
                                    int staticnum = exerciseItem.getStatistic();
                                    System.out.println(staticnum);
                                    statistics.add(staticnum);
                                    date = exerciseItem.getDate();
                                    dates.add(date);
                                    exerciseRepCount++;
                                }
                                else dialog.dismiss();
                            }

                            Intent reportIntent = new Intent(getBaseContext(), ExerciseReportActivity.class);
                            reportIntent.putExtra("Exercise",report);
                            reportIntent.putExtra("Exercise Count", exerciseRepCount);
                            reportIntent.putExtra("Dates", dates);
                            reportIntent.putExtra("Statistics", statistics);
                            startActivity(reportIntent);

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        adb.show();

    }

    //******************* ADAPTER ******************************
    private class MyAdapter extends ArrayAdapter<ExerciseDetails> {
        Context context;
        List<ExerciseDetails> taskList = new ArrayList<ExerciseDetails>();

        public MyAdapter(Context c, int rId, List<ExerciseDetails> objects) {
            super(c, rId, objects);
            taskList = objects;
            context = c;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                CheckBox isDoneChBx = null;
                TextView isDoneTxtBx = null;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.listitem, parent, false);
                    isDoneChBx = (CheckBox) convertView.findViewById(R.id.chkStatus);
                    isDoneTxtBx = (TextView) convertView.findViewById(R.id.chkText);
                    convertView.setTag(isDoneChBx);
                    isDoneChBx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CheckBox cb = (CheckBox) view;
                            ExerciseDetails changeTask = (ExerciseDetails) cb.getTag();
                            changeTask.setIs_done(cb.isChecked() == true ? 1 : 0);
                            exerciseLogHelper.updateExerciseLogItem(changeTask);
                        }
                    });
                } else {
                    isDoneChBx = (CheckBox) convertView.getTag();
                }
                ExerciseDetails current = taskList.get(position);
                isDoneTxtBx.setText(current.toString());
                isDoneChBx.setChecked(current.getIs_done() == 1 ? true : false);
                isDoneChBx.setTag(current);
            }catch(Exception e){
                e.printStackTrace();
            }
            return convertView;
        }
    }


}

