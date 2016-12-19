package com.example.acer.mypda;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.SoundPool;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class ExpensesActivity extends MenuActivity {

    private ExpensesDBHelper expensesHelper;
    private List<ExpenseDetails> expensesList;
    private MyAdapter expensesAdapter;
    SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        expensesHelper = new ExpensesDBHelper(this);

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .build();
        soundPool.load(this,R.raw.cha_ching,1);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error" + Thread.currentThread().getStackTrace()[2], paramThrowable.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        expensesList = expensesHelper.getAllExpenseItems();
        expensesAdapter = new MyAdapter(this, R.layout.listitem, expensesList);
        ListView listTask = (ListView) findViewById(R.id.expenses_list);
        listTask.setAdapter(expensesAdapter);
    }

    public void addExpense(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Expenses Sheet");
        LayoutInflater inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_expenses, null);
        builder.setView(view);

        final EditText dateInput = (EditText) view.findViewById(R.id.dateInput);
        final EditText referenceInput = (EditText) view.findViewById(R.id.referenceInput);
        final EditText descriptionInput = (EditText) view.findViewById(R.id.descriptionInput);
        final EditText categoryInput = (EditText) view.findViewById(R.id.categoryInput);
        final EditText amountInput = (EditText) view.findViewById(R.id.amountInput);

        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String date = dateInput.getText().toString();
                String reference = referenceInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String category = categoryInput.getText().toString();
                String amount = amountInput.getText().toString();

                if (date.isEmpty() || reference.isEmpty() || description.isEmpty() || category.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Input values into all fields.", Toast.LENGTH_SHORT).show();
                } else {

                    //BUILD A NEW TASK ITEM AND ADD IT TO THE DATABASE
                    ExpenseDetails expenseItem = new ExpenseDetails(date, reference, description, category, amount, 0);
                    expensesHelper.addExpenseItem(expenseItem);

                    // ADD THE TASK AND SET A NOTIFICATION OF CHANGES
                    expensesAdapter.add(expenseItem);
                    expensesAdapter.notifyDataSetChanged();

                    soundPool.play(1,1,1,1,0,1.0f);
                }
            }
        });
        builder.show();
    }

    public void deleteExpense(View view){
        expensesHelper.deleteExpenseItem();
        onResume();
    }

    public void reportExpense(View view){
        final EditText reportInput = new EditText(this);

        AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setTitle("Enter Category")
                .setView(reportInput)
                .setCancelable(false)
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            String category = reportInput.getText().toString();
                            String amount;
                            ArrayList<String> amounts = new ArrayList<String>();
                            int categoryItemCount = 0;

                            int listCount = expensesHelper.getExpenseItemsCount();

                            if (category.isEmpty()) {
                                if (listCount == 0) amounts.add("0");
                                else for (int i = 0; i < listCount; i++) {
                                    ExpenseDetails expenseItem = expensesList.get(i);
                                    amount = expenseItem.getAmount();
                                    if(amount == null) amounts.add("0");
                                    else amounts.add(amount);
                                    categoryItemCount++;
                                }
                            } else for (int i = 0; i < listCount; i++) {
                                ExpenseDetails expenseItem = expensesList.get(i);
                                if (category.contentEquals(expenseItem.getCategory())) {
                                    amount = expenseItem.getAmount();
                                    if(amount == null) amounts.add("0");
                                    else amounts.add(amount);
                                    categoryItemCount++;
                                }
                            }

                            Intent reportIntent = new Intent(getBaseContext(), ExpenseReportActivity.class);

                            reportIntent.putExtra("CategoryCount", categoryItemCount);
                            reportIntent.putExtra("Amounts", amounts);
                            reportIntent.putExtra("Category", category);

                            startActivity(reportIntent);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        adb.show();
    }

    //******************* ADAPTER ******************************
    private class MyAdapter extends ArrayAdapter<ExpenseDetails> {
        Context context;
        List<ExpenseDetails> taskList = new ArrayList<ExpenseDetails>();

        public MyAdapter(Context c, int rId, List<ExpenseDetails> objects) {
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
                            ExpenseDetails changeTask = (ExpenseDetails) cb.getTag();
                            changeTask.setIs_done(cb.isChecked() == true ? 1 : 0);
                            expensesHelper.updateExpenseItem(changeTask);
                        }
                    });
                } else {
                    isDoneChBx = (CheckBox) convertView.getTag();
                }
                ExpenseDetails current = taskList.get(position);
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
