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

public class ExpensesDBHelper extends SQLiteOpenHelper {
    //TASK 1: DEFINE THE DATABASE AND TABLE
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "expenses_List";
    private static final String DATABASE_TABLE = "expense_Items";


    //TASK 2: DEFINE THE COLUMN NAMES FOR THE TABLE
    private static final String KEY_ITEM_ID = "_id";
    private static final String KEY_REFERENCE = "reference";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_DATE = "date";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_IS_DONE = "is_done";

    private int expenseItemsCount;

    public ExpensesDBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_REFERENCE + " TEXT, " + KEY_DESCRIPTION + " TEXT, "
                + KEY_CATEGORY + " TEXT, " + KEY_DATE + " TEXT, "
                + KEY_AMOUNT + " TEXT, "
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

    public int getExpenseItemsCount(){
        return expenseItemsCount;
    }


    //********** DATABASE OPERATIONS:  ADD, EDIT, DELETE
    // Adding new item
    public void addExpenseItem(ExpenseDetails expenseItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_REFERENCE, expenseItem.getReference());
        values.put(KEY_DESCRIPTION, expenseItem.getDescription());
        values.put(KEY_CATEGORY, expenseItem.getCategory());
        values.put(KEY_DATE, expenseItem.getDate());
        values.put(KEY_AMOUNT, expenseItem.getAmount());

        //ADD KEY-VALUE PAIR INFORMATION FOR
        //IS_DONE VALUE: 0- NOT DONE, 1 - IS DONE
        values.put(KEY_IS_DONE, expenseItem.getIs_done());

        // INSERT THE ROW IN THE TABLE
        db.insert(DATABASE_TABLE, null, values);
        expenseItemsCount++;

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    public void deleteExpenseItem(){
        List<ExpenseDetails> expensesList = getAllExpenseItems();

        SQLiteDatabase database = this.getReadableDatabase();

        for(int i=0; i< expensesList.size(); i++){
            ExpenseDetails expenseItem = expensesList.get(i);
            //System.out.println(exerciseLogItem.toString());
            if(expenseItem.getIs_done()==1){
                database.delete(DATABASE_TABLE, KEY_ITEM_ID + " = ?",
                        new String[] {String.valueOf(expenseItem.get_id())});
                //System.out.println("Deleted Exercise Log Item: " + exerciseLogItem.toString());
            }
        }
        database.close();
    }

    public List<ExpenseDetails> getAllExpenseItems() {

        //GET ALL THE TIME LOG ITEMS ON THE LIST
        List<ExpenseDetails> expensesList = new ArrayList<ExpenseDetails>();

        //SELECT ALL QUERY FROM THE TABLE
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // LOOP THROUGH THE ExerciseLog ITEMS
        if (cursor.moveToFirst()) {
            do {
                ExpenseDetails expenseDetails = new ExpenseDetails();
                expenseDetails.set_id(cursor.getInt(0));
                expenseDetails.setReference(cursor.getString(1));
                expenseDetails.setDescription(cursor.getString(2));
                expenseDetails.setCategory(cursor.getString(3));
                expenseDetails.setDate(cursor.getString(4));
                expenseDetails.setAmount(cursor.getString(5));
                expenseDetails.setIs_done(cursor.getInt(6));
                expensesList.add(expenseDetails);
            } while (cursor.moveToNext());
        }

        // RETURN THE LIST OF TIME LOG ITEMS FROM THE TABLE
        return expensesList;
    }

    public void clearAll() {
        //GET ALL THE LIST TIME LOG ITEMS AND CLEAR THEM

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, new String[]{});
        db.close();
    }


    public void updateExpenseItem(ExpenseDetails expenseItem) {
        // updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REFERENCE, expenseItem.getReference());
        values.put(KEY_DESCRIPTION, expenseItem.getDescription());
        values.put(KEY_CATEGORY, expenseItem.getCategory());
        values.put(KEY_DATE, expenseItem.getDate());
        values.put(KEY_AMOUNT, expenseItem.getAmount());
        values.put(KEY_IS_DONE, expenseItem.getIs_done());
        db.update(DATABASE_TABLE, values, KEY_ITEM_ID + " = ?", new String[]{String.valueOf(expenseItem.get_id())});
        db.close();
    }

}
