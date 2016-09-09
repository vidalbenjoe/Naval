package com.ph.archilonian.naval.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ph.archilonian.naval.Utilities.Constants;
import com.ph.archilonian.naval.Utilities.Models.Lessons;

public class DatabaseSource {

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DatabaseSource(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    //---opens the database---
    public DatabaseSource open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insertLesson(Lessons lessons) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Constants.KEY_ROW_ID, lessons.getId());
        initialValues.put(Constants.KEY_CONTENT, lessons.getContent());
        initialValues.put(Constants.KEY_IMAGE, lessons.getImage());
        initialValues.put(Constants.KEY_LINKS, lessons.getLinks());
        initialValues.put(Constants.KEY_SEMESTER, lessons.getSemester());
        initialValues.put(Constants.KEY_TITLE, lessons.getTitle());
        initialValues.put(Constants.KEY_WEEK, lessons.getWeek());
        return db.insert(Constants.DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteContact(long rowId) {
        return db.delete(Constants.DATABASE_TABLE, Constants.KEY_ROW_ID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllLessons() {
        return db.query(Constants.DATABASE_TABLE, new String[]{
                Constants.KEY_ROW_ID,
                Constants.KEY_CONTENT,
                Constants.KEY_IMAGE,
                Constants.KEY_LINKS,
                Constants.KEY_SEMESTER,
                Constants.KEY_TITLE,
                Constants.KEY_WEEK}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getSpecifiLesson(String week) throws SQLException {
        Cursor mCursor = db.query(true, Constants.DATABASE_TABLE, new String[]{
                                Constants.KEY_ROW_ID,
                                Constants.KEY_CONTENT,
                                Constants.KEY_IMAGE,
                                Constants.KEY_LINKS,
                                Constants.KEY_SEMESTER,
                                Constants.KEY_TITLE,
                                Constants.KEY_WEEK},
                        Constants.KEY_WEEK+ "=" + "'" + week + "'" , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public void updateLesson(long rowId, Lessons lessons) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.KEY_CONTENT, lessons.getContent());
        cv.put(Constants.KEY_IMAGE, lessons.getImage());
        cv.put(Constants.KEY_LINKS, lessons.getLinks());
        cv.put(Constants.KEY_SEMESTER, lessons.getSemester());
        cv.put(Constants.KEY_TITLE, lessons.getTitle());
        cv.put(Constants.KEY_WEEK, lessons.getWeek());
        db.update(Constants.DATABASE_TABLE, cv, Constants.KEY_ROW_ID + "=" + rowId, null);
    }
}

