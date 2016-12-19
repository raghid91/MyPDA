package com.example.acer.mypda;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class HelpActivity extends AppCompatActivity {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        fragment = new Fragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        String intentString = getIntent().getStringExtra("Help");

        if(intentString.equals(MainActivity.class.toString())){
            MainActivityHelpFragment fragmentcode = new MainActivityHelpFragment();
            fragmentTransaction.add(R.id.activity_help,fragmentcode);
            fragmentTransaction.commit();
        }
        else if(intentString.equals(ToDoActivity.class.toString())){
            ToDoActivityHelpFragment fragmentcode = new ToDoActivityHelpFragment();
            fragmentTransaction.add(R.id.activity_help,fragmentcode);
            fragmentTransaction.commit();
        }
        else if(intentString.equals(TimeLogActivity.class.toString())){
            TimeLogActivityHelpFragment fragmentcode = new TimeLogActivityHelpFragment();
            fragmentTransaction.add(R.id.activity_help,fragmentcode);
            fragmentTransaction.commit();
        }
        else if(intentString.equals(ExpensesActivity.class.toString())){
            ExpensesActivityHelpFragment fragmentcode = new ExpensesActivityHelpFragment();
            fragmentTransaction.add(R.id.activity_help,fragmentcode);
            fragmentTransaction.commit();
        }
        else if(intentString.equals(ExerciseLogActivity.class.toString())){
            ExerciseLogActivityHelpFragment fragmentcode = new ExerciseLogActivityHelpFragment();
            fragmentTransaction.add(R.id.activity_help,fragmentcode);
            fragmentTransaction.commit();
        }
        else if(intentString.equals(SettingsActivity.class.toString())){
            SettingsActivityHelpFragment fragmentcode = new SettingsActivityHelpFragment();
            fragmentTransaction.add(R.id.activity_help,fragmentcode);
            fragmentTransaction.commit();
        }


    }

}
