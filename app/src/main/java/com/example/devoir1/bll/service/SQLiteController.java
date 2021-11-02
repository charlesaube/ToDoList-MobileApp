package com.example.devoir1.bll.service;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteController extends SQLiteOpenHelper {


    public static final String DB_NAME= "appdb";
    public static final int DB_VERSION= 1;
    public static final String TASK_TABLE_NAME= "Task";
    public static final String TASK_ID_COL= "id";
    public static final String TASK_TITLE_COL= "title";
    public static final String TASK_DESCRIPTION_COL= "description";
    public static final String TASK_CATEGORY_COL= "category";
    public static final String TASK_DATE_COL= "date";
    public static final String TASK_PRIORITY_COL= "priority";
    public static final String TASK_ISDONE_COL= "ISDONE";


    public SQLiteController() {

        super (ApplicationController.getAppContext(),DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="CREATE TABLE "+TASK_TABLE_NAME+
                " ("+TASK_ID_COL+"  INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TASK_TITLE_COL+" TEXT NOT NULL,"+
                TASK_DESCRIPTION_COL+" TEXT,"+
                TASK_CATEGORY_COL+" TEXT NOT NULL,"+
                TASK_DATE_COL+" INTEGER,"+
                TASK_PRIORITY_COL+" INTEGER,"+
                TASK_ISDONE_COL+" INTEGER)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TASK_TABLE_NAME);

        onCreate(db);

    }
}
