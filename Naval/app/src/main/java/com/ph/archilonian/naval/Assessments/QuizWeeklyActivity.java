package com.ph.archilonian.naval.Assessments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ph.archilonian.naval.Database.AssessDB.QUIZDBAdapter;
import com.ph.archilonian.naval.Database.AssessDB.QuizModel;
import com.ph.archilonian.naval.Database.AssessDB.SessionCache;
import com.ph.archilonian.naval.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class QuizWeeklyActivity extends AppCompatActivity {
    QUIZDBAdapter myDb;
    Intent intent;
    SessionCache QuizSession;
    int retake;
    int prevTotal;
    int curTotal;
    String initVal = "1";
    String finalDate, strCategory;
    Bundle bundle;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.quiz_weekly_layout);
        toolbar = (Toolbar) findViewById(R.id.quizWeekToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(QuizModel.getAssessmentSem());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayout weeklyquizLinear = (LinearLayout) findViewById(R.id.weeklyquizLinear);

        Log.i("weekCount:", String.valueOf(QuizModel.getWeekCount()));
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        param.setMargins(30, 10, 30, 10);

        Button[] btn = new Button[QuizModel.getWeekCount()];
        for (int i = 0; i < QuizModel.getWeekCount(); i++) {
            btn[i] = new Button(getApplicationContext());
            int iteration = i + 1;
            btn[i].setId(iteration);
            btn[i].setPadding(10, 0, 0, 0);
            btn[i].setText("Week " + iteration);
            btn[i].setTextColor(Color.parseColor("#FFFFFF"));
            btn[i].setTextSize(18);
            btn[i].setHeight(90);
            btn[i].setLayoutParams(param);
            btn[i].setGravity(Gravity.LEFT | Gravity.BOTTOM);
            btn[i].setBackgroundResource(R.drawable.gradient_rounded_green);
            btn[i].setOnClickListener(handleOnClick(btn[i]));
            weeklyquizLinear.addView(btn[i]);
        }

        QuizSession = new SessionCache(QuizWeeklyActivity.this);
        openDB();

        strCategory = QuizModel.getAssessmentSem();
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy");
        finalDate = timeFormat.format(date);
    }

    View.OnClickListener handleOnClick(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                int index = v.getId();
                Log.i("handleOnClick:", String.valueOf(index));

                if (QuizModel.getAssessmentSem() == "Prelim") {
                    if (index == 1) {
                        QuizModel.setAssessmentCategory("Prelim Week " + 1);
                    } else if (index == 2) {
                        QuizModel.setAssessmentCategory("Prelim Week " + 2);
                    } else if (index == 3) {
                        QuizModel.setAssessmentCategory("Prelim Week " + 3);
                    } else if (index == 4) {
                        QuizModel.setAssessmentCategory("Prelim Week " + 4);
                    } else if (index == 5) {
                        QuizModel.setAssessmentCategory("Prelim Week " + 5);
                    } else if (index == 6) {
                        QuizModel.setAssessmentCategory("Prelim Week " + 6);
                    }
                }
                if (QuizModel.getAssessmentSem() == "Midterm") {
                    if (index == 1) {
                        QuizModel.setAssessmentCategory("Midterm Week " + 7);
                    } else if (index == 2) {
                        QuizModel.setAssessmentCategory("Midterm Week " + 8);
                    } else if (index == 3) {
                        QuizModel.setAssessmentCategory("Midterm Week " + "9 - 10");
                    } else if (index == 4) {
                        QuizModel.setAssessmentCategory("Midterm Week " + 11);
                    }
                }
                if (QuizModel.getAssessmentSem() == "Finals") {
                    if (index == 1) {
                        QuizModel.setAssessmentCategory("Finals Week " + "12 - 13");
                    } else if (index == 2) {
                        QuizModel.setAssessmentCategory("Finals Week " + 14);
                    } else if (index == 3) {
                        QuizModel.setAssessmentCategory("Finals Week " + 15);
                    } else if (index == 4) {
                        QuizModel.setAssessmentCategory("Finals Week " + "16 -17");
                    } else if (index == 5) {
                        QuizModel.setAssessmentCategory("Finals Week " + 18);
                    }
                }

                if (QuizSession.hasFlQuiz1()) {
                    final Dialog dialog = new Dialog(QuizWeeklyActivity.this, R.style.DialogAnim);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_signout);
                    Button bYes = (Button) dialog.findViewById(R.id.buttonOk);
                    Button bNo = (Button) dialog.findViewById(R.id.buttonCancel);
                    TextView tvalertmessage = (TextView) dialog.findViewById(R.id.tvalertmessage);
                    HashMap<String, String> quizRecord = QuizSession.getTotalSum();
                    retake = Integer.parseInt(quizRecord.get(SessionCache.REPEATING1));
                    prevTotal = Integer.parseInt(quizRecord.get(SessionCache.NAV_MAX_ITEM1));

//                        CustomUtils.setQuestionCategory(CustomUtils.getQuestionCategory());
                    Log.i("popasodpad: ", QuizModel.getAssessmentSem());
                    Log.i("retaikes: ", "" + retake);
                    Log.i("prevToto: ", "" + prevTotal);
                    Log.i("quizRecord: ", "" + quizRecord);

                    if (retake >= 3) {
                        tvalertmessage.setText("You have taken this " + retake + " times, Do you want to take this quiz again?");
                        bYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // delete the record
                                myDb.deleteQuiz(QuizModel.getAssessmentSem());
                                // store last quiz session for JS and for all the records
                                QuizSession.StoreFlLastQuizTaken(finalDate);
                                QuizSession.StoreAllLastQuizTaken(finalDate);
                                // delete the scorerow if the
                                // user wants to
                                // overwrite the first take of
                                // quiz
//                                    myDb.deletescorerowSet(1, CustomUtils.getQuestionCategory() + " 1");
                                // get the retake value + 1
                                // sum is 4 so when the user try
                                // to take the quiz
                                // again, he will not able to
                                // take it any more, he
                                // will the next condition which
                                // will appear
                                // "You have taken this 4 times"
                                int sum = retake + 1;
                                Log.i("getQuestionCategory: ", "" + QuizModel.getAssessmentSem());
                                myDb.addAUXquiz(1, QuizModel.getAssessmentSem(), "", "0 %");
                                QuizSession.FinishSessionNum1(Integer.toString(sum));
                                bundle = new Bundle();
                                intent = new Intent(QuizWeeklyActivity.this, NavalAssessmentActivity.class);
//                                bundle.putString("questcat", QuizModel.getAssessmentSem());

                                intent.putExtra("retakeNum", sum);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                dialog.dismiss();

                            }
                        });
                        bNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        // this condition will use if retake is
                        // value 1 to 2
                        myDb.deleteQuiz(QuizModel.getAssessmentSem());
                        QuizSession.StoreFlLastQuizTaken(finalDate);
                        QuizSession.StoreAllLastQuizTaken(finalDate);
                        int sum = retake + 1;
                        myDb.addAUXquiz(1, QuizModel.getAssessmentSem(), "", "0 %");
                        curTotal = prevTotal + 5;
                        QuizSession.StoreTotal1(Integer.toString(curTotal));
                        QuizSession.FinishSessionNum1(Integer.toString(sum));
                        bundle = new Bundle();
                        intent = new Intent(QuizWeeklyActivity.this, NavalAssessmentActivity.class);
//                        bundle.putString("questcat", QuizModel.getAssessmentSem());
                        Log.i("sumElse: ", "" + sum);
                        intent.putExtra("retakeNum", sum);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                } else {
                    QuizSession.StoreFlLastQuizTaken(finalDate);
                    QuizSession.StoreAllLastQuizTaken(finalDate);
                    int passVal = Integer.parseInt(initVal);
                    myDb.addAUXquiz(1, QuizModel.getAssessmentSem(), initVal, "0 %");
                    curTotal = prevTotal + 5;
                    QuizSession.StoreTotal1(Integer.toString(curTotal));
                    QuizSession.FinishSessionNum1(initVal);
                    bundle = new Bundle();
                    intent = new Intent(QuizWeeklyActivity.this, NavalAssessmentActivity.class);
                    bundle.putString("questcat", QuizModel.getAssessmentSem());
                    Log.i("passValSum: ", "" + passVal);
                    intent.putExtra("retakeNum", passVal);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }

        };
    }

    private void openDB() {
        myDb = new QUIZDBAdapter(this);
        myDb.open();
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
        QuizWeeklyActivity.this.finish();
        super.onBackPressed();

    }

}
