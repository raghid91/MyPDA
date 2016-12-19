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
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class TimeLogActivity extends MenuActivity {

    private TimeLogDBHelper timeLogHelper;
    private List<TimeLogDetails> timeLogList;
    private MyAdapter timeLogAdapter;
    SoundPool soundpool;

    LocationManager locationManager;
    LocationListener locationListener;
    String locationProvider = null;
    String gpslocation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        timeLogHelper = new TimeLogDBHelper(this);

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

        soundpool = new SoundPool.Builder()
                .setMaxStreams(1)
                .build();
        soundpool.load(this,R.raw.cha_ching,1);

    }

    @Override
    protected void onResume(){
        super.onResume();
        timeLogList = timeLogHelper.getAllTimeLogItems();
        timeLogAdapter = new MyAdapter(this, R.layout.listitem, timeLogList);
        ListView listTask = (ListView) findViewById(R.id.listView_timelog);
        listTask.setAdapter(timeLogAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }

    public void doAdd(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Time Log Sheet");
        LayoutInflater inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_timelog, null);
        builder.setView(view);

        final EditText tn = (EditText)view.findViewById(R.id.taskName);
        final EditText td = (EditText)view.findViewById(R.id.description);
        final EditText st = (EditText)view.findViewById(R.id.starttime);
        final EditText et = (EditText)view.findViewById(R.id.endtime);

        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = tn.getText().toString();
                String description = td.getText().toString();
                String startTime = st.getText().toString();
                String endTime = et.getText().toString();

                if (name.isEmpty() || description.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Input values into all fields.", Toast.LENGTH_SHORT).show();
                } else {

                    //BUILD A NEW TASK ITEM AND ADD IT TO THE DATABASE
                    TimeLogDetails timeLogItem = new TimeLogDetails(name, description, startTime, endTime, gpslocation, 0);
                    timeLogHelper.addTimeLogItem(timeLogItem);

                    // ADD THE TASK AND SET A NOTIFICATION OF CHANGES
                    //timeLogAdapter.add(timeLogItem);
                    //timeLogAdapter.notifyDataSetChanged();
                    onResume();

                    soundpool.play(1,1,1,1,0,1.0f);
                }
            }
        });
        builder.show();

    }

    public void doDelete(View view){
        timeLogHelper.deleteTimeLogItem();
        onResume();
    }

    public void goReport(View view){

        final EditText reportInput = new EditText(this);

        AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setTitle("Enter the Task Name")
                .setView(reportInput)
                .setCancelable(false)
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String report = reportInput.getText().toString();
                        String taskName = null;
                        ArrayList<String> startTimes = new ArrayList<String>();
                        ArrayList<String> endTimes = new ArrayList<String>();
                        int reportCount =0;

                        int count = timeLogHelper.getTimeLogItemCount();

                        for(int i=0; i<count; i++){
                            TimeLogDetails tl = timeLogList.get(i);
                            if(report.contentEquals(tl.getName())){
                                taskName = tl.getName();
                                startTimes.add(tl.getsTime());
                                endTimes.add(tl.geteTime());
                                reportCount++;

                            }
                            else{
                                dialog.dismiss();
                            }
                        }


                        Intent reportIntent = new Intent(getBaseContext(), TimeLogReportActivity.class);
                        reportIntent.putExtra("task",taskName);
                        reportIntent.putExtra("start",startTimes);
                        reportIntent.putExtra("end",endTimes);
                        reportIntent.putExtra("count",reportCount);

                        startActivity(reportIntent);
                    }
                });
        adb.show();
    }

    //******************* ADAPTER ******************************
    private class MyAdapter extends ArrayAdapter<TimeLogDetails> {
        Context context;
        List<TimeLogDetails> taskList = new ArrayList<TimeLogDetails>();

        public MyAdapter(Context c, int rId, List<TimeLogDetails> objects) {
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
                            TimeLogDetails changeTask = (TimeLogDetails) cb.getTag();
                            changeTask.setIs_done(cb.isChecked() == true ? 1 : 0);
                            timeLogHelper.updateTimeLogItem(changeTask);
                        }
                    });
                } else {
                    isDoneChBx = (CheckBox) convertView.getTag();
                }
                TimeLogDetails current = taskList.get(position);
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
