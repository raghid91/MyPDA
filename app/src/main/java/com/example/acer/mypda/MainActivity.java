package com.example.acer.mypda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class MainActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        ImageButton todo = (ImageButton) findViewById(R.id.todoButton);
        todo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent gotoToDo = new Intent(MainActivity.this, ToDoActivity.class);
                startActivity(gotoToDo);
            }
        });

        ImageButton timelog = (ImageButton) findViewById(R.id.timelogButton);
        timelog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent gotoTimeLog = new Intent(MainActivity.this, TimeLogActivity.class);
                startActivity(gotoTimeLog);
            }
        });

        ImageButton expenses = (ImageButton) findViewById(R.id.expensesButton);
        expenses.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent gotoExpenses = new Intent(MainActivity.this, ExpensesActivity.class);
                startActivity(gotoExpenses);
            }
        });

        ImageButton exerciseLog = (ImageButton) findViewById(R.id.exerciselogButton);
        exerciseLog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent gotoExerciseLog = new Intent(MainActivity.this, ExerciseLogActivity.class);
                startActivity(gotoExerciseLog);
            }
        });


        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
            }
        });
    }



}

