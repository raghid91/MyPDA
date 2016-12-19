package com.example.acer.mypda;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class ExerciseReportGraph extends View{

    String chartTitle;
    String firstDate, secondDate, thirdDate;
    int firstStat, secondStat, thirdStat;
    int maxStat=0;
    int repCount=0;

    public ExerciseReportGraph(Context context, String exerciseName,
                               String date1, String date2, String date3,
                               int stat1, int stat2, int stat3, int repCount) {
        super(context);
        init(exerciseName, date1, date2, date3, stat1, stat2, stat3, repCount);

    }

    public ExerciseReportGraph(Fragment fragment, String exerciseName,
                               String date1, String date2, String date3,
                               int stat1, int stat2, int stat3, int repCount) {
        super(fragment.getContext());
        init(exerciseName, date1, date2, date3, stat1, stat2, stat3, repCount);

    }

    private void init(String exerciseName,
                 String date1, String date2, String date3,
                 int stat1, int stat2, int stat3, int repCount){

        chartTitle = exerciseName;
        firstDate = date1;
        secondDate = date2;
        thirdDate = date3;

        this.repCount = repCount;

        firstStat = stat1;
        secondStat = stat2;
        thirdStat = stat3;


        if(maxStat<=firstStat) maxStat=firstStat;
        if(maxStat<=secondStat) maxStat=secondStat;
        if(maxStat<=thirdStat) maxStat=thirdStat;


        System.out.println("Graph work");
        System.out.println(firstStat);
        System.out.println(secondStat);
        System.out.println(thirdStat);
        System.out.println("Max: "+ maxStat);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //set the coordinates and variables

        int xAxisStart = canvas.getWidth()*2/16;
        int xAxisEnd = canvas.getWidth()*14/16;
        int yAxisStart = canvas.getHeight()*3/4;
        int yAxisEnd = canvas.getHeight()*1/4;

        int delta = canvas.getWidth()*1/32;

        int firstLim = xAxisStart;
        int firstBarStart = firstLim + 1*delta;
        int firstBarEnd = firstBarStart + 6*delta;
        int secondLim = firstBarEnd + delta;
        int secondBarStart = secondLim + delta;
        int secondBarEnd = secondBarStart + 6*delta;
        int thirdLim = secondBarEnd + delta;
        int thirdBarStart = thirdLim + delta;
        int thirdBarEnd = thirdBarStart + 6*delta;

        int thirdBarTop = (int) ((yAxisStart-yAxisEnd)*graphRatio(thirdStat,maxStat) + yAxisEnd);
        int secondBarTop = (int) ((yAxisStart-yAxisEnd)*graphRatio(secondStat,maxStat) + yAxisEnd);
        int firstBarTop = (int) ((yAxisStart-yAxisEnd)*graphRatio(firstStat,maxStat) + yAxisEnd);

        //draw the text

        Paint text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(40);
        canvas.drawText(firstDate,firstBarStart+delta,yAxisStart+3*delta,text);
        canvas.drawText(secondDate,secondBarStart+delta,yAxisStart+3*delta,text);
        canvas.drawText(thirdDate,thirdBarStart+delta,yAxisStart+3*delta,text);
        canvas.drawText(String.valueOf(thirdStat),xAxisStart-3*delta,thirdBarTop,text);
        canvas.drawText(String.valueOf(secondStat),xAxisStart-3*delta,secondBarTop,text);
        canvas.drawText(String.valueOf(firstStat),xAxisStart-3*delta,firstBarTop,text);
        text.setTextSize(30);
        canvas.drawText("minutes",xAxisStart-3*delta,yAxisEnd,text);
        text.setTextSize(80);
        text.setTextAlign(Paint.Align.CENTER);
        text.setFakeBoldText(true);
        canvas.drawText(chartTitle,secondBarStart+3*delta,yAxisEnd*3/4,text);

        //draw the lines

        Paint line = new Paint();
        line.setColor(Color.BLACK);
        line.setStrokeWidth(3);
        canvas.drawLine(xAxisStart,yAxisStart,xAxisEnd,yAxisStart,line);
        canvas.drawLine(xAxisStart,yAxisStart,xAxisStart,yAxisEnd,line);
        canvas.drawLine(secondLim,yAxisStart+delta/4,secondLim,yAxisStart-delta/4,line);
        canvas.drawLine(thirdLim,yAxisStart+delta/4,thirdLim,yAxisStart-delta/4,line);
        canvas.drawLine(xAxisStart-delta/4,firstBarTop,xAxisStart+delta/4,firstBarTop,line);
        canvas.drawLine(xAxisStart-delta/4,thirdBarTop,xAxisStart+delta/4,thirdBarTop,line);
        canvas.drawLine(xAxisStart-delta/4,secondBarTop,xAxisStart+delta/4,secondBarTop,line);

        //draw the bars

        //first bar

        if (repCount >= 0) {

            Paint green = new Paint();
            green.setColor(Color.GREEN);
            green.setStyle(Paint.Style.FILL);

            Rect firstRect = new Rect();
            firstRect.set(firstBarStart, firstBarTop, firstBarEnd, yAxisStart);
            canvas.drawRect(firstRect, green);
        }

        //second bar

        if (repCount >= 1) {

            Paint cyan = new Paint();
            cyan.setColor(Color.CYAN);
            cyan.setStyle(Paint.Style.FILL);

            Rect secondRect = new Rect();
            secondRect.set(secondBarStart,secondBarTop,secondBarEnd,yAxisStart);
            canvas.drawRect(secondRect,cyan);

        }

        //third bar

        if (repCount >= 2) {

            Paint magenta = new Paint();
            magenta.setColor(Color.MAGENTA);
            magenta.setStyle(Paint.Style.FILL);

            Rect thirdRect = new Rect();
            thirdRect.set(thirdBarStart,thirdBarTop,thirdBarEnd,yAxisStart);
            canvas.drawRect(thirdRect,magenta);

        }



    }

    private Double graphRatio(int stat, int maxStat){
        Double result = 1.0 - (3.0*stat)/(4.0*maxStat);
        System.out.println(result);
        return result;
    }
}
