package com.example.acer.mypda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 11/30/2016.
 */

public class ExerciseDBHelper extends SQLiteOpenHelper {
    //TASK 1: DEFINE THE DATABASE AND TABLE
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "exerciseLog_List";
    private static final String DATABASE_TABLE = "exerciseLog_Items";


    //TASK 2: DEFINE THE COLUMN NAMES FOR THE TABLE
    private static final String KEY_ITEM_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATISTIC = "statistic";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_IS_DONE = "is_done";

    private int exerciseLogItemCount;

    public ExerciseDBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, " + KEY_DATE + " TEXT, "
                + KEY_STATISTIC + " INTEGER, " + KEY_LOCATION + " TEXT, "
                + KEY_IS_DONE + " INTEGER" + ")";

        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // DROP OLDER TABLE IF EXISTS
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        // CREATE TABLE AGAIN
        onCreate(database);
    }

    public int getExerciseLogItemCount(){
        return exerciseLogItemCount;
    }


    //********** DATABASE OPERATIONS:  ADD, EDIT, DELETE
    // Adding new item
    public void addExerciseLogItem(ExerciseDetails exerciseLogItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, exerciseLogItem.getName());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE ITEM DESCRIPTION
        values.put(KEY_DATE, exerciseLogItem.getDate()); // Item name

        values.put(KEY_STATISTIC, exerciseLogItem.getStatistic());
        values.put(KEY_LOCATION, exerciseLogItem.getLocation());

        //ADD KEY-VALUE PAIR INFORMATION FOR
        //IS_DONE VALUE: 0- NOT DONE, 1 - IS DONE
        values.put(KEY_IS_DONE, exerciseLogItem.getIs_done());

        // INSERT THE ROW IN THE TABLE
        db.insert(DATABASE_TABLE, null, values);
        exerciseLogItemCount++;

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    public void deleteExerciseLogItem(){
        List<ExerciseDetails> exerciseLogList = getAllExerciseLogItems();

        SQLiteDatabase database = this.getReadableDatabase();

        for(int i=0; i< exerciseLogList.size(); i++){
            ExerciseDetails exerciseLogItem = exerciseLogList.get(i);
            //System.out.println(exerciseLogItem.toString());
            if(exerciseLogItem.getIs_done()==1){
                database.delete(DATABASE_TABLE, KEY_ITEM_ID + " = ?",
                        new String[] {String.valueOf(exerciseLogItem.get_id())});
                //System.out.println("Deleted Exercise Log Item: " + exerciseLogItem.toString());
            }
        }
        database.close();
    }

    public List<ExerciseDetails> getAllExerciseLogItems() {

        //GET ALL THE TIME LOG ITEMS ON THE LIST
        List<ExerciseDetails> exerciseLogList = new ArrayList<ExerciseDetails>();

        //SELECT ALL QUERY FROM THE TABLE
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // LOOP THROUGH THE ExerciseLog ITEMS
        if (cursor.moveToFirst()) {
            do {
                ExerciseDetails exerciseLogDetails = new ExerciseDetails();
                exerciseLogDetails.set_id(cursor.getInt(0));
                exerciseLogDetails.setName(cursor.getString(1));
                exerciseLogDetails.setDate(cursor.getString(2));
                exerciseLogDetails.setStatistic(cursor.getInt(3));
                exerciseLogDetails.setLocation(cursor.getString(4));
                exerciseLogDetails.setIs_done(cursor.getInt(5));
                exerciseLogList.add(exerciseLogDetails);
            } while (cursor.moveToNext());
        }

        // RETURN THE LIST OF TIME LOG ITEMS FROM THE TABLE
        return exerciseLogList;
    }

    public void clearAll() {
        //GET ALL THE LIST TIME LOG ITEMS AND CLEAR THEM

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, new String[]{});
        db.close();
    }


    public void updateExerciseLogItem(ExerciseDetails exerciseLogItem) {
        // updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, exerciseLogItem.getName());
        values.put(KEY_DATE, exerciseLogItem.getDate());
        values.put(KEY_STATISTIC,exerciseLogItem.getStatistic());
        values.put(KEY_LOCATION,exerciseLogItem.getLocation());
        values.put(KEY_IS_DONE, exerciseLogItem.getIs_done());
        db.update(DATABASE_TABLE, values, KEY_ITEM_ID + " = ?", new String[]{String.valueOf(exerciseLogItem.get_id())});
        db.close();
    }

}
