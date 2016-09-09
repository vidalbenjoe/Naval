package com.ph.archilonian.naval.Assessments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ph.archilonian.naval.R;


public class SimulationQuizReviewAcitivity extends AppCompatActivity {
    TextView txtContentTitle, tvContents, pageCountTxt;
    String itemTitle, itemContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_topic_contents);
        txtContentTitle = (TextView) findViewById(R.id.txtContentTitle);
        tvContents = (TextView) findViewById(R.id.tvContents);
        pageCountTxt = (TextView) findViewById(R.id.pageCountTxt);
        itemTitle = getIntent().getExtras().getString("itemtitle");
        itemContent = getIntent().getExtras().getString("itemcontent");

//        Log.i("ITemTitleQuizA:", itemTitle);
        String reviewTitle = itemTitle.substring(3);

        pageCountTxt.setVisibility(View.GONE);

        txtContentTitle.setText(reviewTitle);
        tvContents.setText(itemContent);


    }
}
