package com.ph.archilonian.naval.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private static ArrayList<HashMap<String, String>> arraylist = null;
    String TABLE_NAME = "tbl_content";
    String selectQuery = null;
    HashMap<String, String> hm = null;
    Cursor cursor;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {

        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of questions
     */

    public void saveScore(HashMap<String, String> hashMap) {
        database = openHelper.getWritableDatabase();
        ContentValues datas = new ContentValues();
        datas.put("name", hashMap.get("name"));
        datas.put("data", hashMap.get("date"));
        datas.put("score", hashMap.get("score"));

        database.insert("tbl_scoreboard", null, datas);
        database.close();

    }

    public ArrayList<HashMap<String, String>> getSpecificTopic(String strWhereClause) {
        selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE category='" + strWhereClause + "'";
        database = openHelper.getReadableDatabase();
        arraylist = new ArrayList<HashMap<String, String>>();
        cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                hm = new HashMap<String, String>();
                hm.put("id", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0))));
                hm.put("category", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
                hm.put("title", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
                hm.put("content", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
                hm.put("image", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));
                arraylist.add(hm);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return arraylist;
    }


    public ArrayList<HashMap<String, String>> searchContents() {
        selectQuery = "SELECT * FROM " + TABLE_NAME;
        database = openHelper.getReadableDatabase();
        arraylist = new ArrayList<HashMap<String, String>>();
        cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                hm = new HashMap<String, String>();
                hm.put("category", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
                hm.put("title", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
                hm.put("content", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
                hm.put("image", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));
                arraylist.add(hm);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return arraylist;
    }
}
