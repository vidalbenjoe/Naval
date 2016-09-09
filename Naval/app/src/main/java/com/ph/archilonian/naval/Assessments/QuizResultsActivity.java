package com.ph.archilonian.naval.Assessments;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ph.archilonian.naval.Database.AssessDB.QUIZDBAdapter;
import com.ph.archilonian.naval.Database.AssessDB.QuizModel;
import com.ph.archilonian.naval.Database.AssessDB.SessionCache;
import com.ph.archilonian.naval.Fragments.Lessons.LessonsContentActivity;
import com.ph.archilonian.naval.R;
import com.plattysoft.leonids.ParticleSystem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class QuizResultsActivity extends AppCompatActivity implements Animation.AnimationListener {
    TextView quizresultMsg;
    TextView correct;
    TextView wrong;
    TextView mesg;
    Button bscorelog, bqresult;
    int wrongans;
    int finalscore;
    int setq;
    String ncourse;
    String qdetails;
    QUIZDBAdapter myDb;

    SessionCache QuizSession;
    String tdate;

    Animation push_up_in, bounce_in1, bounce_in2, bounce_in3, fade_in;
    //    private TextView tvcorrect, tvwrong;
    private Cursor cr;

    int totalsumof;
    int sumOf;
    double jsper;
    int retake;

    String quizdetails;

    int prevTotal;
    int curTotal;
    String finalDate, Uname;
    Intent intent;
    String initVal = "1";

    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_main_results);
        toolbar = (Toolbar) findViewById(R.id.resulttoolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ConfetiAnim();
        intent = new Intent();
        QuizSession = new SessionCache(getApplicationContext());
        openDB();
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy");
        finalDate = timeFormat.format(date);

        HashMap<String, String> totalSum = QuizSession.getTotalSum();
        sumOf = Integer.parseInt(totalSum.get(SessionCache.NAV_MAX_ITEM1));
        retake = Integer.parseInt(totalSum.get(SessionCache.REPEATING1));

        totalsumof = myDb.getallRowswithName(QuizModel.getAssessmentCategory() + " 1");
        if (totalsumof != 0) {
            double psDiv = (double) totalsumof / sumOf;
            jsper = psDiv * 100.0;
        } else {
            jsper = 0;
        }

        DecimalFormat df = new DecimalFormat("00.00");
        String quizaverage = df.format(jsper) + "%";

        if (retake == 1) {
            quizdetails = "Quiz has been taken for the first time";
        } else {
            quizdetails = "Quiz has been taken " + retake + " times";
        }

//        myDb.addjsquiz(1, "Machinery", quizdetails, quizaverage);
        myDb.addAUXquiz(1, QuizModel.getAssessmentCategory(), quizdetails, quizaverage);
        Typeface roadBrushttf = Typeface.createFromAsset(getAssets(), "fonts/ironman.ttf");

        quizresultMsg = (TextView) findViewById(R.id.quizresultMsg);
        correct = (TextView) findViewById(R.id.tvCorrect);
        wrong = (TextView) findViewById(R.id.tvWrong);
        mesg = (TextView) findViewById(R.id.tvMesg);
        bscorelog = (Button) findViewById(R.id.bSlog);
        bqresult = (Button) findViewById(R.id.bQview);

        bscorelog.setVisibility(View.GONE);
        Bundle g = getIntent().getExtras();
        setq = g.getInt("qno");
        finalscore = g.getInt("score");
        ncourse = g.getString("course");
        qdetails = g.getString("quizdetails");

        correct.setTypeface(roadBrushttf);
        wrong.setTypeface(roadBrushttf);

        bscorelog.setTypeface(roadBrushttf);
        bqresult.setTypeface(roadBrushttf);

        wrongans = 15 - finalscore;
        correct.setText(finalscore + "");
        wrong.setText(wrongans + "");
        mesg.setText(finalscore + "/15");

        if (finalscore >= 15)
            quizresultMsg.setText("Perfect!");
        if (finalscore <= 6) {
            quizresultMsg.setText("You have to review the lessons more");
        } else if (finalscore >= 10) {
            quizresultMsg.setText(QuizModel.getAssessmentCategory() + " \nCongratulation!");
        }

        tdate = totalSum.get(SessionCache.NAV_QUIZ_TAKE);
        QuizSession.StoredLastScore(mesg.getText().toString(), qdetails,
                ncourse);

        push_up_in = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.push_up_in);

        fade_in = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        bounce_in1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);

        bounce_in2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);

        bounce_in3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);

        push_up_in.setAnimationListener(this);
        fade_in.setAnimationListener(this);

        bounce_in1.setAnimationListener(this);
        bounce_in2.setAnimationListener(this);
        bounce_in3.setAnimationListener(this);

        correct.setAnimation(bounce_in1);

        bscorelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDB();
                populateSwithdb();
            }
        });

        bqresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDB();
                populateQwithdb();
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void populateSwithdb() {

        final Dialog dialog = new Dialog(this, R.style.DialogAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.quiz_resulthistory_layout);

        TextView tvQuizChapter = (TextView) dialog.findViewById(R.id.tvchapterName);
        TextView tvLastQuiz = (TextView) dialog.findViewById(R.id.tvlastquizhistory);
        ListView myList = (ListView) dialog.findViewById(R.id.listofhistory);
//        cr = myDb.getAllscorewithChapter("Machinery 1");
        cr = myDb.getAllscorewithChapter(QuizModel.getAssessmentCategory() + " 1");
        startManagingCursor(cr);

        tvQuizChapter.setText("" + ncourse + " Assessment");
        tvLastQuiz.setText(" " + tdate + "");
        String name = QUIZDBAdapter._NAME;

        name.replace(" 1", " ");

        String[] fromFieldNames = new String[]{name,
                QUIZDBAdapter._QDETAILS, QUIZDBAdapter._DATE, QUIZDBAdapter._SCORE, QUIZDBAdapter._USERNAMESCORE};
//        QUIZDBAdapter._NAME.replace("1", "");

        int[] toViewIDs = new int[]{R.id.tvQuiztitle, R.id.tvQdetails,
                R.id.tvQdatetaken, R.id.tvQscore, R.id.tvUserName};

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.quiz_history_layout, cr, fromFieldNames, toViewIDs);
        myList.setAdapter(myCursorAdapter);
        dialog.show();
    }

    @SuppressWarnings("deprecation")
    private void populateQwithdb() {

        final Dialog dialog = new Dialog(this, R.style.DialogAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.quiz_result_summary);
        Button backButtonQuizREsult = (Button) dialog.findViewById(R.id.backButtonQuizREsult);
        ListView myList = (ListView) dialog.findViewById(R.id.listquest);
        Cursor cr = myDb.getAlltempRows();
        startManagingCursor(cr);


        String[] fromFieldNames = new String[]{QuizModel.getAssessmentCategory().contentEquals("SimulationQuiz") ? QUIZDBAdapter.TEMP_NULL : QUIZDBAdapter.TEMP_Q_ITEM,
                QUIZDBAdapter.TEMP_UANS, QUIZDBAdapter.TEMP_ANS};

//        String[] fromFieldNames = new String[]{QUIZDBAdapter.TEMP_Q_ITEM,
//                QUIZDBAdapter.TEMP_UANS, QUIZDBAdapter.TEMP_ANS};

        int[] toViewIDs = new int[]{R.id.questiontv, R.id.useranswertv,
                R.id.trueanswertv};

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, // Context
                R.layout.quiz_question_item, // Row layout template
                cr, // cursor (set of DB records to map)
                fromFieldNames, // DB Column names
                toViewIDs // View IDs to put information in
        );

        backButtonQuizREsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        myList.setAdapter(myCursorAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Cursor cursor = myDb.getRow(id);
                if (cursor.moveToFirst()) {
                    long idDb = cursor.getLong(QUIZDBAdapter.COL_ROWID);
                    int qset = Integer.parseInt(cursor
                            .getString(QUIZDBAdapter.COL_SETID));
                    int item = Integer.parseInt(cursor
                            .getString(QUIZDBAdapter.COL_REFID));
                    String qitem = cursor.getString(QUIZDBAdapter.COL_QITEM);
                    String qans = cursor.getString(QUIZDBAdapter.COL_QANS);
                    String quans = cursor.getString(QUIZDBAdapter.COL_QUANS);

                    String Message = "Lessons" + (item + 1) + ".";

                    if (qans.equals(quans)) {
                        Toast.makeText(getApplicationContext(),
                                "You've got the correct Answer!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("ItemCountRes:", String.valueOf(item));
                        Log.i("ItemCountRes:", String.valueOf(qitem));
                        Bundle b = new Bundle();
                        b.putString("itemtitle", qans);
                        b.putString("itemcontent", qitem);
                        b.putInt("item", item);
                        b.putString("indicator", "assessreview");
                        b.putBoolean("isScrollables", false);
                        LessonsContentActivity.hasContent = "result";

                        Intent intent = new Intent(getApplicationContext(), SimulationQuizReviewAcitivity.class);
                        intent.putExtras(b);
                        startActivity(intent);

//                        if (QuizModel.getAssessmentCategory().contentEquals("SimulationQuiz")){
//                            Intent intent = new Intent(getApplicationContext(), SimulationQuizReviewAcitivity.class);
//                            intent.putExtras(b);
//                            startActivity(intent);
//
//
//                        }else{
//                            if (QuizModel.getAssessmentSem().contentEquals("Prelim")) {
//                                if (QuizModel.getAssessmentCategory().contentEquals("Prelim Week 1")) {
//                                    if (!quans.contentEquals("A. Density")) {
//                                        b.putInt("position", 7);
//                                        b.putString("positstring", "6");
//                                        b.putString("week", "Week 1");
//                                    } else if (!quans.contentEquals("B. Archimedes")) {
//                                        b.putInt("position", 0);
//                                        b.putString("positstring", "0");
//                                        b.putString("week", "Week 1");
//                                    } else if (!quans.contentEquals("A. Archimedes' Principle")) {
//                                        b.putInt("position", 0);
//                                        b.putString("week", "Week 0");
//                                    } else if (!quans.contentEquals("B. Bouyancy")) {
//                                        b.putInt("position", 6);
//                                        b.putString("positstring", "6");
//                                        b.putString("week", "Week 1");
//                                    } else if (!quans.contentEquals("D. Buoyant force")) {
//                                        b.putInt("position", 7);
//                                        b.putString("positstring", "7");
//                                        b.putString("week", "Week 1");
//                                    } else if (!quans.contentEquals("C. The law of floatation")) {
//                                        b.putInt("position", 8);
//                                        b.putString("positstring", "8");
//                                        b.putString("week", "Week 1");
//                                    } else if (!quans.contentEquals("A. Plimsoll line")) {
//                                        b.putInt("position", 13);
//                                        b.putString("positstring", "13");
//                                        b.putString("week", "Week 1");
//                                    } else if (!quans.contentEquals("A. Submarine")) {
//                                        Toast.makeText(getApplicationContext(), "Week 1 page 7", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 14);
//                                        b.putString("positstring", "14");
//                                        b.putString("week", "Week 1");
//                                    } else if (!quans.contentEquals("D. Hydrometer")) {
//                                        b.putInt("position", 15);
//                                        b.putString("positstring", "15");
//                                        b.putString("week", "Week 1");
//                                    }
//                                } else if (QuizModel.getAssessmentCategory().contentEquals("Prelim Week 2")) {
//                                    if (!quans.contentEquals("A. Density")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 1);
//                                        b.putString("positstring", "1");
//                                        b.putString("week", "Week 2");
//                                    } else if (!quans.contentEquals("C. Relative Density")) {
//                                        b.putInt("position", 1);
//                                        b.putString("positstring", "1");
//                                        b.putString("week", "Week 2");
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                    } else if (!quans.contentEquals("A. Displacement")) {
//                                        b.putInt("position", 6);
//                                        b.putString("positstring", "6");
//                                        b.putString("week", "Week 2");
//                                        Toast.makeText(getApplicationContext(), "Week 2 page 2", Toast.LENGTH_SHORT).show();
//                                    } else if (!quans.contentEquals("D. Draught")) {
//                                        b.putInt("position", 7);
//                                        b.putString("positstring", "7");
//                                        b.putString("week", "Week 2");
//                                        Toast.makeText(getApplicationContext(), "Week 2 page 2", Toast.LENGTH_SHORT).show();
//                                    } else if (!quans.contentEquals("B. Deadweight")) {
//                                        b.putInt("position", 9);
//                                        b.putString("positstring", "9");
//                                        b.putString("week", "Week 2");
//                                        Toast.makeText(getApplicationContext(), "Week 2 page 2", Toast.LENGTH_SHORT).show();
//                                    } else if (!quans.contentEquals("A. Freeboard")) {
//                                        b.putInt("position", 10);
//                                        b.putString("positstring", "10");
//                                        b.putString("week", "Week 2");
//                                        Toast.makeText(getApplicationContext(), "Week 2 page 2", Toast.LENGTH_SHORT).show();
//                                    } else if (!quans.contentEquals("A. Load lines")) {
//                                        b.putInt("position", 13);
//                                        b.putString("positstring", "13");
//                                        b.putString("week", "Week 2");
//                                        Toast.makeText(getApplicationContext(), "Week 2 page 2", Toast.LENGTH_SHORT).show();
//                                    } else if (!quans.contentEquals("C. Fresh water allowance")) {
//                                        b.putInt("position", 19);
//                                        b.putString("positstring", "19");
//                                        b.putString("week", "Week 2");
//                                        Toast.makeText(getApplicationContext(), "Week 2 page 2", Toast.LENGTH_SHORT).show();
//                                    } else if (!quans.contentEquals("A. Trim")) {
//                                        b.putInt("position", 28);
//                                        b.putString("positstring", "28");
//                                        b.putString("week", "Week 2");
//                                        Toast.makeText(getApplicationContext(), "Week 2 page 2", Toast.LENGTH_SHORT).show();
//                                    } else if (!quans.contentEquals("A. Buoyancy")) {
//                                        b.putInt("position", 31);
//                                        b.putString("positstring", "31");
//                                        b.putString("week", "Week 2");
//                                        Toast.makeText(getApplicationContext(), "Week 2 page 2", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else if (QuizModel.getAssessmentCategory().contentEquals("Prelim Week 3")) {
//                                    if (!quans.contentEquals("A. Deckline")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 8);
//                                        b.putString("positstring", "8");
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("B. Load Line Disc")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("A. Load Lines")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 2);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("A. Standard Load Line marking")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 7);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("D. Timber Load Lines Markings")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 12);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("A. Summer")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 10);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("D. Tropical")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 10);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("D. Winter")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 10);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("A. Winter North Atlantic")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 10);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("D. Lumber Summer")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 13);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("D. Lumber Winter")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 14);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("D. Lumber Tropical")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 14);
//                                        b.putString("week", "Week 3");
//                                    } else if (!quans.contentEquals("C. Lumber Tropical Fresh Water")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 14);
//                                        b.putString("week", "Week 3");
//                                    }
//                                } else if (QuizModel.getAssessmentCategory().contentEquals("Prelim Week 4")) {
//                                    if (!quans.contentEquals("A. Static stability")) {
////                                    Toast.makeText(getApplicationContext(), "Week 2 page 1", Toast.LENGTH_SHORT).show();
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("B. Heeling")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("D. List")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("A. Trim")) {
//                                        b.putInt("position", 2);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("B. Centre of Buoyancy")) {
//                                        b.putInt("position", 5);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("C. KG")) {
//                                        b.putInt("position", 3);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("B. KM")) {
//                                        b.putInt("position", 3);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("D. GM")) {
//                                        b.putInt("position", 3);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("A. Metacentre Point")) {
//                                        b.putInt("position", 6);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("D. Neutral Ship")) {
//                                        b.putInt("position", 11);
//                                        b.putString("week", "Week 4");
//                                    } else if (!quans.contentEquals("B. Angle of loll")) {
//                                        b.putInt("position", 15);
//                                        b.putString("week", "Week 4");
//                                    }
//                                } else if (QuizModel.getAssessmentCategory().contentEquals("Prelim Week 5")) {
//
//                                    if (!quans.contentEquals("A. Centre of gravity")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    } else if (!quans.contentEquals("A. G")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    } else if (!quans.contentEquals("B. Curves")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    } else if (!quans.contentEquals("A. Length")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    } else if (!quans.contentEquals("B. Cosine")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    } else if (!quans.contentEquals("D. Sine")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    } else if (!quans.contentEquals("C. Tangent")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    } else if (!quans.contentEquals("A. Mass")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    } else if (!quans.contentEquals("A. Buoyancy")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    } else if (!quans.contentEquals("A. Free surface, surface")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 5");
//                                    }
//                                } else if (QuizModel.getAssessmentCategory().contentEquals("Prelim Week 6")) {
//                                    if (!quans.contentEquals("A. Internal forces")) {
//                                        //External link
//                                    } else if (!quans.contentEquals("A. Ballast tank")) {
//                                        b.putInt("position", 1);
//                                        b.putString("week", "Week 6");
//                                    } else if (!quans.contentEquals("D. Cargo")) {
//                                        //External link
//                                    } else if (!quans.contentEquals("A. Gravity")) {
//                                        //External link
//                                    } else if (!quans.contentEquals("A. Trim")) {
//                                        b.putInt("position", 5);
//                                        b.putString("positstring", "5");
//                                        b.putString("week", "Week 6");
//                                    } else if (!quans.contentEquals("D. Distance")) {
//                                        //External link
//                                    } else if (!quans.contentEquals("B. Centre of floatation")) {
//                                        //External link
//                                    }
//                                }
//
//                            } else if (QuizModel.getAssessmentSem().contentEquals("Midterm")) {
//
//                            } else if (QuizModel.getAssessmentSem().contentEquals("Finals")) {
//
//                            }
//
//
//                            Intent intent = new Intent(getApplicationContext(), LessonsContentActivity.class);
//                            intent.putExtras(b);
//
//                            Log.i("ResultExtra:", String.valueOf(b));

//                        }

                        Log.i("ITEMRESULT:", String.valueOf(item));
                        Log.i("RESULT-Message:", Message);
                        Log.i("RESULT-quans:", quans);
                        Log.i("RESULT-qans:", qans);
                        Log.i("RESULT-qitem:", qitem);

                        Toast.makeText(getApplicationContext(), "Try to Review it Again", Toast.LENGTH_SHORT).show();
//                        if (CustomUtils.getQuestionCategory().contentEquals("Heat Engine Cycle")) {
//                            Bundle b = new Bundle();
//                            b.putInt("item", item);
//                            Intent intent = new Intent(getApplicationContext(), AssessmentReviewActivity.class);
//                            intent.putExtras(b);
//                            startActivity(intent);
//                        }
                    }
                }
                cursor.close();
            }
        });
        dialog.show();
    }


    public void obtlDialog() {
        final Dialog dialog = new Dialog(QuizResultsActivity.this,
                R.style.DialogAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_signout);
        dialog.setCancelable(false);
        Button bOk = (Button) dialog.findViewById(R.id.buttonOk);
        Button bCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        bOk.setText("Yes");
        bCancel.setText("No");
        TextView question = (TextView) dialog.findViewById(R.id.tvalertmessage);

        question.setText("Your score is less than 3. Would you like to try extra activity?");

        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent toQuiz = new Intent(QuizResultsActivity.this
//                        , Activity_Dashboard.class);
//                QuizResultsActivity.this.startActivity(toQuiz);
//                QuizResultsActivity.this.finish();
                dialog.dismiss();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void openDB() {
        myDb = new QUIZDBAdapter(this);
        myDb.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation == bounce_in1) {
            correct.setVisibility(View.VISIBLE);
        }
        if (animation == bounce_in2) {
            wrong.setVisibility(View.VISIBLE);
        }
        if (animation == fade_in) {
//            bscorelog.setVisibility(View.VISIBLE);
            bqresult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == bounce_in1) {
            wrong.clearAnimation();
            wrong.startAnimation(bounce_in2);
        }
        if (animation == bounce_in2) {
            bscorelog.startAnimation(fade_in);
            bqresult.startAnimation(fade_in);
        }
        if (animation == bounce_in2) {
//            tvcorrect.startAnimation(bounce_in3);
//            tvwrong.startAnimation(bounce_in3);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    public void ConfetiAnim() {
        new ParticleSystem(this, 80, R.drawable.confeti2, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 180, 180)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(findViewById(R.id.emiter_top_right), 8);

        new ParticleSystem(this, 80, R.drawable.confeti3, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(findViewById(R.id.emiter_top_left), 8);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                this.finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        QuizResultsActivity.this.finish();
        super.onBackPressed();
    }

}

