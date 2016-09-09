package com.ph.archilonian.naval.Assessments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ph.archilonian.naval.Database.AssessDB.QuizModel;
import com.ph.archilonian.naval.R;

public class QuizSemesterFragment extends Fragment {
    Button prelimBtn, midtermBtn, finalsBtn;
    Intent i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.quiz_semester_layout, container, false);

        prelimBtn = (Button) mainView.findViewById(R.id.prelimBtn);
        midtermBtn = (Button) mainView.findViewById(R.id.midtermBtn);
        finalsBtn = (Button) mainView.findViewById(R.id.finalsBtn);

        prelimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getActivity(), QuizWeeklyActivity.class);
                startActivity(i);
                QuizModel.setAssessmentSem("Prelim");
                QuizModel.setWeekCount(6);

            }
        });

        midtermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getActivity(), QuizWeeklyActivity.class);
                startActivity(i);
                QuizModel.setAssessmentSem("Midterm");
                QuizModel.setWeekCount(4);
            }
        });

        finalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getActivity(), QuizWeeklyActivity.class);
                startActivity(i);
                QuizModel.setAssessmentSem("Finals");
                QuizModel.setWeekCount(5);
            }
        });

        return mainView;
    }
}
