package com.ph.archilonian.naval.Database.AssessDB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QUIZDBAdapter {
    public static final int DATABASE_VERSION = 21;
    public static final String DATABASE_NAME = "navalquiz_db";
    public static final String USER_TABLE = "user_info";

    public static final String USER_ID = "_id";
    public static final String USER_NAME = "uname";
    public static final String USER_FNAME = "fname";
    public static final String USER_LNAME = "lname";
    public static final String USER_AGE = "age";
    public static final String USER_SEX = "sex";

    public static final String USER_TABLE_SQL = "CREATE TABLE " + USER_TABLE
            + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT, "
            + USER_FNAME + " TEXT, "
            + USER_LNAME + " TEXT, "
            + USER_AGE + " TEXT, "
            + USER_SEX + " TEXT )";

    public static final String SCORE_TABLE = "navalscores";

    public static final String _ID = "_id";
    public static final String _RID = "rid";
    public static final String _CCID = "ccid";
    public static final String _NAME = "score_name";
    public static final String _QDETAILS = "quiz_details";
    public static final String _SCORE = "spoint";
    // public static final String _TOTAL = "stotal";
    public static final String _DATE = "sdate";
    public static final String _USERNAMESCORE = "usernamescore";

    public static final String SCORE_TABLE_SQL = "CREATE TABLE " + SCORE_TABLE
            + "(" + _ID + " INTEGER PRIMARY KEY autoincrement, "
            + _RID + " INTEGER not null, "
            + _CCID + " INTEGER not null, "
            + _NAME + " TEXT not null, "
            + _QDETAILS + " TEXT not null, "
            + _SCORE + " TEXT not null, "
            + _DATE + " TEXT not null, "
            + _USERNAMESCORE + " TEXT not null " + ");";

    public static final String _AUXID = "_id";
    public static final String _CHID = "_kid";
    public static final String _AUXNAME = "_navalname";
    public static final String _AUXRETAKE = "_navalretake";
    public static final String _AUXAVERAGE = "_navalaverage";
    public static final String AUXQUIZ_TABLE = "navalquiz_table";

    public static final String JSQUIZ_TABLE_SQL = "CREATE TABLE "
            + AUXQUIZ_TABLE + "(" + _AUXID
            + " INTEGER PRIMARY KEY autoincrement, "
            + _CHID + " INTEGER not null, "
            + _AUXNAME + " TEXT not null, "
            + _AUXRETAKE + " TEXT not null, "
            + _AUXAVERAGE + " TEXT not null " + ");";


    public static final String QUE_ID = "qid";
    public static final String QUE_LESSON = "lid";
    public static final String QUE_ITEM = "qitem";
    public static final String QUE_ANS = "qans";
    public static final String QUE_OPT1 = "qopt1";
    public static final String QUE_OPT2 = "qopt2";
    public static final String QUE_OPT3 = "qopt3";
    public static final String QUE_OPT4 = "qopt4";


    public static final String PRELIMWEEKONE_TABLE = "prelim_weekone_tbl";

    public static final String PRELIM_WEEKONE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + PRELIMWEEKONE_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String PRELIMWEEKTWO_TABLE = "prelim_weektwo_tbl";

    public static final String PRELIM_WEEKTWO_TABLE = "CREATE TABLE IF NOT EXISTS "
            + PRELIMWEEKTWO_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String PRELIMWEEKTHREE_TABLE = "prelim_weekthree_tbl";

    public static final String PRELIM_WEEKTHREE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + PRELIMWEEKTHREE_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";

    public static final String PRELIMWEEKFOUR_TABLE = "prelim_weekfour_tbl";

    public static final String PRELIM_WEEKFOUR_TABLE = "CREATE TABLE IF NOT EXISTS "
            + PRELIMWEEKFOUR_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String PRELIMWEEKFIVE_TABLE = "prelim_weekfive_tbl";

    public static final String PRELIM_WEEKFIVE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + PRELIMWEEKFIVE_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String PRELIMWEEKSIX_TABLE = "prelim_weeksix_tbl";

    public static final String PRELIM_WEEKSIX_TABLE = "CREATE TABLE IF NOT EXISTS "
            + PRELIMWEEKSIX_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String MIDTERMWEEKSEVEN_TABLE = "midterm_weekseven_tbl";

    public static final String MIDTERM_WEEKSEVEN_TABLE = "CREATE TABLE IF NOT EXISTS "
            + MIDTERMWEEKSEVEN_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String MIDTERMWEEKEIGHT_TABLE = "midterm_weekeight_tbl";

    public static final String MIDTERM_WEEKEIGHT_TABLE = "CREATE TABLE IF NOT EXISTS "
            + MIDTERMWEEKEIGHT_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String MIDTERMWEEKNINE_TABLE = "midterm_weeknine_tbl";

    public static final String MIDTERM_WEEKNINE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + MIDTERMWEEKNINE_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String MIDTERMWEEKELEVEN_TABLE = "midterm_weekeleven_tbl";

    public static final String MIDTERM_WEEKELEVEN_TABLE = "CREATE TABLE IF NOT EXISTS "
            + MIDTERMWEEKELEVEN_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String FINALSWEEKTHIRTEEN_TABLE = "final_weekthirteen_tbl";

    public static final String FINALS_WEEKTHIRTEEN_TABLE = "CREATE TABLE IF NOT EXISTS "
            + FINALSWEEKTHIRTEEN_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";

    public static final String FINALSWEEKFOURTEEN_TABLE = "final_weekfourteen_tbl";

    public static final String FINALS_WEEKFOURTEEN_TABLE = "CREATE TABLE IF NOT EXISTS "
            + FINALSWEEKFOURTEEN_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String FINALSWEEKFIFTHTEEN_TABLE = "final_weekfifthteen_tbl";

    public static final String FINALS_WEEKFIFTHTEEN_TABLE = "CREATE TABLE IF NOT EXISTS "
            + FINALSWEEKFIFTHTEEN_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";

    public static final String FINALSWEEKSIXTHTEEN_TABLE = "final_weeksisxthteen_tbl";

    public static final String FINALS_WEEKSIXTHTEEN_TABLE = "CREATE TABLE IF NOT EXISTS "
            + FINALSWEEKSIXTHTEEN_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String FINALSWEEKSEVENTEEN_TABLE = "final_weekseventeen_tbl";

    public static final String FINALS_WEEKSEVENTEEN_TABLE = "CREATE TABLE IF NOT EXISTS "
            + FINALSWEEKSEVENTEEN_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";


    public static final String FINALSWEEKEIGHTEEN_TABLE = "final_weekeighteen_tbl";

    public static final String FINALS_WEEKEIGHTEEN_TABLE = "CREATE TABLE IF NOT EXISTS "
            + FINALSWEEKEIGHTEEN_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";

    public static final String SIMULATIONQUIZ_TABLE = "simulation_quiz_tbl";

    public static final String SIMULATION_QUIZ_TABLE = "CREATE TABLE IF NOT EXISTS "
            + SIMULATIONQUIZ_TABLE + "(" + QUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUE_LESSON + " TEXT, "
            + QUE_ITEM + " TEXT, "
            + QUE_ANS + " TEXT, "
            + QUE_OPT1 + " TEXT, "
            + QUE_OPT2 + " TEXT, "
            + QUE_OPT3 + " TEXT, "
            + QUE_OPT4 + " TEXT  )";

    // CREATING TEMPORARY STORAGE BY USING SQLITE
    public static final String TEMP_QUE_TABLE = "js_temp";

    public static final String TEMP_ID = "_id";
    public static final String TEMP_SET = "temp_set";
    public static final String TEMP_NULL = "temp_null";
    public static final String TEMP_LESSON = "temp_ref";
    public static final String TEMP_Q_ITEM = "temp_qitem";
    public static final String TEMP_ANS = "temp_ans";
    public static final String TEMP_UANS = "temp_user_ans";

    public static final String[] ALL_TEMPS = new String[]{TEMP_ID, TEMP_SET,
            TEMP_LESSON, TEMP_Q_ITEM, TEMP_ANS, TEMP_UANS, TEMP_NULL};
    public static final String TEMP_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + TEMP_QUE_TABLE + "(" + TEMP_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEMP_SET + " TEXT, "
            + TEMP_NULL + " TEXT, " + TEMP_LESSON + " TEXT, " + TEMP_Q_ITEM + " TEXT, " + TEMP_ANS
            + " TEXT, " + TEMP_UANS + " TEXT )";

    public static final int COL_ROWID = 0;
    public static final int COL_SETID = 1;
    public static final int COL_REFID = 2;
    public static final int COL_QITEM = 3;
    public static final int COL_QANS = 4;
    public static final int COL_QUANS = 5;
    // Context of application who uses us.

    private final Context context;

    private Dbhandler dbhandler;
    private static SQLiteDatabase db;

    public QUIZDBAdapter(Context ctx) {
        this.context = ctx;
        dbhandler = new Dbhandler(context);
    }

    // Open the database connection.
    public QUIZDBAdapter open() {
        db = dbhandler.getReadableDatabase();
        return this;
    }


    // Close the database connection.
    public void close() {
        dbhandler.close();
    }

	/*
     * public long addScores(int rid, String _name, int _score, String _date){
	 * ContentValues vl = new ContentValues(); vl.put(_RID, rid); vl.put(_NAME,
	 * _name); vl.put(_SCORE, _score); // vl.put(_TOTAL, _total); vl.put(_DATE,
	 * _date); return db.insert(SCORE_TABLE, null, vl); }
	 */

    public long addscores(int rid, int ccid, String _name, String _qdetails,
                          int _score, String _date, String name) {
        ContentValues vl = new ContentValues();
        vl.put(_RID, rid);
        vl.put(_CCID, ccid);
        vl.put(_NAME, _name);
        vl.put(_QDETAILS, _qdetails);
        vl.put(_SCORE, _score);
        // vl.put(_TOTAL, _total);
        vl.put(_DATE, _date);
        vl.put(_USERNAMESCORE, name);
        return db.insert(SCORE_TABLE, null, vl);
    }

    public long addAUXquiz(int _cid, String _jsname, String _retake,
                           String _jsaverage) {
        ContentValues vl = new ContentValues();
        vl.put(_CHID, _cid);
        vl.put(_AUXNAME, _jsname);
        vl.put(_AUXRETAKE, _retake);
        vl.put(_AUXAVERAGE, _jsaverage);

        return db.insert(AUXQUIZ_TABLE, null, vl);
    }

    // DELETE ROW WITH SCORE ID
    public void deleteQuiz(String Quizname) {
        // String where = _NAME + "=" + QuizSet;
        db.delete(AUXQUIZ_TABLE, _AUXNAME + "=?", new String[]{Quizname});
    }

	/*
     * //GET ALL THE SCORES WITH RETAKE AND _NAME public Cursor
	 * getAllScorewith(long ccid, String _name){ Cursor cr =
	 * db.rawQuery("SELECT * FROM " +SCORE_TABLE+ " WHERE rid=? AND ccid=?", new
	 * String[]{Long.toString(ccid), _name}); if(cr != null){ cr.moveToFirst();
	 * } return cr; }
	 */

    public Cursor getAllscorewithChapter(String _name) {
        // String where = _NAME + "=" + _name;
        Cursor cr = db.rawQuery("SELECT * FROM " + SCORE_TABLE + " WHERE "
                + _NAME + "=?", new String[]{_name});
        if (cr != null) {
            cr.moveToFirst();
        }
        return cr;
    }

    public Cursor getScoreChapterName(String _name) {
        // String where = _NAME + "=" + _name;
        Cursor cr = db.rawQuery("SELECT * FROM " + SCORE_TABLE + " WHERE "
                + _NAME + "=?", new String[]{_name});
        if (cr != null) {
            cr.moveToFirst();
        }
        return cr;
    }

    public void deletescorerowSet(long ccid, String _name) {
        db.delete(SCORE_TABLE, "ccid=? AND score_name=?",
                new String[]{Long.toString(ccid), _name});
    }

    public void addtempQuestion(TempQuestion tquestion) {

        ContentValues values = new ContentValues();
        values.put(TEMP_SET, tquestion.getQset());
        values.put(TEMP_LESSON, tquestion.getLid());
        values.put(TEMP_Q_ITEM, tquestion.getQitem());
        values.put(TEMP_ANS, tquestion.getQans());
        values.put(TEMP_UANS, tquestion.getQuserans());
        values.put(TEMP_NULL, "");
        db.insert(TEMP_QUE_TABLE, null, values);
    }

    // GET ALL ROWS
    public Cursor getAlltempRows() {
        Cursor cr = db.query(true, TEMP_QUE_TABLE, ALL_TEMPS, null, null, null,
                null, null, null);
        if (cr != null) {
            cr.moveToFirst();
        }
        return cr;
    }

    // DELETE ROW WITH ID
    public boolean delTempRows(long rowId) {
        String where = TEMP_ID + "=" + rowId;
        return db.delete(TEMP_QUE_TABLE, where, null) != 0;
    }

    // DELETE ALL ROWS
    public void delAllTempRows() {
        Cursor c = getAlltempRows();
        long rowId = c.getColumnIndexOrThrow(TEMP_ID);
        if (c.moveToFirst()) {
            do {
                delTempRows(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = TEMP_ID + "=" + rowId;
        Cursor c = db.query(true, TEMP_QUE_TABLE, ALL_TEMPS, where, null, null,
                null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

	/*
     * public int getAllrowswithA(int a){ int sum = 0; Cursor cursor =
	 * db.rawQuery("SELECT sum(spoint) AS myTOTAL FROM "+ SCORE_TABLE
	 * +" WHERE rid=?",new String[]{Integer.toString(a)});
	 * if(cursor.moveToFirst()){ sum =
	 * cursor.getInt(cursor.getColumnIndex("myTOTAL")); } return sum; }
	 */

    public int getallRowswithName(String _name) {
        int sum = 0;
        Cursor cursor = db.rawQuery("SELECT sum(spoint) AS myCTOTAL FROM "
                + SCORE_TABLE + " WHERE score_name=?", new String[]{_name});
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(cursor.getColumnIndex("myCTOTAL"));
        }
        return sum;
    }

    /*
     * public int getAlltotalwithA(int b){ int all = 0; Cursor cursor =
     * db.rawQuery("SELECT sum(stotal) AS myMAX FROM "+ SCORE_TABLE
     * +" WHERE rid=?",new String[]{Integer.toString(b)});
     * if(cursor.moveToFirst()){ all =
     * cursor.getInt(cursor.getColumnIndex("myMAX")); } return all; }
     */
    // ADD QUESTION


    public void PrelimWeekOneQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(PRELIMWEEKONE_TABLE, null, values);
    }


    public void PrelimWeekTwoQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(PRELIMWEEKTWO_TABLE, null, values);
    }


    public void PrelimWeekThreeQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(PRELIMWEEKTHREE_TABLE, null, values);
    }

    public void PrelimWeekFourQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(PRELIMWEEKFOUR_TABLE, null, values);
    }


    public void PrelimWeekFiveQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(PRELIMWEEKFIVE_TABLE, null, values);
    }

    public void PrelimWeekSixQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(PRELIMWEEKSIX_TABLE, null, values);
    }


    /**
     * ************************** MIDTERM
     *******************************/

    public void MidtermWeekSevenQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(MIDTERMWEEKSEVEN_TABLE, null, values);
    }


    public void MidtermWeekEightQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(MIDTERMWEEKEIGHT_TABLE, null, values);
    }


    public void MidtermWeekNineQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(MIDTERMWEEKNINE_TABLE, null, values);
    }

    public void MidtermWeekElevenQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(MIDTERMWEEKELEVEN_TABLE, null, values);
    }

    /**
     * ************************** FINALS
     *******************************/

    public void FinalsWeekThirteenQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(FINALSWEEKTHIRTEEN_TABLE, null, values);
    }

    public void FinalsWeekFourteenQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(FINALSWEEKFOURTEEN_TABLE, null, values);
    }

    public void FinalsWeekFifthteenQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(FINALSWEEKFIFTHTEEN_TABLE, null, values);
    }

    public void FinalsWeekSixthteenQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(FINALSWEEKSIXTHTEEN_TABLE, null, values);
    }


    public void FinalsWeekSevenTeenQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(FINALSWEEKSEVENTEEN_TABLE, null, values);
    }


    public void FinalsWeekEighTeenQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(FINALSWEEKEIGHTEEN_TABLE, null, values);
    }



    public void simulationsQuestions(Question question) {
        ContentValues values = new ContentValues();
        values.put(QUE_LESSON, question.getLid());
        values.put(QUE_ITEM, question.getQitem());
        values.put(QUE_ANS, question.getQans());
        values.put(QUE_OPT1, question.getOpta());
        values.put(QUE_OPT2, question.getOptb());
        values.put(QUE_OPT3, question.getOptc());
        values.put(QUE_OPT4, question.getOptd());
        db.insert(SIMULATIONQUIZ_TABLE, null, values);
    }


    // GET ALL LIST OF QUESTIONS
    public List<Question> getAllQuestionsSimulation() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + SIMULATIONQUIZ_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }



    // GET ALL LIST OF QUESTIONS
    public List<Question> getAllQuestionsPrelimWeekOne() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PRELIMWEEKONE_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsPrelimWeekTWO() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + PRELIMWEEKTWO_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsPrelimWeekThree() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PRELIMWEEKTHREE_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

    public List<Question> getAllQuestionsPrelimWeekFour() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PRELIMWEEKFOUR_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsPrelimWeekFive() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PRELIMWEEKFIVE_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

    public List<Question> getAllQuestionsPrelimWeekSix() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PRELIMWEEKSIX_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsMidtermWeekSeven() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MIDTERMWEEKSEVEN_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsMidtermWeekEight() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MIDTERMWEEKEIGHT_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsMidtermWeekNine() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MIDTERMWEEKNINE_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

    public List<Question> getAllQuestionsMidtermWeekEleven() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MIDTERMWEEKELEVEN_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

    public List<Question> getAllQuestionsFinalsWeekThirteen() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FINALSWEEKTHIRTEEN_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

    public List<Question> getAllQuestionsFinalsWeekFourteen() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FINALSWEEKFOURTEEN_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsFinalsWeekFifthteen() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FINALSWEEKFIFTHTEEN_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsFinalsWeekSixthteen() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FINALSWEEKSIXTHTEEN_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsFinalsWeekSeventeen() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FINALSWEEKSEVENTEEN_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    public List<Question> getAllQuestionsFinalsWeekEighteen() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FINALSWEEKEIGHTEEN_TABLE;
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY RANDOM() LIMIT 15", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQid(cursor.getInt(0));
                quest.setLid(cursor.getString(1));
                quest.setQitem(cursor.getString(2));
                quest.setQans(cursor.getString(3));
                quest.setOpta(cursor.getString(4));
                quest.setOptb(cursor.getString(5));
                quest.setOptc(cursor.getString(6));
                quest.setOptd(cursor.getString(7));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

    public ArrayList<HashMap<String, String>> getScores(String category) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM auxmachscores where score_name='" + category + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("score_name", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
                hm.put("spoint", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(5))));
                hm.put("usernamescore", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(7))));
                arrayList.add(hm);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return arrayList;
    }


    public static class Dbhandler extends SQLiteOpenHelper {

        public Dbhandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(USER_TABLE_SQL);
            _db.execSQL(SCORE_TABLE_SQL);
            _db.execSQL(JSQUIZ_TABLE_SQL);

            _db.execSQL(PRELIM_WEEKONE_TABLE);
            _db.execSQL(PRELIM_WEEKTWO_TABLE);
            _db.execSQL(PRELIM_WEEKTHREE_TABLE);
            _db.execSQL(PRELIM_WEEKFOUR_TABLE);
            _db.execSQL(PRELIM_WEEKFIVE_TABLE);
            _db.execSQL(PRELIM_WEEKSIX_TABLE);

            _db.execSQL(MIDTERM_WEEKSEVEN_TABLE);
            _db.execSQL(MIDTERM_WEEKEIGHT_TABLE);
            _db.execSQL(MIDTERM_WEEKNINE_TABLE);
            _db.execSQL(MIDTERM_WEEKELEVEN_TABLE);

            _db.execSQL(FINALS_WEEKTHIRTEEN_TABLE);
            _db.execSQL(FINALS_WEEKFOURTEEN_TABLE);
            _db.execSQL(FINALS_WEEKFIFTHTEEN_TABLE);
            _db.execSQL(FINALS_WEEKSIXTHTEEN_TABLE);
            _db.execSQL(FINALS_WEEKSEVENTEEN_TABLE);
            _db.execSQL(FINALS_WEEKEIGHTEEN_TABLE);

            _db.execSQL(SIMULATION_QUIZ_TABLE);

            _db.execSQL(TEMP_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + SCORE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + AUXQUIZ_TABLE);

            _db.execSQL("DROP TABLE IF EXISTS " + PRELIMWEEKONE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + PRELIMWEEKTWO_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + PRELIMWEEKTHREE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + PRELIMWEEKFOUR_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + PRELIMWEEKFIVE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + PRELIMWEEKSIX_TABLE);

            _db.execSQL("DROP TABLE IF EXISTS " + MIDTERMWEEKSEVEN_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + MIDTERMWEEKEIGHT_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + MIDTERMWEEKNINE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + MIDTERMWEEKELEVEN_TABLE);

            _db.execSQL("DROP TABLE IF EXISTS " + FINALSWEEKTHIRTEEN_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + FINALSWEEKFOURTEEN_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + FINALSWEEKFIFTHTEEN_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + FINALSWEEKSIXTHTEEN_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + FINALSWEEKSEVENTEEN_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + FINALSWEEKEIGHTEEN_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + SIMULATIONQUIZ_TABLE);

            _db.execSQL("DROP TABLE IF EXISTS " + TEMP_QUE_TABLE);
            onCreate(_db);
        }
    }

}
