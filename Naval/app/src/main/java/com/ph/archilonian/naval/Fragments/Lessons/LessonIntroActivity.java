package com.ph.archilonian.naval.Fragments.Lessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ph.archilonian.naval.R;
import com.ph.archilonian.naval.Utilities.GUI.CustomViewPager;

import static com.ph.archilonian.naval.Fragments.Lessons.LessonsContentActivity.hasContent;

public class LessonIntroActivity extends AppCompatActivity {
    String lessonWeek, lessonTitle;
    Button viewDetailsBtn;

    Toolbar toolbar;

    Bundle bundle;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_intro_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lessonWeek = getIntent().getExtras().getString("lessonweek");
        lessonTitle = getIntent().getExtras().getString("lessontitle");
        getSupportActionBar().setTitle(lessonWeek);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView lessonTitleContent = (TextView) findViewById(R.id.lessonTitleContent);
        TextView introContent = (TextView) findViewById(R.id.introContent);
        if (lessonWeek.contentEquals("Week 0")){
            introContent.setText(getString(R.string.intro_preweek0));
        } else if (lessonWeek.contentEquals("Week 1")) {
            introContent.setText(getString(R.string.intro_preweek1));
        } else if (lessonWeek.contentEquals("Week 2")) {
            introContent.setText(getString(R.string.intro_preweek2));
        } else if (lessonWeek.contentEquals("Week 3")) {
            introContent.setText(getString(R.string.intro_preweek3));
        } else if (lessonWeek.contentEquals("Week 4")) {
            introContent.setText(getString(R.string.intro_preweek4));
        } else if (lessonWeek.contentEquals("Week 5")) {
            introContent.setText(getString(R.string.intro_preweek5));
        } else if (lessonWeek.contentEquals("Week 6")) {
            introContent.setText(getString(R.string.intro_preweek6));
        } else if (lessonWeek.contentEquals("Week 7")) {
            introContent.setText(getString(R.string.intro_midweek7));
        } else if (lessonWeek.contentEquals("Week 8")) {
            introContent.setText(getString(R.string.intro_midweek8));
        } else if (lessonWeek.contentEquals("Week 9 - 10")) {
            introContent.setText(getString(R.string.intro_midweek910));
        } else if (lessonWeek.contentEquals("Week 11")) {
            introContent.setText(getString(R.string.intro_midweek11));
        } else if (lessonWeek.contentEquals("Week 12 - 13")) {
            introContent.setText(getString(R.string.intro_finweek1213));
        } else if (lessonWeek.contentEquals("Week 14")) {
            introContent.setText(getString(R.string.intro_finweek14));
        } else if (lessonWeek.contentEquals("Week 15")) {
            introContent.setText(getString(R.string.intro_finweek15));
        } else if (lessonWeek.contentEquals("Week 16 -17")) {
            introContent.setText(getString(R.string.intro_finweek1617));
        } else if (lessonWeek.contentEquals("Week 18")) {
            introContent.setText(getString(R.string.intro_finweek18));
        }

        viewDetailsBtn = (Button) findViewById(R.id.viewDetailsBtn);
        lessonTitleContent.setText(lessonTitle);

        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomViewPager pvp = new CustomViewPager(LessonIntroActivity.this);
                pvp.setPagingEnabled(true);
                hasContent = "not";



                bundle = new Bundle();
                i = new Intent(LessonIntroActivity.this, LessonsContentActivity.class);
                bundle.putString("week", lessonWeek);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

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
        LessonIntroActivity.this.finish();
        super.onBackPressed();

    }
}
