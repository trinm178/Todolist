package com.example.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workmanager";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Task";
    private static final String TABLE_TASKNAME = "TaskName";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL );",TABLE_NAME, TABLE_TASKNAME);
        sqLiteDatabase.execSQL(query);

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = String.format("DELETE TABLE IF EXISTS %s", TABLE_NAME);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);

        Log.d(TAG, "onUpgrade: ");
    }

    public void InsertTask(String task){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(TABLE_TASKNAME, task);
        database.insert(TABLE_NAME, null, values);
        database.close();
        Log.d(TAG, "addCV: Thành công");
    }

    public void DeleteTask(String task){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME,TABLE_TASKNAME + "=?", new String[]{task});
        database.close();
    }
    public ArrayList<String> getTask(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[]{TABLE_TASKNAME},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(TABLE_TASKNAME);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        database.close();
        return taskList;
    }

}
