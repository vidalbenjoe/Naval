package com.ph.archilonian.naval.Database.AssessDB;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionCache {
    // Shared Preferences
    SharedPreferences qpref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "QuizPref";

    // All Shared Preferences Keys
    public static final String IS_TAKEN1 = "isquiz1taken";


    public static final String NAV_MAX_ITEM1 = "max_quiz_1";


    public static final String NAV_QUIZ_TAKE = "naval_last_quiz";


    public static final String ALL_QUIZ_TAKE = "all_last_quiz";


    public static final String REPEATING1 = "repeat_quiz1";

    public SessionCache(Context context) {
        this._context = context;
        qpref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = qpref.edit();
    }


    public void FinishSessionNum1(String repeat) {
        // Storing login value as TRUE
        editor.putBoolean(IS_TAKEN1, true);
        editor.putString(REPEATING1, repeat);
        editor.commit();
    }


    public void StoreTotal1(String total1) {

        editor.putString(NAV_MAX_ITEM1, total1);
        editor.commit();
    }


    public void StoreFlLastQuizTaken(String taken) {
        editor.putString(NAV_QUIZ_TAKE, taken);
        editor.commit();
    }

    public void StoreAllLastQuizTaken(String taken) {
        editor.putString(ALL_QUIZ_TAKE, taken);
        editor.commit();
    }

    public static final String _LASTSCORE = "_lastquiz";
    public static final String _LASTQUIZNAME = "_coursename";
    public static final String _LASTQDETAILS = "_lastqdetails";

    public void StoredLastScore(String _score, String _qdetails, String _quizname) {
        editor.putString(_LASTSCORE, _score);
        editor.putString(_LASTQDETAILS, _qdetails);
        editor.putString(_LASTQUIZNAME, _quizname);
        editor.commit();
    }

    public HashMap<String, String> getTotalSum() {
        HashMap<String, String> totalsum = new HashMap<String, String>();
        totalsum.put(NAV_MAX_ITEM1, qpref.getString(NAV_MAX_ITEM1, null));
        totalsum.put(NAV_QUIZ_TAKE, qpref.getString(NAV_QUIZ_TAKE, null));
        totalsum.put(ALL_QUIZ_TAKE, qpref.getString(ALL_QUIZ_TAKE, null));
        totalsum.put(REPEATING1, qpref.getString(REPEATING1, null));
        totalsum.put(_LASTSCORE, qpref.getString(_LASTSCORE, null));
        totalsum.put(_LASTQUIZNAME, qpref.getString(_LASTQUIZNAME, null));
        totalsum.put(_LASTQDETAILS, qpref.getString(_LASTQDETAILS, null));
        return totalsum;
    }

    // Get Login State
    public boolean hasFlQuiz1() {
        return qpref.getBoolean(IS_TAKEN1, false);
    }

}