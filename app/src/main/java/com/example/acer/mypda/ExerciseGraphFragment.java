package com.example.acer.mypda;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class ExerciseGraphFragment extends Fragment {

    View thisview;
    ExerciseReportGraph graphReport;

    String chartTitle;
    String firstDate, secondDate, thirdDate;
    int firstStat, secondStat, thirdStat;
    int repCount = 0;


    public ExerciseGraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle work = getArguments();
        String exerciseName = work.getString("Exercise");
        int exerciseRepCount = work.getInt("Exercise Count");
        ArrayList<String> dates = work.getStringArrayList("Dates");
        ArrayList<Integer> statistics = work.getIntegerArrayList("Statistics");
        int iterator = work.getInt("Iterator");

        /*
        System.out.println("Statistics in fragment: \n");
        for (int j = 0; j < statistics.size(); j++) {
            System.out.println(statistics.get(j) + "\n");
        }*/

        // Inflate the layout for this fragment
        thisview = inflater.inflate(R.layout.fragment_exercise_graph, container, false);

        try {

            if(exerciseRepCount >0){
                chartTitle = exerciseName;
                repCount = exerciseRepCount;

                if(exerciseRepCount>=1){
                    firstDate = dates.get(iterator);
                    firstStat = statistics.get(iterator);
                }
                else{
                    firstDate = "";
                    firstStat = 0;
                }
                if(exerciseRepCount>=2){
                    secondDate = dates.get(iterator + 1);
                    secondStat = statistics.get(iterator + 1);
                }
                else{
                    secondDate = "";
                    secondStat = 0;
                }
                if(exerciseRepCount>=3){
                    thirdDate = dates.get(iterator + 2);
                    thirdStat = statistics.get(iterator + 2);
                }
                else{
                    thirdDate = "";
                    thirdStat = 0;
                }

                graphReport = new ExerciseReportGraph(this, exerciseName, firstDate, secondDate, thirdDate, firstStat, secondStat, thirdStat, repCount);
            }

            LinearLayout pagelay = (LinearLayout) thisview.findViewById(R.id.pagelayout);
            graphReport.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            pagelay.addView(graphReport);

        } catch (Exception e){
            e.printStackTrace();
        }

        return thisview;
    }

}
