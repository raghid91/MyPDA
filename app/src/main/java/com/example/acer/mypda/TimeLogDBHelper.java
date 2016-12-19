package com.example.acer.mypda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 11/27/2016.
 */

public class TimeLogDBHelper extends SQLiteOpenHelper {
    //TASK 1: DEFINE THE DATABASE AND TABLE
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "timeLog_List";
    private static final String DATABASE_TABLE = "timeLog_Items";


    //TASK 2: DEFINE THE COLUMN NAMES FOR THE TABLE
    private static final String KEY_ITEM_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_IS_DONE = "is_done";

    private int timeLogItemCount;

    public TimeLogDBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, " + KEY_DESCRIPTION + " TEXT, "
                + KEY_START_TIME + " TEXT, " + KEY_END_TIME + " TEXT, "
                + KEY_LOCATION + " TEXT, "
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

    public int getTimeLogItemCount(){
        return timeLogItemCount;
    }


    //********** DATABASE OPERATIONS:  ADD, EDIT, DELETE
    // Adding new item
    public void addTimeLogItem(TimeLogDetails timeLogItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, timeLogItem.getName());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE ITEM DESCRIPTION
        values.put(KEY_DESCRIPTION, timeLogItem.getDescription()); // Item name

        values.put(KEY_START_TIME, timeLogItem.getsTime());
        values.put(KEY_END_TIME, timeLogItem.geteTime());
        values.put(KEY_LOCATION, timeLogItem.getLocation());

        //ADD KEY-VALUE PAIR INFORMATION FOR
        //IS_DONE VALUE: 0- NOT DONE, 1 - IS DONE
        values.put(KEY_IS_DONE, timeLogItem.getIs_done());

        // INSERT THE ROW IN THE TABLE
        db.insert(DATABASE_TABLE, null, values);
        timeLogItemCount++;

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    public void deleteTimeLogItem (){
        List<TimeLogDetails> timeLogList = getAllTimeLogItems();

        SQLiteDatabase database = this.getReadableDatabase();

        for(int i=0; i< timeLogList.size(); i++){
            TimeLogDetails timeLogItem = timeLogList.get(i);
            //System.out.println(timeLogItem.toString());
            if(timeLogItem.getIs_done()==1){
                database.delete(DATABASE_TABLE, KEY_ITEM_ID + " = ?",
                        new String[] {String.valueOf(timeLogItem.get_id())});
                System.out.println("Deleted Time Log Item: " + timeLogItem.toString());
            }
        }
        database.close();
    }

    public List<TimeLogDetails> getAllTimeLogItems() {

        //GET ALL THE TIME LOG ITEMS ON THE LIST
        List<TimeLogDetails> timeLogList = new ArrayList<TimeLogDetails>();

        //SELECT ALL QUERY FROM THE TABLE
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // LOOP THROUGH THE TimeLog ITEMS
        if (cursor.moveToFirst()) {
            do {
                TimeLogDetails timeLogDetails = new TimeLogDetails();
                timeLogDetails.set_id(cursor.getInt(0));
                timeLogDetails.setName(cursor.getString(1));
                timeLogDetails.setDescription(cursor.getString(2));
                timeLogDetails.setsTime(cursor.getString(3));
                timeLogDetails.seteTime(cursor.getString(4));
                timeLogDetails.setLocation(cursor.getString(5));
                timeLogDetails.setIs_done(cursor.getInt(6));
                timeLogList.add(timeLogDetails);
            } while (cursor.moveToNext());
        }

        // RETURN THE LIST OF TIME LOG ITEMS FROM THE TABLE
        return timeLogList;
    }

    public void clearAll() {
        //GET ALL THE LIST TIME LOG ITEMS AND CLEAR THEM

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, new String[]{});
        db.close();
    }


    public void updateTimeLogItem(TimeLogDetails timeLogItem) {
        // updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, timeLogItem.getName());
        values.put(KEY_DESCRIPTION, timeLogItem.getDescription());
        values.put(KEY_START_TIME,timeLogItem.getsTime());
        values.put(KEY_END_TIME, timeLogItem.geteTime());
        values.put(KEY_LOCATION, timeLogItem.getLocation());
        values.put(KEY_IS_DONE, timeLogItem.getIs_done());
        db.update(DATABASE_TABLE, values, KEY_ITEM_ID + " = ?", new String[]{String.valueOf(timeLogItem.get_id())});
        db.close();
    }


}