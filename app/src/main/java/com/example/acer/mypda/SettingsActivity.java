package com.example.acer.mypda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class SettingsActivity extends MenuActivity {

    Button clearAllButton;
    Button clearSpecificButton;
    ListView clearListView;
    ArrayAdapter listAdapter;
    ArrayList<String> tablelist;

    ToDoDBHelper toDoDBHelper;
    TimeLogDBHelper timeLogDBHelper;
    ExerciseDBHelper exerciseDBHelper;
    ExpensesDBHelper expensesDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toDoDBHelper = new ToDoDBHelper(this);
        timeLogDBHelper = new TimeLogDBHelper(this);
        exerciseDBHelper = new ExerciseDBHelper(this);
        expensesDBHelper = new ExpensesDBHelper(this);

        tablelist = new ArrayList<>();

        tablelist.add(0, "To Do List");
        tablelist.add(1, "Time Log List");
        tablelist.add(2, "Exercise Log List");
        tablelist.add(3, "Expenses List");

        listAdapter = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, tablelist);

        clearAllButton = (Button) findViewById(R.id.clearAllButton);
        clearSpecificButton = (Button) findViewById(R.id.clearSpecificButton);
        clearListView = (ListView) findViewById(R.id.clearListView);

        clearListView.setAdapter(listAdapter);

        clearListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        AlertDialog.Builder todoadb = new AlertDialog.Builder(SettingsActivity.this)
                                .setCancelable(false)
                                .setMessage("Deleting To Do List. Are you sure?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        toDoDBHelper.clearAll();
                                    }
                                });
                        todoadb.show();
                        break;
                    case 1:
                        AlertDialog.Builder timelogadb = new AlertDialog.Builder(SettingsActivity.this)
                                .setCancelable(false)
                                .setMessage("Deleting Time Log List. Are you sure?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        timeLogDBHelper.clearAll();
                                    }
                                });
                        timelogadb.show();
                        break;
                    case 2:
                        AlertDialog.Builder exerciseadb = new AlertDialog.Builder(SettingsActivity.this)
                                .setCancelable(false)
                                .setMessage("Deleting Exercise List. Are you sure?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        exerciseDBHelper.clearAll();
                                    }
                                });
                        exerciseadb.show();
                        break;
                    case 3:
                        AlertDialog.Builder expenseadb = new AlertDialog.Builder(SettingsActivity.this)
                                .setCancelable(false)
                                .setMessage("Deleting Expenses List. Are you sure?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        expensesDBHelper.clearAll();
                                    }
                                });
                        expenseadb.show();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void clearAll(View view){
        AlertDialog.Builder clearalladb = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Deleting all. Are you sure?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toDoDBHelper.clearAll();
                        timeLogDBHelper.clearAll();
                        exerciseDBHelper.clearAll();
                        expensesDBHelper.clearAll();
                    }
                });
        clearalladb.show();
    }

    public void showList(View view){
        clearListView.setVisibility(View.VISIBLE);
    }

}
