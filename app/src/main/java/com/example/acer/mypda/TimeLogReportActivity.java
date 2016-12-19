package com.example.acer.mypda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class TimeLogReportActivity extends AppCompatActivity {

    String name;
    ArrayList<String> start, end;
    int count=0;
    TextView text, showResult;
    int totalHours=0,totalMinutes=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log_report);

        text = (TextView) findViewById(R.id.name);
        showResult = (TextView) findViewById(R.id.display);

        name = getIntent().getStringExtra("task");
        start = getIntent().getStringArrayListExtra("start");
        end = getIntent().getStringArrayListExtra("end");
        count = getIntent().getIntExtra("count",0);

        System.out.println("Count is: " + count);
        System.out.println("Task is: " + name);
        System.out.println("Start times are: " + start);
        System.out.println("End times are: " + end);


        text.setText("The amount of time spent on the task '" + name + "' is: ");

        try{
            for(int i=0; i<count; i++){
                calculateTime(start.get(i),end.get(i));
            }

            String timeDiff = totalHours + " hour(s) " + totalMinutes + " minutes(s)";
            showResult.setText(timeDiff);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void calculateTime(String start, String end) throws Exception{
        DateFormat format = new SimpleDateFormat("hh:mm");
        Date time1 = format.parse(start);
        Date time2 = format.parse(end);

        long millis = time2.getTime() - time1.getTime();

        int hours = (int) millis/(1000*60*60);
        int minutes = (int) (millis/(1000*60))%60;

        totalHours = totalHours + hours;
        totalMinutes = totalMinutes + minutes;

    }

}

