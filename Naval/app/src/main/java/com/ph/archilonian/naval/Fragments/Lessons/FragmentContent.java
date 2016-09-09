package com.ph.archilonian.naval.Fragments.Lessons;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ph.archilonian.naval.Assessments.NavalAssessmentActivity;
import com.ph.archilonian.naval.Database.AssessDB.QUIZDBAdapter;
import com.ph.archilonian.naval.Database.AssessDB.QuizModel;
import com.ph.archilonian.naval.Database.AssessDB.SessionCache;
import com.ph.archilonian.naval.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FragmentContent extends Fragment {
    String strSemester, strWeek, strTitle, strContent, strPosition;
    LinearLayout linearExitButtons;
    View lineSeparator;
    Button takeAssessmentBt;


    QUIZDBAdapter myDb;
    Intent intent;
    SessionCache QuizSession;
    int retake;
    int prevTotal;
    int curTotal;
    String initVal = "1";
    String finalDate, strCategory;
    Bundle bundle;

    public static String consSem, consWeek;
    public static int currentPage,pageCount;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle data = getArguments();
        /** Getting integer data of the key current_page from the bundle */
        strSemester = data.getString("Semester");
        strWeek = data.getString("Week");
        strTitle = data.getString("Title");
        strContent = data.getString("Content");
        strPosition = data.getString("position");
        QuizSession = new SessionCache(getActivity());
        openDB();

        strCategory = QuizModel.getAssessmentSem();
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy");
        finalDate = timeFormat.format(date);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_topic_contents, viewGroup, false);
        lineSeparator = (View) v.findViewById(R.id.lineSeparator);
        TextView txtContentTitle = (TextView) v.findViewById(R.id.txtContentTitle);
        TextView txtContent = (TextView) v.findViewById(R.id.tvContents);
        linearExitButtons = (LinearLayout) v.findViewById(R.id.linearExitButtons);
        takeAssessmentBt = (Button) v.findViewById(R.id.takeAssessmentBt);
        TextView pageCountTxt = (TextView) v.findViewById(R.id.pageCountTxt);

        txtContentTitle.setText(strTitle);
        txtContent.setText(strContent);
        Log.i("CurrentPageActual:", String.valueOf(currentPage));

        int totalpage = pageCount - 1;
        pageCountTxt.setText(currentPage+ "/"+totalpage);
        txtContent.setSingleLine(false);
        if (consWeek.contentEquals("Week 0")) {
            takeAssessmentBt.setVisibility(View.GONE);
        }
        try {
            if (strTitle.equals("") && strContent.equals("")) {
                linearExitButtons.setVisibility(View.VISIBLE);
                pageCountTxt.setVisibility(View.GONE);
                lineSeparator.setVisibility(View.GONE);
                takeAssessmentBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSemester();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    public void getSemester() {
        Log.i("conSem:", consSem);
        Log.i("consWeek:", consWeek);
        if (consSem.contentEquals("Prelim")) {
            QuizModel.setAssessmentSem("Prelim");
            if (consWeek.contentEquals("Week 1")) {
                QuizModel.setAssessmentCategory("Prelim Week 1");
            } else if (consWeek.contentEquals("Week 2")) {
                QuizModel.setAssessmentCategory("Prelim Week 2");
            } else if (consWeek.contentEquals("Week 3")) {
                QuizModel.setAssessmentCategory("Prelim Week 3");
            } else if (consWeek.contentEquals("Week 4")) {
                QuizModel.setAssessmentCategory("Prelim Week 4");
            } else if (consWeek.contentEquals("Week 5")) {
                QuizModel.setAssessmentCategory("Prelim Week 5");
            } else if (consWeek.contentEquals("Week 6")) {
                QuizModel.setAssessmentCategory("Prelim Week 6");
            }
            takeAssessmentFunc();
        } else if (consSem.contentEquals("Midterm")) {
            QuizModel.setAssessmentSem("Midterm");
            if (consWeek.contentEquals("Week 7")) {
                QuizModel.setAssessmentCategory("Midterm Week 7");
            } else if (consWeek.contentEquals("Week 8")) {
                QuizModel.setAssessmentCategory("Midterm Week 8");
            } else if (consWeek.contentEquals("Week 9 - 10")) {
                QuizModel.setAssessmentCategory("Midterm Week 9 - 10");
            } else if (consWeek.contentEquals("Week 11")) {
                QuizModel.setAssessmentCategory("Midterm Week 11");
            }
            takeAssessmentFunc();
        } else if (consSem.contentEquals("Finals")) {
            QuizModel.setAssessmentSem("Finals");
            if (consWeek.contentEquals("Week 12 - 13")) {
                QuizModel.setAssessmentCategory("Finals Week 12 - 13");
            } else if (consWeek.contentEquals("Week 14")) {
                QuizModel.setAssessmentCategory("Finals Week 14");
            } else if (consWeek.contentEquals("Week 15")) {
                QuizModel.setAssessmentCategory("Finals Week 15");
            } else if (consWeek.contentEquals("Week 16 -17")) {
                QuizModel.setAssessmentCategory("Finals Week 16 -17");
            } else if (consWeek.contentEquals("Week 18")) {
                QuizModel.setAssessmentCategory("Finals Week 18");
            }
            takeAssessmentFunc();
        }
    }

    public void takeAssessmentFunc() {
        if (QuizSession.hasFlQuiz1()) {
            final Dialog dialog = new Dialog(getActivity(), R.style.DialogAnim);
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
                        intent = new Intent(getActivity(), NavalAssessmentActivity.class);
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
                intent = new Intent(getActivity(), NavalAssessmentActivity.class);
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
            intent = new Intent(getActivity(), NavalAssessmentActivity.class);
            bundle.putString("questcat", QuizModel.getAssessmentSem());
            Log.i("passValSum: ", "" + passVal);
            intent.putExtra("retakeNum", passVal);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }

    private void openDB() {
        myDb = new QUIZDBAdapter(getActivity());
        myDb.open();
    }


}
