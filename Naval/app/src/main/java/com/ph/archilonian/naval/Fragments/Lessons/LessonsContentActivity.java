package com.ph.archilonian.naval.Fragments.Lessons;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.ph.archilonian.naval.Adapters.LessonContentAdapter;
import com.ph.archilonian.naval.Database.DatabaseSource;
import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.Constants;
import com.ph.archilonian.naval.Utilities.GUI.CustomViewPager;
import com.ph.archilonian.naval.Utilities.GUI.TouchImageView;
import com.ph.archilonian.naval.Utilities.Models.Lessons;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import nl.elastique.poetry.database.DatabaseHelper;

import static com.ph.archilonian.naval.Fragments.Lessons.FragmentContent.consSem;
import static com.ph.archilonian.naval.Fragments.Lessons.FragmentContent.consWeek;
import static com.ph.archilonian.naval.Fragments.Lessons.FragmentContent.pageCount;

public class LessonsContentActivity extends AppCompatActivity {
    Toolbar toolbar;
    static TouchImageView image;
    TextView linearTxtContentTitle, linearTxtContent;
    CustomViewPager pager;

    private static View mDetailsLayout;

    ArrayList<Lessons> lessonArrayList = new ArrayList<Lessons>();
    static ArrayList<HashMap<String, String>> arrayListTopic, arraylist, arrayCatcher;
    static FragmentManager fm;
    LessonContentAdapter lessonContentAdapter;

    int page = 0;
    String weekStr;
    String strImageBanner;
    static RelativeLayout relativeBanner;

    private DatabaseHelper mDatabaseHelper;
    private DatabaseSource dbSource;
    private static ProgressDialog progressDialog;
    Cursor checkData;

    String assessReview;
    int reviewPos;
    public static String hasContent;
    Boolean enableScroll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_content_main_layout);
        toolbar = (Toolbar) findViewById(R.id.lessonContentToolBar);
        Backendless.initApp(this, Constants.APPLICATION_ID, Constants.SECRET_KEY, Constants.VERSION);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        enableScroll = true;
        dbSource = new DatabaseSource(this);
        progressDialog = new ProgressDialog(LessonsContentActivity.this);


        // Referencing Fragment Manager
        fm = getSupportFragmentManager();

        mDetailsLayout = findViewById(R.id.details_layout);
        relativeBanner = (RelativeLayout) mDetailsLayout.findViewById(R.id.relativeBanner);
        image = (TouchImageView) mDetailsLayout.findViewById(R.id.details_image);
        linearTxtContentTitle = (TextView) mDetailsLayout.findViewById(R.id.txtContentTitle);
        linearTxtContent = (TextView) mDetailsLayout.findViewById(R.id.tvContents);
        pager = (CustomViewPager) mDetailsLayout.findViewById(R.id.pager);

        File database = new File("/data/data/com.ph.archilonian.naval/databases/DBNaval");
        weekStr = getIntent().getExtras().getString("week");
        assessReview = getIntent().getExtras().getString("indicator");
        reviewPos = getIntent().getExtras().getInt("position");

        Log.i("REVIEWPO:", String.valueOf(reviewPos));
        Log.i("weekStr:", String.valueOf(weekStr));


        if (hasContent.contentEquals("result")) {
            enableScroll = getIntent().getExtras().getBoolean("isScrollables");
        } else {
            enableScroll = true;
        }

        if (!database.exists()) {
            loadContentFromBackendless();
        } else {
            dbSource.open();
            checkData = dbSource.getSpecifiLesson(weekStr);
            if (checkData.getCount() > 0) {
                loadContentFromLocalDatabase();
            } else {
                loadContentFromBackendless();
                dbSource.close();
            }

        }
        Log.i("scrollEn:", String.valueOf(enableScroll));
        pager.setPagingEnabled(true);
        if (enableScroll == false) {
            pager.setPagingEnabled(false);
        } else {
            pager.setPagingEnabled(true);
        }
        pager.setOffscreenPageLimit(1);
        Log.i("POSITIT:", String.valueOf(pager.getCurrentItem()));

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //setupImageBanner(position);
                int count = LessonContentAdapter.PAGE_COUNT;

                Log.i("POSIT:", String.valueOf(position));

                if (pager.getCurrentItem() != count) {
                    page = position;
                    try {
                        strImageBanner = arrayCatcher.get(page).get("Image");
//                        Log.i("strImageBanner", strImageBanner);
                        if (strImageBanner != null) {
                            int resId = getResources().getIdentifier(strImageBanner, "drawable", getPackageName());
                            relativeBanner.setVisibility(View.VISIBLE);
                            image.setImageResource(resId);
//                            image.setVisibility(View.INVISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    relativeBanner.setVisibility(View.VISIBLE);
                } else {
                    relativeBanner.setVisibility(View.GONE);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (strImageBanner == null) {
//                    Log.i("onPageScroll:", strImageBanner);
                    relativeBanner.setVisibility(View.VISIBLE);
//                    image.setVisibility(View.GONE);
                    image.setImageResource(getResources().getIdentifier("noimage", "drawable", getPackageName()));
                }

            }
        });

    }

    void loadContentFromLocalDatabase() {
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("getting content from the database...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        arraylist = new ArrayList<HashMap<String, String>>();
        checkData = dbSource.getSpecifiLesson(weekStr);
        Log.i("checkDatacount:", String.valueOf(checkData.getCount()));
        if (checkData.getCount() > 0) {
            if (assessReview != null || reviewPos > 0) {
                if (assessReview.contentEquals("assessreview")) {
                    if (checkData.moveToPosition(reviewPos)) {
                        do {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("id", checkData.getString(checkData.getColumnIndex(Constants.KEY_ROW_ID)));
                            hm.put("Semester", checkData.getString(checkData.getColumnIndex(Constants.KEY_SEMESTER)));
                            hm.put("Week", checkData.getString(checkData.getColumnIndex(Constants.KEY_WEEK)));
                            hm.put("Title", checkData.getString(checkData.getColumnIndex(Constants.KEY_TITLE)));
                            hm.put("Content", checkData.getString(checkData.getColumnIndex(Constants.KEY_CONTENT)));
                            hm.put("Links", checkData.getString(checkData.getColumnIndex(Constants.KEY_LINKS)));
                            hm.put("Image", checkData.getString(checkData.getColumnIndex(Constants.KEY_IMAGE)));
                            hm.put("pageCount", String.valueOf(checkData.getCount()));
                            arraylist.add(hm);
                        } while (checkData.moveToNext());
                    }
                } else if (assessReview.isEmpty() || assessReview.length() < 0) {
                    if (checkData.moveToFirst()) {
                        do {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("id", checkData.getString(checkData.getColumnIndex(Constants.KEY_ROW_ID)));
                            hm.put("Semester", checkData.getString(checkData.getColumnIndex(Constants.KEY_SEMESTER)));
                            hm.put("Week", checkData.getString(checkData.getColumnIndex(Constants.KEY_WEEK)));
                            hm.put("Title", checkData.getString(checkData.getColumnIndex(Constants.KEY_TITLE)));
                            hm.put("Content", checkData.getString(checkData.getColumnIndex(Constants.KEY_CONTENT)));
                            hm.put("Links", checkData.getString(checkData.getColumnIndex(Constants.KEY_LINKS)));
                            hm.put("Image", checkData.getString(checkData.getColumnIndex(Constants.KEY_IMAGE)));
                            hm.put("pageCount", String.valueOf(checkData.getCount()));
                            arraylist.add(hm);
                        } while (checkData.moveToNext());
                    }
                }
            } else {
                if (checkData.moveToFirst()) {
                    do {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("id", checkData.getString(checkData.getColumnIndex(Constants.KEY_ROW_ID)));
                        hm.put("Semester", checkData.getString(checkData.getColumnIndex(Constants.KEY_SEMESTER)));
                        hm.put("Week", checkData.getString(checkData.getColumnIndex(Constants.KEY_WEEK)));
                        hm.put("Title", checkData.getString(checkData.getColumnIndex(Constants.KEY_TITLE)));
                        hm.put("Content", checkData.getString(checkData.getColumnIndex(Constants.KEY_CONTENT)));
                        hm.put("Links", checkData.getString(checkData.getColumnIndex(Constants.KEY_LINKS)));
                        hm.put("Image", checkData.getString(checkData.getColumnIndex(Constants.KEY_IMAGE)));
                        hm.put("pageCount", String.valueOf(checkData.getCount()));
                        arraylist.add(hm);
                    } while (checkData.moveToNext());
                }
            }


        }

        puttingDataIntoArrayCatcher();
    }

    void loadContentFromBackendless() {

        progressDialog.setTitle("Loading");
        progressDialog.setMessage("getting content from the database...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setRelated(Arrays.asList("Semester", "Week", "Title", "Content", "Links", "Image"));
        queryOptions.addSortByOption("id ASC");
        queryOptions.setPageSize(100);
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setWhereClause("Week = " + "'" + weekStr + "'");
        Backendless.Data.of(Lessons.class).find(query, new AsyncCallback<BackendlessCollection<Lessons>>() {

                    @Override
                    public void handleResponse(BackendlessCollection<Lessons> listOfRecords) {
                        Log.i("FAULTS", listOfRecords.getCurrentPage().size() + " ");
                        BackendlessCollection<Lessons> lessonCollection = listOfRecords;
                        List<Lessons> lessonList = new ArrayList<Lessons>();
                        arraylist = new ArrayList<HashMap<String, String>>();
                        lessonList.addAll(lessonCollection.getCurrentPage());

                        for (Lessons lessons : lessonList) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("id", lessons.getId());
                            hm.put("Semester", lessons.getSemester());
                            hm.put("Week", lessons.getWeek());
                            hm.put("Title", lessons.getTitle());
                            hm.put("Content", lessons.getContent());
                            hm.put("Links", lessons.getLinks());
                            hm.put("Image", lessons.getImage());
                            hm.put("pageCount", String.valueOf(lessonList.size()));
                            arraylist.add(hm);
                            dbSource.open();
                            dbSource.insertLesson(lessons);
                        }

                        puttingDataIntoArrayCatcher();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("Hanldefaults", fault.getMessage());
                        progressDialog.dismiss();
                    }
                }

        );

    }

    void puttingDataIntoArrayCatcher() {
        Log.i("lessonCOntent:", String.valueOf(arraylist));
        arrayListTopic = new ArrayList<HashMap<String, String>>();
        arrayCatcher = arraylist;

        for (int i = 0; i < arrayCatcher.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("Semester", arrayCatcher.get(i).get("Semester"));
            hm.put("Week", arrayCatcher.get(i).get("Week"));
            hm.put("Title", arrayCatcher.get(i).get("Title"));
            hm.put("Content", arrayCatcher.get(i).get("Content"));
            hm.put("Links", arrayCatcher.get(i).get("Links"));
            hm.put("Image", arrayCatcher.get(i).get("Image"));
            hm.put("pageCount", arrayCatcher.get(i).get("pageCount"));
            arrayListTopic.add(hm);
        }

//                        JSONArray jsArray = new JSONArray(arrayCatcher);
//                        // Load JSON
//                        JSONObject json = null;
//                        try {
//                            json = jsArray.getJSONObject(arrayCatcher.size());
//                            // Get child arrays from JSON
//                            JSONArray users_json = JsonPathResolver.resolveArray(json, "users");
//                            JsonPersister persister = new JsonPersister(mDatabaseHelper.getWritableDatabase());
//                            persister.persistArray(LessonTable.class, users_json);
//                        } catch (JSONException | JsonPathException e) {
//                            e.printStackTrace();
//                        }


        // Persist arrays to database
        if (arrayCatcher.size() > 0) {
            linearTxtContentTitle.setText(arrayCatcher.get(0).get("Title"));
            linearTxtContent.setText(arrayCatcher.get(0).get("Content"));
            consSem = arrayCatcher.get(0).get("Semester");
            consWeek = arrayCatcher.get(0).get("Week");
            Log.i("pageCOuntCacher", arrayCatcher.get(0).get("pageCount"));

            if (arrayCatcher.get(0).get("pageCount") == null){

            }else{
                pageCount = Integer.parseInt(arrayCatcher.get(0).get("pageCount"));
            }

            lessonContentAdapter = new LessonContentAdapter(fm, arrayListTopic);
            pager.setAdapter(lessonContentAdapter);
        } else {
            Toast.makeText(LessonsContentActivity.this, "No lessons available", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
        dbSource.close();
    }


    public void loadingDialog() {
        final Dialog dialog = new Dialog(LessonsContentActivity.this);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // app icon in action bar clicked; go home
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        LessonsContentActivity.this.finish();
        super.onBackPressed();
    }


}
