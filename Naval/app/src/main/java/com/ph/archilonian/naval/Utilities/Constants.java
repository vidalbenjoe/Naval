package com.ph.archilonian.naval.Utilities;

import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.Models.Researchers;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    // Local Database Variables
    public static final String KEY_ID = "_id";
    public static final String KEY_ROW_ID = "row_id";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LINKS = "links";
    public static final String KEY_SEMESTER = "semester";
    public static final String KEY_TITLE = "title";
    public static final String KEY_WEEK = "week";
    public static final String TAG = "DBAdapter";

    public static final String DATABASE_NAME = "DBNaval";
    public static final String DATABASE_TABLE = "lessons";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_CREATE =
            "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_ROW_ID + ","
                    + KEY_CONTENT + ","
                    + KEY_IMAGE + ","
                    + KEY_LINKS + ","
                    + KEY_SEMESTER + ","
                    + KEY_TITLE + ","
                    + KEY_WEEK + "" + ")";
    //backendless
    public static String APPLICATION_ID = "EF862A3C-B196-B6F3-FF16-7D3226B9A800";
    public static String SECRET_KEY = "D5F48617-C18C-9663-FFFF-7009A8F32C00";
    public static final String VERSION = "v1";

    public static boolean isGuideEnabled = true;
    public static int shipObjIndex = 0;
    public static int shipPartsIndex = 0;
    public static int selectedInList = 0;
    public static boolean isRandomized = false;

    public static String getHeaderText() {
        return headerText;
    }

    public static void setHeaderText(String headerText) {
        Constants.headerText = headerText;
    }

    public static String headerText;

    public static final List<Researchers> researchers = new ArrayList<>();

    static {
        researchers.add(new Researchers(R.drawable.mikeimg, "Michael Cunanan", R.color.bb_tabletRightBorderDark, "Lead Programmer", "UI Designer", "Design"));
        researchers.add(new Researchers(R.drawable.tatsuyaimg, "Danyel Garcia", R.color.bootstrap_brand_success, "Programmer", "Data analyst", "Documentation"));
        researchers.add(new Researchers(R.drawable.splashscreenbg, "Drazen Angulo Gabilo", R.color.radiobt_color, "3D Modeler", "Designer"));
    }

}
