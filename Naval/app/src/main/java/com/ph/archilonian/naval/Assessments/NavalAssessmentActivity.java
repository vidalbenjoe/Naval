package com.ph.archilonian.naval.Assessments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ph.archilonian.naval.Database.AssessDB.QUIZDBAdapter;
import com.ph.archilonian.naval.Database.AssessDB.Question;
import com.ph.archilonian.naval.Database.AssessDB.QuestionSet;
import com.ph.archilonian.naval.Database.AssessDB.QuizModel;
import com.ph.archilonian.naval.Database.AssessDB.SessionCache;
import com.ph.archilonian.naval.Database.AssessDB.TempQuestion;
import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.CustomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NavalAssessmentActivity extends AppCompatActivity {
    List<Question> quesList;
    public Question question;

    SessionCache QuizSession;
    public static QUIZDBAdapter myDb;

    int score;
    int qid = 0;
    int page = 1;
    int qset = 1;

    String Uname;
    String ncourse;
    String finalDate;
    private Date date;

    ImageView quizlin;
    TextView tvQue, tvPage, tvRef;
    RadioButton rd1, rd2, rd3, rd4, rd5;
    Button bnext;
    Toolbar toolbar;
    RadioGroup grp;
    //    ProgressBar progressBar;
//    MyCountDownTimer countDownTimer;

    Boolean isDoneQuiz = true;
    Dialog dialog;
    LinearLayout questionsHolderLinear;

    //    http://www.codeproject.com/Questions/826312/Android-CountDown-Timer-in-Quiz-Application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_main_layout);
        toolbar = (Toolbar) findViewById(R.id.quiztoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Assessment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        QuizSession = new SessionCache(getApplicationContext());
        openDB();
        myDb.delAllTempRows();
//        quesList = myDb.getAllQuestionsPrelimWeekOne();
        ncourse = QuizModel.getAssessmentCategory();
        questionCategory();
        if (quesList.size() == 0) {
            loadQuestion();
            questionCategory();
        } else {
            Log.d("database not empty", "queslist with setno");
        }

        Typeface roadBrushttf = Typeface.createFromAsset(getAssets(), "fonts/ironman.ttf");
        quizlin = (ImageView) findViewById(R.id.quizlin);

        question = quesList.get(qid);

        Log.i("questonTAg", "" + question);

        questionsHolderLinear = (LinearLayout) findViewById(R.id.questionsHolderLinear);
        tvRef = (TextView) findViewById(R.id.tvRef);
        tvQue = (TextView) findViewById(R.id.tvQuestion);
        tvPage = (TextView) findViewById(R.id.tvPage);
        rd1 = (RadioButton) findViewById(R.id.rd1);
        rd2 = (RadioButton) findViewById(R.id.rd2);
        rd3 = (RadioButton) findViewById(R.id.rd3);
        rd4 = (RadioButton) findViewById(R.id.rd4);
        rd5 = (RadioButton) findViewById(R.id.rd5);

        bnext = (Button) findViewById(R.id.bnext);
        bnext.setEnabled(false);
        tvQue.setTypeface(roadBrushttf);
        rd1.setTypeface(roadBrushttf);
        rd2.setTypeface(roadBrushttf);
        rd3.setTypeface(roadBrushttf);
        rd4.setTypeface(roadBrushttf);
        bnext.setTypeface(roadBrushttf);
//        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        progressBar.setProgress(100);
        setQuestionView();
        tvPage.setText(page + "/15");

//        countDownTimer = new MyCountDownTimer(10000 /* 20 Sec */,
//                1000);
//        countDownTimer.start();
        grp = (RadioGroup) findViewById(R.id.radioGroup1);



        rd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnext.setEnabled(true);
                rd1.setBackgroundResource(R.drawable.rounded_green);
                rd2.setBackgroundResource(R.drawable.rounded_white);
                rd3.setBackgroundResource(R.drawable.rounded_white);
                rd4.setBackgroundResource(R.drawable.rounded_white);

                rd1.setTextColor(Color.WHITE);
                rd2.setTextColor(Color.parseColor("#787777"));
                rd3.setTextColor(Color.parseColor("#787777"));
                rd4.setTextColor(Color.parseColor("#787777"));

                rd5.setSelected(false);
            }
        });

        rd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnext.setEnabled(true);
                rd1.setBackgroundResource(R.drawable.rounded_white);
                rd2.setBackgroundResource(R.drawable.rounded_green);
                rd3.setBackgroundResource(R.drawable.rounded_white);
                rd4.setBackgroundResource(R.drawable.rounded_white);
                rd1.setTextColor(Color.parseColor("#787777"));
                rd2.setTextColor(Color.WHITE);
                rd3.setTextColor(Color.parseColor("#787777"));
                rd4.setTextColor(Color.parseColor("#787777"));

                rd5.setSelected(false);
            }
        });

        rd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnext.setEnabled(true);
                rd1.setBackgroundResource(R.drawable.rounded_white);
                rd2.setBackgroundResource(R.drawable.rounded_white);
                rd3.setBackgroundResource(R.drawable.rounded_green);
                rd4.setBackgroundResource(R.drawable.rounded_white);
                rd1.setTextColor(Color.parseColor("#787777"));
                rd2.setTextColor(Color.parseColor("#787777"));
                rd3.setTextColor(Color.WHITE);
                rd4.setTextColor(Color.parseColor("#787777"));
                rd5.setSelected(false);
            }
        });

        rd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnext.setEnabled(true);
                rd1.setBackgroundResource(R.drawable.rounded_white);
                rd2.setBackgroundResource(R.drawable.rounded_white);
                rd3.setBackgroundResource(R.drawable.rounded_white);
                rd4.setBackgroundResource(R.drawable.rounded_green);
                rd1.setTextColor(Color.parseColor("#787777"));
                rd2.setTextColor(Color.parseColor("#787777"));
                rd3.setTextColor(Color.parseColor("#787777"));
                rd4.setTextColor(Color.WHITE);
                rd5.setSelected(false);
            }
        });

        bnext.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {
//                countDownTimer.cancel();
                if (nextQuestion(false)) {
                    // Start The timer again
//                    countDownTimer.start();
                }
            }
        });
    }

    boolean nextQuestion(Boolean c) {

        RadioButton answerRb = (RadioButton) findViewById(grp
                .getCheckedRadioButtonId());
        grp.clearCheck();

        page++;
        rd1.setBackgroundResource(R.drawable.rounded_white);
        rd2.setBackgroundResource(R.drawable.rounded_white);
        rd3.setBackgroundResource(R.drawable.rounded_white);
        rd4.setBackgroundResource(R.drawable.rounded_white);

        rd1.setTextColor(Color.parseColor("#787777"));
        rd2.setTextColor(Color.parseColor("#787777"));
        rd3.setTextColor(Color.parseColor("#787777"));
        rd4.setTextColor(Color.parseColor("#787777"));

        if (rd5.isSelected()) {
            String lid = tvRef.getText().toString();
            String qitem = tvQue.getText().toString();
            String qans = question.getQans().toString();
            String quserans = "No Answer";
            myDb.addtempQuestion(new TempQuestion("1", lid, qitem,
                    qans, quserans));
        } else {
            if (question.getQans().equals(answerRb.getText())) {
                String lid = tvRef.getText().toString();
                String qitem = tvQue.getText().toString();
                String qans = question.getQans().toString();
                String quserans = answerRb.getText().toString();
                myDb.addtempQuestion(new TempQuestion("1", lid, qitem,
                        qans, quserans));
                score++;
                Log.d("course", "Your score is " + score);

            } else if (!question.getQans().equals(answerRb.getText())) {
                String lid = tvRef.getText().toString();
                String qitem = tvQue.getText().toString();
                String qans = question.getQans().toString();
                String quserans = answerRb.getText().toString();
                myDb.addtempQuestion(new TempQuestion("1", lid, qitem,
                        qans, quserans));
                Log.d("wrong answer", answerRb.getText() + " is incorrect");
            }
        }

        try {
            if (qid < 15) {
                question = quesList.get(qid);
                setQuestionView();
                grp.clearCheck();
                tvPage.setText(page + "/15");
                bnext.setEnabled(false);
            } else {
                UserDialog();
            }
        }catch (Exception e){
            UserDialog();
        }


        return true;
    }


    public void loadQuestion() {

        if (QuizModel.getAssessmentCategory() == "SimulationQuiz") {
            QuestionSet.simulationQues();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 1") {
            QuestionSet.PrelimWeekOneQues();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 2") {
            QuestionSet.PrelimWeekTwoQues();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 3") {
            QuestionSet.PrelimWeekThreeQues();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 4") {
            QuestionSet.PrelimWeekFourQues();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 5") {
            QuestionSet.PrelimWeekFiveQues();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 6") {
            QuestionSet.PrelimWeekSixQues();
        }

        //midterm
        else if (QuizModel.getAssessmentCategory() == "Midterm Week 7") {
            QuestionSet.MidtermWeekSevenQues();
        } else if (QuizModel.getAssessmentCategory() == "Midterm Week 8") {
            QuestionSet.MidtermWeekEightQues();
        } else if (QuizModel.getAssessmentCategory() == "Midterm Week 9 - 10") {
            QuestionSet.MidtermWeekNineAndTenQues();
        } else if (QuizModel.getAssessmentCategory() == "Midterm Week 11") {
            QuestionSet.MidtermWeekElevenQues();
        }
        //finals
        else if (QuizModel.getAssessmentCategory() == "Finals Week 12 - 13") {
            QuestionSet.FinalsWeekThirteenQues();
        } else if (QuizModel.getAssessmentCategory() == "Finals Week 14") {
            QuestionSet.FinalsWeekFourteenQues();
        } else if (QuizModel.getAssessmentCategory() == "Finals Week 15") {
            QuestionSet.FinalsWeekFifthteenQues();
        } else if (QuizModel.getAssessmentCategory() == "Finals Week 16 -17") {
            QuestionSet.FinalsWeekSixthteenQues();
        } else if (QuizModel.getAssessmentCategory() == "Finals Week 18") {
            QuestionSet.FinalsWeekEighteenQues();
        }
    }

    public void questionCategory() {

        if (QuizModel.getAssessmentCategory() == "SimulationQuiz") {
            quesList = myDb.getAllQuestionsSimulation();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 1") {
            quesList = myDb.getAllQuestionsPrelimWeekOne();
            CustomUtils.toastMessage(this, "Week 1");
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 2") {
            CustomUtils.toastMessage(this, "Week 2");
            quesList = myDb.getAllQuestionsPrelimWeekTWO();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 3") {
            CustomUtils.toastMessage(this, "Week 3");
            quesList = myDb.getAllQuestionsPrelimWeekThree();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 4") {
            CustomUtils.toastMessage(this, "Week 4");
            quesList = myDb.getAllQuestionsPrelimWeekFour();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 5") {
            CustomUtils.toastMessage(this, "Week 5");
            quesList = myDb.getAllQuestionsPrelimWeekFive();
        } else if (QuizModel.getAssessmentCategory() == "Prelim Week 6") {
            CustomUtils.toastMessage(this, "Week 6");
            quesList = myDb.getAllQuestionsPrelimWeekSix();
            //Midterm
        } else if (QuizModel.getAssessmentCategory() == "Midterm Week 7") {
            CustomUtils.toastMessage(this, "Week 7");
            quesList = myDb.getAllQuestionsMidtermWeekSeven();
        } else if (QuizModel.getAssessmentCategory() == "Midterm Week 8") {
            CustomUtils.toastMessage(this, "Week 8");
            quesList = myDb.getAllQuestionsMidtermWeekEight();
        } else if (QuizModel.getAssessmentCategory() == "Midterm Week 9 - 10") {
            CustomUtils.toastMessage(this, "Week 9 - 10");
            quesList = myDb.getAllQuestionsMidtermWeekNine();
        } else if (QuizModel.getAssessmentCategory() == "Midterm Week 11") {
            CustomUtils.toastMessage(this, "Week 11");
            quesList = myDb.getAllQuestionsMidtermWeekEleven();
        }
        //finals
        else if (QuizModel.getAssessmentCategory() == "Finals Week 12 - 13") {
            CustomUtils.toastMessage(this, "Week 12 - 13");
            quesList = myDb.getAllQuestionsFinalsWeekThirteen();
        } else if (QuizModel.getAssessmentCategory() == "Finals Week 14") {
            CustomUtils.toastMessage(this, "Week 14");
            quesList = myDb.getAllQuestionsFinalsWeekFourteen();
        } else if (QuizModel.getAssessmentCategory() == "Finals Week 15") {
            CustomUtils.toastMessage(this, "Week 15");
            quesList = myDb.getAllQuestionsFinalsWeekFifthteen();
        } else if (QuizModel.getAssessmentCategory() == "Finals Week 16 -17") {
            CustomUtils.toastMessage(this, "Week 16");
            quesList = myDb.getAllQuestionsFinalsWeekSixthteen();
        } else if (QuizModel.getAssessmentCategory() == "Finals Week 18") {
            CustomUtils.toastMessage(this, "Week 18");
            quesList = myDb.getAllQuestionsFinalsWeekEighteen();
        }

    }

    public void setQuestionView() {
        tvRef.setText(question.getLid());
        tvQue.setText(question.getQitem() + "");
        rd1.setText(question.getOpta());
        rd2.setText(question.getOptb());
        rd3.setText(question.getOptc());
        rd4.setText(question.getOptd());

        if (rd3.getText().toString().isEmpty() && rd4.getText().toString().isEmpty()) {
            rd3.setVisibility(View.INVISIBLE);
            rd4.setVisibility(View.INVISIBLE);
        } else {
            if (rd3.getText().toString().isEmpty()) {
                rd3.setVisibility(View.INVISIBLE);
            } else if (rd4.getText().toString().isEmpty()) {
                rd4.setVisibility(View.INVISIBLE);
            } else {
                rd3.setVisibility(View.VISIBLE);
                rd4.setVisibility(View.VISIBLE);
            }
        }
        String refNumber = tvRef.getText().toString();
        int refInt = Integer.parseInt(refNumber);

        if (QuizModel.getAssessmentCategory() == "SimulationQuiz") {
            questionsHolderLinear.setVisibility(View.GONE);
            if (refInt == 1) {//
                quizlin.setBackgroundResource(R.drawable.quizimganchor);
                quizlin.setVisibility(View.VISIBLE);
                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 2) {
                quizlin.setBackgroundResource(R.drawable.quizimgbowthruster);
                quizlin.setVisibility(View.VISIBLE);
                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 3) {

                quizlin.setBackgroundResource(R.drawable.quizimgbulbousbow);
                quizlin.setVisibility(View.VISIBLE);
                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 4) {
                quizlin.setBackgroundResource(R.drawable.quizimgshipcrane);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 5) {
                quizlin.setBackgroundResource(R.drawable.quizimgdeckofship);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 6) {
                quizlin.setBackgroundResource(R.drawable.quizimgforecastle);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 7) {
                quizlin.setBackgroundResource(R.drawable.quizimgfunnelship);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 8) {
                quizlin.setBackgroundResource(R.drawable.quizimghull);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 9) {
                quizlin.setBackgroundResource(R.drawable.quizimgmaindeck);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 10) {
                quizlin.setBackgroundResource(R.drawable.quizimgmarinesteamengine);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 11) {
                quizlin.setBackgroundResource(R.drawable.quizimgmarinegasturbine);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 12) {
                quizlin.setBackgroundResource(R.drawable.quizimgmarinesteam);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 13) {
                quizlin.setBackgroundResource(R.drawable.quizimgpropeller);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 14) {
                quizlin.setBackgroundResource(R.drawable.quizimgrudder);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 15) {
                quizlin.setBackgroundResource(R.drawable.quizimgshaftofship);
                quizlin.setVisibility(View.VISIBLE);

                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else if (refInt == 16) {
                quizlin.setBackgroundResource(R.drawable.quizimgsternship);
                quizlin.setVisibility(View.VISIBLE);
                Log.d("REFERENCE NUMBER:", tvRef.getText().toString());
            } else {
                quizlin.setVisibility(View.GONE);
            }
        }
        qid++;


    }


    public void UserDialog() {

        if (isDoneQuiz) {
            dialog = new Dialog(NavalAssessmentActivity.this,
                    R.style.DialogAnim);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_entername_layout);
            dialog.setCancelable(false);
            final EditText enterNameEdt = (EditText) dialog.findViewById(R.id.enterNameEdt);
            Button submitNameBtn = (Button) dialog.findViewById(R.id.submitNameBtn);

            submitNameBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Uname = enterNameEdt.getText().toString();
                    Intent in = getIntent();
                    int retake = in.getExtras().getInt("retakeNum");
                    date = new Date();
                    SimpleDateFormat timeFormat = new SimpleDateFormat(
                            "MMM dd, yyyy");
                    finalDate = timeFormat.format(date);
                    String subj = ncourse + " " + qset;
                    Log.i("SUBJNCOURSE:", subj);
                    String qdetails = "Retake: " + retake;
                    myDb.addscores(3, retake, subj, qdetails, score, finalDate, Uname);
                    myDb.deleteQuiz(ncourse);
                    Intent intent = new Intent(getApplicationContext(), QuizResultsActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("qno", qset);
                    b.putInt("score", score);
                    Log.i("PASS FINAL SCORE %d: ", String.valueOf(score));
                    b.putString("course", ncourse);//
                    b.putString("quizdetails", qdetails);
                    intent.putExtras(b);
                    startActivity(intent);
                    NavalAssessmentActivity.this.finish();
                    closeDB();
                }
            });
            dialog.show();
//            countDownTimer.cancel();
            isDoneQuiz = false;
        } else {
//            dialog.dismiss();
        }

    }

    /**
     * public class MyCountDownTimer extends CountDownTimer {
     * public MyCountDownTimer(long startTime, long interval) {
     * super(startTime, interval);
     * }
     * <p>
     * public void onFinish() {
     * Log.e("Times up", "Times up");
     * rd5.setSelected(true);
     * countDownTimer.cancel();
     * if (nextQuestion(false)) {
     * // Start The timer again
     * countDownTimer.start();
     * } else {
     * countDownTimer.cancel();
     * }
     * }
     *
     * @Override public void onTick(long millisUntilFinished) {
     * timerCountTxtV.setText("Timer: " + (millisUntilFinished / 1000) + "");
     * Log.e("Second Gone", "Another Second Gone");
     * Log.e("Time Remaining", "seconds remaining: " + millisUntilFinished
     * / 1000);
     * int progress = (int) (millisUntilFinished / 100);
     * progressBar.setProgress(progress);
     * <p>
     * }
     * }
     */
    private void openDB() {
        myDb = new QUIZDBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        NavalAssessmentActivity.this.finish();
//        countDownTimer.cancel();
        super.onBackPressed();
    }

}
