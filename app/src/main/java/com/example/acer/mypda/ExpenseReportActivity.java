package com.example.acer.mypda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class ExpenseReportActivity extends AppCompatActivity {

    String category;
    ArrayList<String> amounts;
    int categorycount;
    Double sumAmounts =0.0;


    TextView textCategory,textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report);

        textCategory = (TextView) findViewById(R.id.textCategory);
        textResult = (TextView) findViewById(R.id.textResult);

        category = getIntent().getStringExtra("Category");
        amounts = getIntent().getStringArrayListExtra("Amounts");
        categorycount = getIntent().getIntExtra("CategoryCount",0);

        try {

            if(categorycount==0){
                sumAmounts=0.0;
            }
            else {
                for (int i = 0; i < amounts.size(); i++) {
                    sumAmounts = sumAmounts + Double.parseDouble(amounts.get(i));
                }
            }

            if(category.isEmpty()) textCategory.setText("Your total expenses are:");
            else textCategory.setText("Your total expenses on '" + category + "' is:");
            textResult.setText("$ " + sumAmounts);

            /*if (category.isEmpty()) {
                for (int i = 0; i < amounts.size(); i++) {
                    sumAmounts = +Double.parseDouble(amounts.get(i));
                }
            } else if (categorycount == 0) {
                sumAmounts = 0.0;
            } else{

            }*/

        } catch (Exception e){
            e.printStackTrace();
        }



    }
}
