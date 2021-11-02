package com.example.devoir1.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.devoir1.R;
import com.example.devoir1.bll.model.Task;
import com.example.devoir1.bll.service.ApplicationController;
import com.example.devoir1.bll.service.SQLiteController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskSQLiteDAO implements ITaskDAO {

    SQLiteController sqLiteController;

    public TaskSQLiteDAO() {
        this.sqLiteController = new SQLiteController();
    }

    @Override
    public List<Task> findAllTask() {

        String query = "SELECT * FROM "+ ApplicationController.getAppContext().getString(R.string.TASK_TABLE_NAME);
        SQLiteDatabase db = sqLiteController.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Boolean taskDone;

        List<Task> foundTasks = new ArrayList<>();
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){

            if(Integer.parseInt(cursor.getString(6)) == 0){
                taskDone = false;
            }
            else{
                taskDone = true;
            }
            Task newTask = new Task(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    new Date(cursor.getLong(4)),
                    Integer.parseInt(cursor.getString(5)),
                    taskDone);

            foundTasks.add(newTask);
            cursor.moveToNext();
        }

        db.close();
        return foundTasks;

    }

    @Override
    public void addNewTask(int id, String title, String description, String category, Date date, int priority) {
        if(!(title == null || description == null || category == null)) {
            SQLiteDatabase db = sqLiteController.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_TITLE_COL), title);
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_DESCRIPTION_COL), description);
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_CATEGORY_COL), category);
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_DATE_COL), date.getTime());
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_PRIORITY_COL), priority);
            values.put("ISDONE", 0);

            db.insert(ApplicationController.getAppContext().getString(R.string.TASK_TABLE_NAME), null, values);
            db.close();
        }
    }

    @Override
    public void updateTask(int id, String title, String description, String category, Date date, int priority) {
        SQLiteDatabase db = sqLiteController.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(title !=null)
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_TITLE_COL),title);
        if(description != null)
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_DESCRIPTION_COL),description);
        if(category !=null)
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_CATEGORY_COL),category);
        if(date != null)
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_DATE_COL),date.getTime());
        if(priority != 0)
            values.put(ApplicationController.getAppContext().getString(R.string.TASK_PRIORITY_COL),priority);


        if(values != null)
            db.update(ApplicationController.getAppContext().getString(R.string.TASK_TABLE_NAME),values,"id=?",new String[]{String.valueOf(id)});

        db.close();

    }

    @Override
    public void updateIsDoneTask(int id, int isDone) {
        SQLiteDatabase db = sqLiteController.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ISDONE",isDone);
        db.update(ApplicationController.getAppContext().getString(R.string.TASK_TABLE_NAME),values,"id=?",new String[]{String.valueOf(id)});

        db.close();
    }

    @Override
    public void deleteTask(int id) {
        SQLiteDatabase db = sqLiteController.getWritableDatabase();

        db.delete(ApplicationController.getAppContext().getString(R.string.TASK_TABLE_NAME),"id=?",new String[]{String.valueOf(id)});
    }

    @Override
    public List<Task> findTaskWithKeyword(String keyword) {
        String query = "SELECT * FROM "+ ApplicationController.getAppContext().getString(R.string.TASK_TABLE_NAME)+" WHERE "+ApplicationController.getAppContext().getString(R.string.TASK_TITLE_COL)+" LIKE \'%"+keyword+"%\'";
        SQLiteDatabase db = sqLiteController.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Boolean taskDone;
        List<Task> foundTasks = new ArrayList<>();
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){

            if(Integer.parseInt(cursor.getString(6)) == 0){
                taskDone = false;
            }
            else{
                taskDone = true;
            }
            Task newTask = new Task(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    new Date(cursor.getLong(4)),
                    Integer.parseInt(cursor.getString(5)),
                    taskDone);

            foundTasks.add(newTask);
            cursor.moveToNext();
        }

        db.close();
        return foundTasks;
    }
}
