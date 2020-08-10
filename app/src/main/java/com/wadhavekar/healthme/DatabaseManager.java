package com.wadhavekar.healthme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myRecords.db";
    public static final String TABLE1_NAME = "MyWeight_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "WEIGHT";
    public static final String COL3 = "DATE";

    public static final String TABLE2_NAME = "myWorkout_data";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SETS";
    public static final String COL_4= "REPS";
    public static final String COL_5 = "DAY";

    public static final String TABLE3_NAME = "Height";
    public static final String Col1 = "ID";
    public static final String Col2 = "HEIGHT";

    public static final String TABLE4_NAME = "Target";
    public static final String Col_1 = "ID";
    public static final String Col_2 = "TARGET";

    public static final String STEPS_PER_DAY = "StepsPerDay";
    public static final String C1 = "ID";
    public static final String C2 = "STEPS";
    public static final String C3 = "DATE_OF_ENTRY";


    public static final String TABLE6_NAME = "AGE";
    public static final String c1 = "ID";
    public static final String c2 = "AGE";

    public static final String TABLE7_NAME = "STEPTARGET";
    public static final String C_1 = "ID";
    public static final String C_2 = "TARGET_STEPS";







    public DatabaseManager(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE1_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "WEIGHT TEXT, DATE TEXT)";
        String createTable2 = "CREATE TABLE " +TABLE2_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+" TEXT, "+COL_3+" TEXT, "+COL_4+" TEXT, "+ COL_5 +" TEXT)";
        String createTable3 = "CREATE TABLE " + TABLE3_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, HEIGHT TEXT)";
        String createTable4 = "CREATE TABLE " + TABLE4_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, TARGET TEXT)";
        String createTable5 = "CREATE TABLE " + TABLE6_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, AGE INTEGER)";
        String createTable6 = "CREATE TABLE " + STEPS_PER_DAY +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, STEPS TEXT, DATE_OF_ENTRY TEXT)";
        String createTable7 = "CREATE TABLE " + TABLE7_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, TARGET_STEPS TEXT)";

        db.execSQL(createTable2);
        db.execSQL(createTable);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
        db.execSQL(createTable5);
        db.execSQL(createTable6);
        db.execSQL(createTable7);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS myWorkout_data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + STEPS_PER_DAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE6_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE7_NAME);

        onCreate(db);
    }

    public boolean addAge(String age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(c2,age);
        long result = db.insert(TABLE6_NAME,null,contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAge(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+TABLE6_NAME, null);
        return data;
    }


    public boolean addTargetSteps(String ts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_2,ts);
        long result = db.insert(TABLE7_NAME,null,contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getTargetSteps(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+TABLE7_NAME, null);
        return data;
    }

    public boolean addData(String weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,weight);
        contentValues.put(COL3,""+getDate());

        long result = db.insert(TABLE1_NAME,null,contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean addStepsToDB(int steps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C2,""+steps);
        cv.put(C3,""+getDate());

        long result = db.insert(STEPS_PER_DAY,null,cv);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public Cursor getSteps(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ STEPS_PER_DAY, null);
        return data;
    }


    public boolean addHeight(String height){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Col2,height);
        long result = db.insert(TABLE3_NAME,null,cv);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getMyHeight(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ TABLE3_NAME, null);
        return data;
    }

    public Cursor getTarget(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ TABLE4_NAME, null);
        return data;
    }

    public boolean addTarget(String target){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Col_2,target);
        long result = db.insert(TABLE4_NAME,null,cv);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor retrieveData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE1_NAME,null);
        return data;
    }

    public void deleteAllData(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ TABLE1_NAME);
    }

    public boolean addWorkoutData(String name,String sets,String reps,String day){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentVals = new ContentValues();
        contentVals.put(COL_2,name);
        contentVals.put(COL_3,sets);
        contentVals.put(COL_4,reps);
        contentVals.put(COL_5,day);
        long result = 0;
        try {
            result = db.insert("myWorkout_data",null,contentVals);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getStackTrace();
        }

        if (result == -1){
            return false;
        }
        else{
            return true;
        }


    }

    public Cursor getMondayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE2_NAME +" WHERE DAY='Monday' ",null);
        return data;
    }
    public Cursor getTuesdayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE2_NAME +" WHERE DAY='Tuesday' ",null);
        return data;
    }
    public Cursor getWednesdayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE2_NAME +" WHERE DAY='Wednesday' ",null);
        return data;
    }
    public Cursor getThursdayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE2_NAME +" WHERE DAY='Thursday' ",null);
        return data;
    }
    public Cursor getFridayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE2_NAME +" WHERE DAY='Friday' ",null);
        return data;
    }
    public Cursor getSaturdayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE2_NAME +" WHERE DAY='Saturday' ",null);
        return data;
    }
    public Cursor getLastEnteredWeight(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ TABLE1_NAME, null);
        return data;
    }

    public void removeRecord(String name,String day){
        SQLiteDatabase db = this.getWritableDatabase();
        String remove = "DELETE FROM "+ TABLE2_NAME + " WHERE NAME = '"+name+"' AND DAY = '"+day+"' ";
        db.execSQL(remove);

    }






    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(d1);
    }
}
