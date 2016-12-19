package com.example.acer.mypda;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class ExerciseReportActivity extends AppCompatActivity {

    String exerciseName;
    int exerciseRepCount=0;
    ArrayList<String> dates;
    ArrayList<Integer> statistics;
    int iterator=0;

    TextView textExerciseName;

    Bundle passToFragment;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_report);

        textExerciseName = (TextView) findViewById(R.id.exercisereporttitle);

        exerciseName = getIntent().getStringExtra("Exercise");
        exerciseRepCount = getIntent().getIntExtra("Exercise Count",0);
        dates = getIntent().getStringArrayListExtra("Dates");
        statistics = getIntent().getIntegerArrayListExtra("Statistics");


        fragment = new Fragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        passToFragment = new Bundle();
        passToFragment.putString("Exercise",exerciseName);
        passToFragment.putInt("Exercise Count", exerciseRepCount);
        passToFragment.putStringArrayList("Dates",dates);
        passToFragment.putIntegerArrayList("Statistics",statistics);
        passToFragment.putInt("Iterator",iterator);

        ExerciseGraphFragment fragmentcode = new ExerciseGraphFragment();
        fragmentcode.setArguments(passToFragment);
        fragmentTransaction.add(R.id.imageResult,fragmentcode);
        fragmentTransaction.commit();

        /*System.out.println("Statistics: \n");
        for(int j=0;j<statistics.size();j++){
            System.out.println(statistics.get(j)+"\n");
        }*/

    }

    public void goNext(View view){

        if(exerciseRepCount>3){
            if(iterator<exerciseRepCount-2){
                try{
                    iterator++;

                    passToFragment.putString("Exercise",exerciseName);
                    passToFragment.putInt("Exercise Count", exerciseRepCount);
                    passToFragment.putStringArrayList("Dates",dates);
                    passToFragment.putIntegerArrayList("Statistics",statistics);
                    passToFragment.putInt("Iterator",iterator);

                    FragmentManager nextFragmentManager = getFragmentManager();
                    FragmentTransaction nextFragmentTransaction = nextFragmentManager.beginTransaction();

                    ExerciseGraphFragment fragmentcode = new ExerciseGraphFragment();
                    fragmentcode.setArguments(passToFragment);
                    nextFragmentTransaction.replace(R.id.imageResult,fragmentcode);
                    nextFragmentTransaction.commit();

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void goPrevious(View view){
        if(exerciseRepCount>3){
            if(iterator>0){
                iterator--;

                passToFragment.putString("Exercise",exerciseName);
                passToFragment.putInt("Exercise Count", exerciseRepCount);
                passToFragment.putStringArrayList("Dates",dates);
                passToFragment.putIntegerArrayList("Statistics",statistics);
                passToFragment.putInt("Iterator",iterator);

                FragmentManager previousFragmentManager = getFragmentManager();
                FragmentTransaction previousFragmentTransaction = previousFragmentManager.beginTransaction();

                ExerciseGraphFragment fragmentcode = new ExerciseGraphFragment();
                fragmentcode.setArguments(passToFragment);
                previousFragmentTransaction.replace(R.id.imageResult,fragmentcode);
                previousFragmentTransaction.commit();
            }
        }

    }
}
